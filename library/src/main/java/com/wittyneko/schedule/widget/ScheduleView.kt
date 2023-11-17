package com.wittyneko.schedule.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.wittyneko.schedule.R
import com.wittyneko.schedule.extensions.*
import org.joda.time.Duration
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.Period

/**
 * Desc: 日程时间轴
 * <p>
 * Date: 2020-07-28
 * Copyright: Copyright (c) 2010 - 2020
 * Updater:
 * Update Time:
 * Update Comments:
 * @property itemLongClickListener OnLongClickListener
 * @property itemClickListener OnClickListener
 * @property editClickListener OnClickListener
 * @property cancelClickListener OnClickListener
 *
 * Author: wittyneko
 */
class ScheduleView : FrameLayout {
    val TAG = "hrq===112233"

    private var mLastFocusX = 0f
    private var mLastFocusY = 0f
    private var mDownFocusX = 0f
    private var mDownFocusY = 0f

    private var mLastFocusRawX = 0f
    private var mLastFocusRawY = 0f
    private var mDownFocusRawX = 0f
    private var mDownFocusRawY = 0f

    private var isCheckTouch = false
    private var isRemoveItem = false
    private var isFirstLayout = true

    // 自动滚动状态
    var autoScroll = AutoScroll.IDLE

    enum class AutoScroll { IDLE, TOP, BOTTOM }

    var showCurrentTime = true//显示当前时间
    set(value) {
        field = value
        currentTime.visibility = if (value) View.VISIBLE else View.GONE
    }
    var onlyTodayShowCurrentTime = false //只在今天显示当前时间
    var timeOffset = 0L //服务器时间偏移量
    val serviceTime get() = System.currentTimeMillis() - timeOffset //服务器时间
    val zeroTime = LocalTime.MIDNIGHT //开始时间
    val durationItemMin = Duration.standardMinutes(30) //item最小时间
    val duration24 = Duration.standardHours(24)
    val durationMin = Duration(0) // 可调整最小时间
    val durationMax = duration24 // 可调整最大时间
    val timePattern = "HH:mm"
    val time24String = "24:00"

    val layoutInterface by lazy { LayoutInflater.from(context) }
    val longPressTimeout = ViewConfiguration.getLongPressTimeout()
    val viewConfiguration by lazy { ViewConfiguration.get(context) }

    val topSpace = resources.idp(8)
    val bottomSpace = resources.idp(8)

    var columnMargin = resources.idp(1)
    val itemWidth get() = measuredWidth - mDelegate.item_margin_start - mDelegate.item_margin_end + columnMargin
    val smoothScrollSpace = resources.idp(8) //每次滑动距离
    val timeTextoffset = resources.idp(7) // 隐藏时间距离偏移

    val lineViewList = arrayListOf<View>()
    val timeViewList = arrayListOf<TextView>()
    val itemViewList = arrayListOf<ScheduleItem>()
    var itemEditView: ScheduleItem? = null
        private set

    val scrollView by lazy { parentAsView<NestedScrollView>() }
    val editView by lazy {
        val view = layoutInterface.inflate(R.layout.layout_schedule_item_edit, this, false) as ScheduleEdit
        view.asLayoutParams<MarginLayoutParams>().bottomMargin = bottomSpace
        view.editTouchView.asLayoutParams<MarginLayoutParams>().apply {
            view.editTouchView.setBackgroundResource(mDelegate.edit_background)
            marginStart = mDelegate.edit_margin_start.toInt()
            marginEnd = mDelegate.edit_margin_end.toInt()
        }

        view.topPoint.asLayoutParams<MarginLayoutParams>().apply {
            view.topPoint.setImageResource(mDelegate.edit_point_src)
            width = mDelegate.edit_point_size
            height = mDelegate.edit_point_size

            leftMargin = mDelegate.edit_point_margin_start
            topMargin = - mDelegate.edit_point_size / 2
        }
        view.bottomPoint.asLayoutParams<MarginLayoutParams>().apply {
            view.bottomPoint.setImageResource(mDelegate.edit_point_src)
            width = mDelegate.edit_point_size
            height = mDelegate.edit_point_size

            rightMargin = mDelegate.edit_point_margin_end
            bottomMargin = - mDelegate.edit_point_size / 2
        }

        view.tvContent.apply {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDelegate.edit_text_size.toFloat())
            this.setTextColor(mDelegate.edit_text_color)
            this.asLayoutParams<MarginLayoutParams>().apply {
                marginStart = mDelegate.edit_text_margin_start
                topMargin = mDelegate.edit_text_margin_top
                marginEnd = mDelegate.edit_text_margin_end
                bottomMargin = mDelegate.edit_text_margin_bottom
            }
        }

        view.startTimeView.apply {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDelegate.edit_start_text_size.toFloat())
            this.setTextColor(mDelegate.edit_start_text_color)
            this.asLayoutParams<MarginLayoutParams>().apply {
                marginStart = mDelegate.edit_start_text_margin_start
                topMargin = mDelegate.edit_start_text_margin_top
            }
        }

        view.endTimeView.apply {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDelegate.edit_end_text_size.toFloat())
            this.setTextColor(mDelegate.edit_end_text_color)
            this.asLayoutParams<MarginLayoutParams>().apply {
                marginStart = mDelegate.edit_end_text_margin_start
                bottomMargin = -mDelegate.edit_end_text_margin_bottom
            }
        }

        view
    }
    val currentTime by lazy {
        val view = layoutInterface.inflate(R.layout.layout_schedule_current_time, this, false) as ScheduleCurrentTime
        view.timeView.apply {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDelegate.current_time_text_size.toFloat())
            this.setTextColor(mDelegate.current_time_text_color)
            this.asLayoutParams<MarginLayoutParams>().apply {
                marginStart = mDelegate.current_time_text_margin_start
            }
        }
        view.point.apply {
            this.visibility = if (mDelegate.current_time_show_point) View.VISIBLE else View.GONE
            this.setImageResource(mDelegate.current_time_point_src)
            this.asLayoutParams<MarginLayoutParams>().apply {
                width = mDelegate.current_time_point_width
                height = mDelegate.current_time_point_height
                marginStart = mDelegate.current_time_point_margin_start
            }
        }
        view.line.apply {
            this.setBackgroundColor(mDelegate.current_time_line_src)
            this.asLayoutParams<MarginLayoutParams>().apply {
                height = mDelegate.current_time_line_height
                marginStart = mDelegate.current_time_line_margin_start
                marginEnd = mDelegate.current_time_line_margin_end
            }
        }
        view
    }

    var selectedDate = LocalDate.now() //当前选中日期
    private var mAdapter: Adapter<Any>? = null

    var itemLongClickListener = OnLongClickListener {
        //编辑Item
        if (mDelegate.edit_enable) {
            editItem(it.asView())
            editView.isAdd = false
            mDelegate.edit_modify_date_vibrate_enable.takeIf { it }?.let {
                VibrateUtils.vibrate(context, mDelegate.vibrate_time)
            }
        }
        true
    }

    var itemClickListener = OnClickListener {
        val item = it.asView<ScheduleItem>()
        onItemClickListener?.invoke(item, itemViewList.indexOf(item))
        //删除Item
        //removeItem(it.asView())
    }

    var editClickListener = OnClickListener {
        hideEdit()
        itemEditView?.also { item ->
            //编辑
            moveItem(item, editView.startPeriod, editView.endPeriod)
            itemEditView = null
        } ?: run {
            //新建
            //val item = createItem(editView.startPeriod, editView.editEndPeriod)
            //addItem(item)
            onCreateClickListener?.onClick(editView)
        }
    }

    var cancelClickListener = OnClickListener {
        hideEdit()
        itemEditView?.also { item ->
            moveItem(item, editView.startPeriod, editView.endPeriod)
            itemEditView = null
        }
    }

    var onCreateClickListener: OnClickListener? = null

    var onItemClickListener: ((view: ScheduleItem, position: Int) -> Unit)? = null

    var onItemChangeListener: ((view: ScheduleItem, position: Int) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private val mDelegate = ScheduleDelegate()

    /**
     * 获取属性，可按需设置
     */
    fun getDelegate() = mDelegate

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        attrs?.let {
            mDelegate.initTypedArray(resources, context.obtainStyledAttributes(attrs, R.styleable.ScheduleView))
        }

        clipChildren = false
        clipToPadding = false

        for (i in 0..24) {
            //添加时间线
            addView(View(context).also {
                lineViewList.add(i, it)
                it.setBackgroundColor(mDelegate.time_line_src)
                it.layoutParams = generateDefaultLayoutParams().also { layoutParams ->
                    layoutParams.height = mDelegate.time_line_height
                    layoutParams.topMargin = mDelegate.item_space * i + topSpace
                    if (i == 24) layoutParams.bottomMargin = bottomSpace
                    layoutParams.marginStart = mDelegate.time_line_margin_start
                    layoutParams.marginEnd = mDelegate.time_line_margin_end
                }
            })

            addView(TextView(context).also {
                timeViewList.add(i, it)
                it.text = when (i) {
                    24 -> time24String
                    else -> zeroTime.plusHours(i).toString(timePattern)
                }
                it.setTextColor(mDelegate.time_text_color)
                it.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDelegate.time_text_size.toFloat())
                it.layoutParams = generateDefaultLayoutParams().also { layoutParams ->
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    layoutParams.topMargin = mDelegate.item_space * i
                    layoutParams.marginStart = mDelegate.time_text_margin_start
                    layoutParams.marginEnd = mDelegate.time_line_margin_end
                }
            })
        }

        addView(currentTime)
    }

    override fun onAttachedToWindow() {
        post { refreshTime() }
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(::refreshTime)
        super.onDetachedFromWindow()
    }

    fun refreshTime() {
        currentTime.updateLayoutParams()
        postDelayed(::refreshTime, 2000)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        itemViewList.forEach {
            it.updateLayoutParams()
            it.layoutParams.resolveLayoutDirection(it.layoutDirection)
            //updateViewLayout(it, it.layoutParams)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isFirstLayout && visibility == View.VISIBLE) {
            currentTime.updateLayoutParams()
            scrollToCurTime()
            isFirstLayout = false
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var consume = false
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownFocusX = ev.x
                mDownFocusY = ev.y
                mLastFocusX = ev.x
                mLastFocusY = ev.y
                mDownFocusRawX = ev.rawX
                mDownFocusRawY = ev.rawY
                mLastFocusRawX = ev.rawX
                mLastFocusRawY = ev.rawY
                isCheckTouch = false
                isRemoveItem = false
                autoScroll = AutoScroll.IDLE
                editView.resetTouchStatus()
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_CANCEL -> {
            }
        }
        return consume.takeIf { it } ?: super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var consume = false
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {

                if (Math.abs(ev.y - mDownFocusY) > viewConfiguration.scaledTouchSlop * 0.3) {
                    if (editView.isShow && editView.isItemTouch) {
                        parent.requestDisallowInterceptTouchEvent(true)
                        consume = editView.isShow
                    }
                } else {
                    if (isCheckTouch) editView.checkTouchLocation(ev)
                    if (editView.isItemTouch) isCheckTouch = false
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        //Log.e(ev.run { "action $action, $consume, $x, $y ${editView.isItemTouch}, ${editView.isTopTouch}, ${editView.isBottomTouch}" })
        return consume.takeIf { it } ?: super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var consume = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (editView.isShow) editView.checkTouchLocation(event)
                consume = true
            }
            MotionEvent.ACTION_MOVE -> {
                // 创建状态
                if (editView.isShow) {
                    // 触摸事件在编辑布局
                    if (editView.run { isItemTouch || isTopTouch || isBottomTouch }) {
                        parent.requestDisallowInterceptTouchEvent(true)
                        consume = true
                        val diffY = event.y - mLastFocusY
                        val betweenY = event.y - mDownFocusY
                        val betweenTime = distanceToTime(betweenY, 0)
                        //val betweenDuration = distanceToTime(betweenY, 0).toStandardDuration()
                        //val betweenMinute = betweenDuration.standardMinutes
                        //Log.e("betweenTime ${betweenMinute};  " + betweenTime.run { "$hours, $minutes" })

                        // 编辑时间
                        when {
                            // 调整开始时间
                            editView.isTopTouch -> {
                                val startPeriod = (editView.editStartPeriod + betweenTime).round()

                                if ((editView.endPeriod - startPeriod).toStandardDuration() >= durationItemMin
                                    && startPeriod.toStandardDuration() >= durationMin
                                ) {
                                    takeIf { editView.startPeriod != startPeriod }?.let { touchVibrate() }
                                    editView.startPeriod = startPeriod
                                }
                            }
                            // 调整结束时间
                            editView.isBottomTouch -> {
                                val endPeriod = (editView.editEndPeriod + betweenTime).round()

                                if ((endPeriod - editView.startPeriod).toStandardDuration() >= durationItemMin
                                    && endPeriod.toStandardDuration() <= durationMax
                                ) {
                                    takeIf { editView.endPeriod != endPeriod }?.let { touchVibrate() }
                                    editView.endPeriod = endPeriod
                                }
                            }
                            // 调整时间段
                            editView.isItemTouch -> {
                                val startPeriod = (editView.editStartPeriod + betweenTime).round()
                                val diff = startPeriod - editView.editStartPeriod
                                val endPeriod = editView.editEndPeriod + diff

                                if (startPeriod.toStandardDuration() >= durationMin
                                    && endPeriod.toStandardDuration() <= durationMax
                                ) {
                                    takeIf { editView.startPeriod != startPeriod && editView.endPeriod != endPeriod }?.let { touchVibrate() }

                                    editView.startPeriod = startPeriod
                                    editView.endPeriod = endPeriod
                                }
                            }
                        }

                        editView.updateLayoutParams()
                        editView.requestLayout()

                        // 滑动到顶部/底部自动滚动
                        if (autoScroll != AutoScroll.IDLE || Math.abs(betweenY) > viewConfiguration.scaledTouchSlop) {

                            val scrollFrame = scrollView.visibleOnScreen
                            val outsideTop = event.rawY - scrollFrame.top < mDelegate.item_space + topSpace
                            val canScrollTop = scrollView.canScrollVertically(-1) && outsideTop
                            val outsideBottom =
                                scrollFrame.bottom - event.rawY < mDelegate.item_space + bottomSpace
                            val canScrollBottom = scrollView.canScrollVertically(1) && outsideBottom
                            autoScroll = when {
                                !outsideTop && !outsideBottom -> AutoScroll.IDLE
                                autoScroll != AutoScroll.IDLE && (canScrollTop || canScrollBottom) -> autoScroll
                                diffY < 0 && canScrollTop -> AutoScroll.TOP
                                diffY >= 0 && canScrollBottom -> AutoScroll.BOTTOM
                                else -> autoScroll
                            }

                            when (autoScroll) {
                                AutoScroll.TOP -> {
                                    scrollView.smoothScrollBy(0, -smoothScrollSpace)
                                }
                                AutoScroll.BOTTOM -> {
                                    scrollView.smoothScrollBy(0, smoothScrollSpace)
                                }
                            }
                        }
                    }
                }
                mLastFocusX = event.x
                mLastFocusY = event.y
                mLastFocusRawX = event.rawX
                mLastFocusRawY = event.rawY
            }
            MotionEvent.ACTION_UP -> {
                VibrateUtils.cancelVibrate(context)

                mLastFocusX = event.x
                mLastFocusY = event.y
                mLastFocusRawX = event.rawX
                mLastFocusRawY = event.rawY
                if (editView.isShow) {
                    if (editView.run { isItemTouch || isTopTouch || isBottomTouch }) {
//                        if (Math.abs(mLastFocusY - mDownFocusY) < viewConfiguration.scaledTouchSlop) {
//                            editClickListener.onClick(editView)
//                        }
                    } else {
                        cancelClickListener.onClick(this)
                    }
                } else {
                    if (!isRemoveItem && mDelegate.append_enable) {
                        val start = distanceToTime(mLastFocusY).round().takeIf { it.hours < 23 }
                            ?: Period.hours(23)
                        showEdit(start)
                        editView.isAdd = true
                        mDelegate.append_vibrate_enable.takeIf { it }?.let {
                            VibrateUtils.vibrate(context, mDelegate.vibrate_time)
                        }
                    }
                }
            }
        }


        //Log.e(event.run { "action $action, $consume, $x, $y ${editView.isItemTouch}, ${editView.isTopTouch}, ${editView.isBottomTouch}" })

        return consume.takeIf { it } ?: super.onTouchEvent(event)
    }

    /**
     * 编辑状态震动
     */
    private fun touchVibrate() {
        if (editView.isAdd && mDelegate.append_modify_date_vibrate_enable) {
            VibrateUtils.vibrate(context, mDelegate.vibrate_time)
        } else  if (!editView.isAdd && mDelegate.edit_modify_date_vibrate_enable) {
            VibrateUtils.vibrate(context, mDelegate.vibrate_time)
        }
    }

    /**
     * Desc: 滚动到当前时间前一小时
     * <p>
     * Author: wittyneko
     * Date: 2020-08-07
     */
    fun scrollToCurTime() {
        val localTime = LocalTime(serviceTime)
        val hour = Math.max(localTime.hourOfDay - 1, 0)
        val localPeriod = Period.hours(hour)
        val curY = Math.min(
            timeToDistance(localPeriod, 0).toInt(),
            measuredHeight - scrollView.measuredHeight
        )
        scrollView.scrollTo(0, curY)
    }

    /**
     * Desc: 进入创建/编辑
     * <p>
     * Author: wittyneko
     * Date: 2020-07-22
     * @param startPeriod Period
     * @param endPeriod Period
     */
    fun showEdit(startPeriod: Period, endPeriod: Period = startPeriod.plusHours(1)) {
        editView.startPeriod = startPeriod
        editView.endPeriod = endPeriod
        editView.updateLayoutParams()
        if (!editView.isShow) {
            addView(editView)
            post {
                editView.updateLayoutParams()
                requestLayout()
            }
        } else {
            requestLayout()
        }
        if (itemEditView != null) {
            mAdapter?.apply {
                val index = itemViewList.indexOf(itemEditView!!)
                if (index < getItemCount()) bindEdit(getItem(index), editView, index)
            }
        } else {
            mAdapter?.bindCreate(editView)
        }
        editView.editTouchView.setOnClickListener(editClickListener)
    }

    /**
     * Desc: 取消创建/编辑
     * <p>
     * Author: wittyneko
     * Date: 2020-07-22
     */
    fun hideEdit() {
        removeView(editView)
        post {
            editView.updateLayoutParams()
            requestLayout()
        }
    }

    fun cancelEdit() {
        itemEditView = null
        hideEdit()
    }

    /**
     * Desc: 移动Item
     * <p>
     * Author: wittyneko
     * Date: 2020-07-27
     * @param item TimeTableItem
     * @param startPeriod Period
     * @param endPeriod Period
     */
    fun moveItem(item: ScheduleItem, startPeriod: Period, endPeriod: Period) {
        item.startPeriod = startPeriod
        item.endPeriod = endPeriod
        onItemChangeListener?.invoke(item, itemViewList.indexOf(item))
        measureItemColumn()
        item.requestLayout()
    }

    /**
     * Desc: 编辑Item
     * <p>
     * Author: wittyneko
     * Date: 2020-07-27
     * @param item TimeTableItem
     */
    fun editItem(item: ScheduleItem) {
        isCheckTouch = true
        itemEditView = item
        parent.requestDisallowInterceptTouchEvent(true)
        showEdit(item.startPeriod, item.endPeriod)
    }

    /**
     * Desc: 删除Item
     * <p>
     * Author: wittyneko
     * Date: 2020-07-27
     * @param item TimeTableItem
     */
    fun removeItem(item: ScheduleItem) {
        itemViewList.remove(item)
        measureItemColumn()
        removeView(item)
        isRemoveItem = true
    }

    /**
     * Desc: 删除多个Item
     * <p>
     * Author: wittyneko
     * Date: 2020-07-27
     * @param item Array<out TimeTableItem>
     */
    fun removeItems(vararg item: ScheduleItem) {
        itemViewList.removeAll(item)
        measureItemColumn()
        item.forEach { removeView(it) }
        isRemoveItem = true
    }

    /**
     * Desc: 添加Item
     * <p>
     * Author: wittyneko
     * Date: 2020-07-27
     * @param item TimeTableItem
     */
    fun addItem(item: ScheduleItem) {
        item.maxLines = mDelegate.item_text_max_lines
        itemViewList.add(item)
        measureItemColumn()
        addView(item, indexOfChild(currentTime))
    }

    /**
     * Desc: 添加多个Item
     * <p>
     * Author: wittyneko
     * Date: 2020-07-27
     * @param item Array<out TimeTableItem>
     */
    fun addItems(vararg item: ScheduleItem) {
        item.forEach { it.maxLines = mDelegate.item_text_max_lines }
        itemViewList.addAll(item)
        measureItemColumn()
        item.forEach { addView(it, indexOfChild(currentTime)) }
    }

    /**
     * Desc: 新建Item
     * <p>
     * Author: wittyneko
     * Date: 2020-07-23
     * @param startPeriod Period
     * @param endPeriod Period
     * @return TimeTableItem
     */
    fun createItem(startPeriod: Period, endPeriod: Period) = run {
        val item = layoutInterface.inflate(
            R.layout.layout_schedule_item, this, false
        ) as ScheduleItem

        item.startPeriod = startPeriod
        item.endPeriod = endPeriod
        item.setOnLongClickListener(itemLongClickListener)
        item.setOnClickListener(itemClickListener)
        //item.updateLayoutParams()

        item.setBackgroundResource(mDelegate.item_background)

        item.ivLeft.apply {
            this.visibility = if (mDelegate.item_show_left_icon) View.VISIBLE else View.GONE
            this.setImageResource(mDelegate.item_left_icon_src)
        }

        item.tvContent.apply {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDelegate.item_text_size.toFloat())
            this.setTextColor(mDelegate.item_text_color)
            this.asLayoutParams<MarginLayoutParams>().apply {
                marginStart = mDelegate.item_text_margin_start
                topMargin = mDelegate.item_text_margin_top
                marginEnd = mDelegate.item_text_margin_end
                bottomMargin = mDelegate.item_text_margin_bottom
            }
        }

        item
    }

    /**
     * Desc: 距离换算时间
     * <p>
     * Author: wittyneko
     * Date: 2020-07-22
     * @param dist Float
     * @param offset Int
     * @return Period
     */
    fun distanceToTime(dist: Float, offset: Int = topSpace) = run {
        val time = (dist - offset) / mDelegate.item_space
        val hour = time.toInt()
        val minute = ((time - hour) * 60).toInt()
        //LocalTime(hour, minute)

        Period(hour, minute, 0, 0)
    }

    /**
     * Desc: 时间换算距离
     * <p>
     * Author: wittyneko
     * Date: 2020-07-22
     * @param time Period
     * @param offset Int
     * @return Float
     */
    fun timeToDistance(time: Period, offset: Int = topSpace) = run {
        time.hours * mDelegate.item_space + time.minutes / 60f * mDelegate.item_space + offset
    }

    /**
     * Desc: 15分钟取整
     * <p>
     * Author: wittyneko
     * Date: 2020-07-23
     * @param round 最小时间，默认为15分钟
     * @receiver Period
     * @return Period
     */
    fun Period.round(round: Int = mDelegate.min_minute): Period {
        return withMinutes(minutes / round * round)
    }

    fun measureItemColumn() {
        val rowList = arrayListOf<Row>() // 行数据
        //val columnList = arrayListOf<Column>() // 列数据
        //val sortList = itemViewArray.sortedByDescending { (it.endPeriod - it.startPeriod).toStandardDuration() }
        val sortList = itemViewList.sortedWith(
            compareBy<ScheduleItem> { it.startPeriod.toStandardDuration() }
                .thenByDescending { (it.endPeriod - it.startPeriod).toStandardDuration() }
        )

        sortList.forEach { item ->

            /**
             * 1. 判断列是否存在不存在新建
             * 2. 比较item时间和列时间
             * 2.1 在列之内比较下一列
             * 2.2 在列之外添加到列
             */
            var rowIndex = 0
            var columnIndex = 0
            while (true) {

                // 判断行是否存在
                if (rowIndex >= rowList.size) {
                    val column = Column(item.startPeriod, item.endPeriod, arrayListOf(item))
                    val row = Row(item.startPeriod, item.endPeriod, arrayListOf(column))
                    rowList.add(row)
                    break
                }


                val row = rowList[rowIndex]
                val rowStart = row.startPeriod.toStandardDuration()
                val rowEnd = row.endPeriod.toStandardDuration()
                val rowRange = rowStart..rowEnd

                val itemStart = item.startPeriod.toStandardDuration()
                val itemEnd = item.endPeriod.toStandardDuration()
                val itemRange = itemStart..itemEnd

                val equalStartEnd = (itemStart == rowEnd || itemEnd == rowStart)
                val inRow = (itemStart in rowRange || itemEnd in rowRange)
                if ((inRow && !equalStartEnd).not()) {
                    rowIndex++
                    continue
                }

                val columnList = row.columnList
                if (columnIndex < columnList.size) {
                    val column = columnList[columnIndex]
                    val columnStart = column.startPeriod.toStandardDuration()
                    val columnEnd = column.endPeriod.toStandardDuration()
                    val columnRange = columnStart..columnEnd

                    val isBreak = run {

                        column.viewList.forEach { prev ->

                            val prevStart = prev.startPeriod.toStandardDuration()
                            val prevEnd = prev.endPeriod.toStandardDuration()
                            val prevRange = prevStart..prevEnd

                            val equalColumnStartEnd = (itemStart == prevEnd || itemEnd == prevStart)
                            val inColumn = (itemStart in prevRange || itemEnd in prevRange)

//                            Log.e(
//                                "columnRange " +
//                                        "${zeroTime.plus(item.startPeriod)}, ${zeroTime.plus(item.endPeriod)}, " +
//                                        "${zeroTime.plus(prev.startPeriod)}, ${zeroTime.plus(prev.endPeriod)}, " +
//                                        "$inColumn equalColumnStartEnd, $rowIndex $columnIndex "
//                            )
                            if (inColumn && !equalColumnStartEnd) {
                                columnIndex++
                                return@run false
                            }
                        }
                        column.viewList.add(item)

                        if (columnEnd < itemEnd) column.endPeriod = item.endPeriod
                        if (columnStart > itemStart) column.startPeriod = item.startPeriod
                        if (rowEnd < itemEnd) row.endPeriod = item.endPeriod
                        if (rowStart > itemStart) row.startPeriod = item.startPeriod
                        return@run true
                    }
                    if (isBreak) break
                } else {
                    columnList.add(Column(item.startPeriod, item.endPeriod, arrayListOf(item)))
                    if (rowEnd < itemEnd) row.endPeriod = item.endPeriod
                    if (rowStart > itemStart) row.startPeriod = item.startPeriod

                    break
                }
            }
        }

        /**
         * 1. 遍历列数据下所有item，赋值开始列和总列数
         * 2. item 和下一列起止时间比较
         * 2.1 在时间内结束列为该列
         * 2.2 不再继续比较下一列
         */
        rowList.forEach { row ->
            val columnList = row.columnList
            val columnListSize = columnList.size
            columnList.forEachIndexed { index, column ->

                column.viewList.forEach { item ->
                    item.columnStart = index
                    item.columnEnd = index
                    item.columnSize = columnListSize
                    val itemStart = item.startPeriod.toStandardDuration()
                    val itemEnd = item.endPeriod.toStandardDuration()
                    val itemRange = itemStart..itemEnd

                    for (next in (index + 1) until columnListSize) {
                        val nextColumn = columnList[next]
                        val isBreak = run {
                            nextColumn.viewList.forEach { nextItem ->

                                val columnStart = nextItem.startPeriod.toStandardDuration()
                                val columnEnd = nextItem.endPeriod.toStandardDuration()
                                val columnRange = columnStart..columnEnd

                                val equalStartEnd =
                                    (itemStart == columnEnd || itemEnd == columnStart)
                                val inColumn = itemStart in columnRange || itemEnd in columnRange
                                        || columnStart in itemRange || columnEnd in itemRange
                                if (inColumn && !equalStartEnd) {
                                    return@run true
                                }
                            }
                            return@run false
                        }
                        if (isBreak) break
                        item.columnEnd = next
                    }
                }
            }
        }
    }

    /**
     * Desc: 计算Item位置
     * <p>
     * Author: wittyneko
     * Date: 2020-07-26
     * @receiver TimeTableItem
     */
    fun ScheduleItem.updateLayoutParams() {
        //Log.e(run { "columnMeasure $columnStart, $columnEnd, $columnSize, $columnMeasure, $itemWidth" })
        layoutParams.topMargin = timeToDistance(startPeriod).toInt()
        layoutParams.height = timeToDistance(endPeriod - startPeriod, 0).toInt()
        //layoutParams.marginStart = itemMarginStart.toInt()
        //layoutParams.marginEnd = itemMarginEnd.toInt()
        val width = itemWidth * columnMeasureWidth
        layoutParams.marginStart = (mDelegate.item_margin_start + itemWidth * columnMeasureStart).toInt()
        layoutParams.width = (itemWidth * columnMeasureWidth - columnMargin).toInt()
    }

    /**
     * Desc: 计算编辑位置
     * <p>
     * Author: wittyneko
     * Date: 2020-07-26
     * @receiver TimeTableEdit
     */
    fun ScheduleEdit.updateLayoutParams() {
        val startTime = zeroTime.plus(startPeriod)
        val endTime = zeroTime.plus(endPeriod)

        layoutParams.topMargin = timeToDistance(startPeriod).toInt()
        layoutParams.height = timeToDistance(endPeriod - startPeriod, 0).toInt()
        startTimeView.text = startTime.toString(timePattern)
        endTimeView.text = when {
            endPeriod.toStandardDuration() == duration24 -> time24String
            else -> endTime.toString(timePattern)
        }
        updateCurrentTime()
        updateTimeVisible()
    }

    /**
     * Desc: 计算当前时间位置
     * <p>
     * Author: wittyneko
     * Date: 2020-07-27
     * @receiver TimeTableCurrentTime
     */
    fun ScheduleCurrentTime.updateLayoutParams() {
        val localTime = LocalTime(serviceTime)
        val localPeriod = Period(localTime.hourOfDay, localTime.minuteOfHour, 0, 0)
        val local = localPeriod.toStandardDuration()
        val isToady = selectedDate == LocalDate(serviceTime)
        mDelegate.item_end_mode.takeIf { it == ScheduleDelegate.ITEM_END_MODE_DEFAULT }?.let {
            //默认情况根据当前时间处理
            itemViewList.forEach { item ->
                val start = item.startPeriod.toStandardDuration()
                val end = item.endPeriod.toStandardDuration()
                val (bg, left, color) = when {
                    isToady && local > end -> Triple(
                        mDelegate.item_end_background, mDelegate.item_end_left_icon_src,
                        mDelegate.item_end_text_color
                    )
                    isToady && local in item.run { start..end } -> Triple(
                        mDelegate.item_background, mDelegate.item_left_icon_src,
                        mDelegate.item_text_color
                    )
                    else -> Triple(
                        mDelegate.item_background, mDelegate.item_left_icon_src,
                        mDelegate.item_text_color
                    )
                }
                item.setBackgroundResource(bg)
                item.ivLeft.setImageResource(left)
                item.tvContent.setTextColor(color)
            }
        }
        val curY = timeToDistance(localPeriod) - measuredHeight / 2
        updateCurrentTime()
        if (translationY != curY) {
            translationY = curY
            currentTime.timeView.text = localTime.toString(timePattern)
            updateTimeVisible()
        } else {
            // 不是今天隐藏当前时间
            val newVisibility = if (isToady) View.VISIBLE else View.GONE
            if (onlyTodayShowCurrentTime && currentTime.visibility != newVisibility) {
                currentTime.visibility = newVisibility
                updateTimeVisible()
            }
        }
        //layoutParams.resolveLayoutDirection(layoutDirection)
    }

    private fun updateCurrentTime() {
        showCurrentTime.takeIf { it }?.let { currentTime.visibility = View.VISIBLE } ?: run { currentTime.visibility = View.GONE }
    }

    /**
     * Desc: 隐藏重叠时间
     * <p>
     * Author: wittyneko
     * Date: 2020-07-27
     */
    fun updateTimeVisible() {
        currentTime.timeView.post {
            val startRange = editView.startTimeView.frameOnScreen.run { top..bottom }
            val endRange = editView.endTimeView.frameOnScreen.run { top..bottom }
            val curFrame = currentTime.timeView.frameOnScreen

            val curRange = curFrame.run { top..bottom }
            val curTop = curFrame.top + timeTextoffset
            val curBottom = curFrame.bottom - timeTextoffset
            val curVisible = curTop in startRange || curBottom in startRange
                    || curTop in endRange || curBottom in endRange
            //currentTime.timeView.visibility = if (curVisible) View.INVISIBLE else View.VISIBLE
            currentTime.timeView.setTextColor(if (curVisible) Color.TRANSPARENT else mDelegate.current_time_text_color)

            // 背景时间隐藏
            timeViewList.forEach {
                val frame = it.frameOnScreen
                frame.top += timeTextoffset
                frame.bottom -= timeTextoffset
                // 判断是否和编辑编辑区域重叠
                val inRange = frame.top in startRange || frame.bottom in startRange
                        || frame.top in endRange || frame.bottom in endRange
                val visibility = if (inRange && editView.isShow) View.INVISIBLE else {
                    // 判断当前时间区域重叠
                    val inCurRange = frame.top in curRange || frame.bottom in curRange
                    val isCurVisible = currentTime.isShow && currentTime.visibility == View.VISIBLE
                    if (inCurRange && isCurVisible) View.INVISIBLE else View.VISIBLE
                }
                it.setTextColor(if (visibility != View.VISIBLE) Color.TRANSPARENT else mDelegate.time_text_color)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun setAdapter(adapter: Adapter<*>) {
        if (mAdapter != adapter) {
            mAdapter = adapter as Adapter<Any>
        }
    }

    fun notifyItem(position: Int) {
        mAdapter?.apply {
            if (position < getItemCount() && position < itemViewList.size) {
                val view = itemViewList[position]
                bindView(getItem(position), view, position)
            }
        }
    }

    fun notifyAllItem() {
        mAdapter?.also { adapter ->
            for (position in 0 until adapter.getItemCount()) {
                val item = adapter.getItem(position)
                val view = itemViewList.getOrNull(position)
                    ?: createItem(Period.ZERO, Period.ZERO).also {
                        it.maxLines = mDelegate.item_text_max_lines
                        itemViewList.add(it)
                    }
                adapter.bindView(item, view, position)
            }

            val removeList = if (itemViewList.size > adapter.getItemCount()) {
                val count = itemViewList.size - adapter.getItemCount()
                val item = itemViewList.takeLast(count)
                itemViewList.removeAll(item)
                item
            } else emptyList()

            measureItemColumn()
            removeList.forEach { removeView(it) }
            currentTime.updateLayoutParams()
            itemViewList.forEach { if (!it.isShow) addView(it, indexOfChild(currentTime)) }
        }
    }

    fun getAdapter() = mAdapter

    class Row(
        var startPeriod: Period,
        var endPeriod: Period,
        var columnList: MutableList<Column>
    )

    class Column(
        var startPeriod: Period,
        var endPeriod: Period,
        var viewList: MutableList<ScheduleItem>
    )

    abstract class Adapter<T> {
        abstract fun getItemCount(): Int

        abstract fun getItem(position: Int): T

        abstract fun bindView(item: T, view: ScheduleItem, position: Int)

        abstract fun bindEdit(item: T, view: ScheduleEdit, position: Int)

        abstract fun bindCreate(view: ScheduleEdit)
    }
}