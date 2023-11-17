package com.wittyneko.schedule.extensions

import android.content.res.Resources
import android.content.res.TypedArray
import androidx.annotation.DrawableRes
import com.wittyneko.schedule.R

/**
 * 计划代理类，存储各种属性
 * @author huangrenqiu
 * @since 2023/8/31 11:08
 */
class ScheduleDelegate {

    companion object {
        val ITEM_END_MODE_DEFAULT = 0
        val ITEM_END_MODE_USER_SET = 1
    }

    /**
     * 每次滑动最低间隔时间
     */
    var min_minute: Int = 0

    /**
     * 每个时段间隔高度
     */
    var item_space: Int = 0

    /**
     * 每个时段左边距
     */
    var item_margin_start: Int = 0

    /**
     * 每个时段右边距
     */
    var item_margin_end: Int = 0

    /**
     * 计划项北京
     */
    @DrawableRes var item_background: Int = 0

    /**
     * 计划项文本颜色
     */
    var item_text_color: Int = 0

    /**
     * 计划项文本大小
     */
    var item_text_size: Int = 0

    /**
     * 文本最大行数，默认为view可显示区域，当设置时，显示区域可显示行数小雨设置值时按显示区域
     */
    var item_text_max_lines: Int = -1

    /**
     * 计划项文本左边距
     */
    var item_text_margin_start: Int = 0

    /**
     * 计划项文本上边距
     */
    var item_text_margin_top: Int = 0

    /**
     * 计划项文本右边距
     */
    var item_text_margin_end: Int = 0

    /**
     * 计划项文本下边距
     */
    var item_text_margin_bottom: Int = 0

    /**
     * 计划项显示左侧icon
     */
    var item_show_left_icon = true

    /**
     * 计划项左侧icon资源
     */
    @DrawableRes var item_left_icon_src: Int = 0

    /**
     * 计划项完成的样式，默认系统系统样式，用户可设置为[ITEM_END_MODE_USER_SET]在[ScheduleView.Adapter]中自行设置
     */
    var item_end_mode = ITEM_END_MODE_DEFAULT

    /**
     * 计划项完成背景
     */
    @DrawableRes var item_end_background: Int = 0

    /**
     * 计划项完成文本颜色
     */
    var item_end_text_color: Int = 0

    /**
     * 计划项完成文本大小
     */
    var item_end_text_size: Int = 0

    /**
     * 计划项完成文本左边距
     */
    var item_end_text_margin_start: Int = 0

    /**
     * 计划项完成文本上边距
     */
    var item_end_text_margin_top: Int = 0

    /**
     * 计划项完成文本右边距
     */
    var item_end_text_margin_end: Int = 0

    /** 计划项完成文本下边距
     *
     */
    var item_end_text_margin_bottom: Int = 0

    /**
     * 计划项完成左侧icon
     */
    var item_end_show_left_icon = true

    /**
     * 计划项完成左侧icon资源
     */
    @DrawableRes var item_end_left_icon_src: Int = 0

    /**
     * 震动时长，单位毫秒
     */
    var vibrate_time = 5L

    /**
     * 是否支持编辑
     */
    var edit_enable = true

    /**
     * 编辑时是否支持震动
     */
    var edit_vibrate_enable = true

    /**
     * 编辑状态拖动时间是否支持震动
     */
    var edit_modify_date_vibrate_enable = true

    /**
     * 是否支持新建
     */
    var append_enable = true

    /**
     * 新建时是否支持震动
     */
    var append_vibrate_enable = true

    /**
     * 新建状态拖动时间是否支持震动
     */
    var append_modify_date_vibrate_enable = true

    /**
     * 编辑状态背景
     */
    @DrawableRes var edit_background: Int = 0

    /**
     * 编辑框左边距
     */
    var edit_margin_start: Int = 0

    /**
     * 编辑框右边距
     */
    var edit_margin_end: Int = 0

    /**
     * 编辑框圆点大小
     */
    var edit_point_size: Int = 0

    /**
     * 编辑框圆点资源
     */
    @DrawableRes var edit_point_src: Int = 0

    /**
     * 编辑框圆点边距
     */
    var edit_point_margin_start: Int = 0
    var edit_point_margin_end: Int = 0

    /**
     * 编辑框文本颜色
     */
    var edit_text_color: Int = 0

    /**
     * 编辑框文本大小
     */
    var edit_text_size: Int = 0

    /**
     * 编辑框文本左边距
     */
    var edit_text_margin_start: Int = 0

    /**
     * 编辑框文本上边距
     */
    var edit_text_margin_top: Int = 0

    /**
     * 编辑框文本右边距
     */
    var edit_text_margin_end: Int = 0

    /**
     * 编辑框文本下边距
     */
    var edit_text_margin_bottom: Int = 0

    /**
     * 编辑框开始时间文本颜色
     */
    var edit_start_text_color: Int = 0

    /**
     * 编辑框开始时间文本大小
     */
    var edit_start_text_size: Int = 0

    /**
     * 编辑框开始时间文本左边距
     */
    var edit_start_text_margin_start: Int = 0

    /**
     * 编辑框开始时间文本上边距
     */
    var edit_start_text_margin_top: Int = 0

    /**
     * 编辑框结束时间文本颜色
     */
    var edit_end_text_color: Int = 0

    /**
     * 编辑框结束时间文本大小
     */
    var edit_end_text_size: Int = 0

    /**
     * 编辑框结束时间文本左边距
     */
    var edit_end_text_margin_start: Int = 0

    /**
     * 编辑框结束时间文本下边距
     */
    var edit_end_text_margin_bottom: Int = 0

    /**
     * 当前时间线文本颜色
     */
    var current_time_text_color: Int = 0

    /**
     * 当前时间线文本大小
     */
    var current_time_text_size: Int = 0

    /**
     * 当前时间线文本左间距
     */
    var current_time_text_margin_start: Int = 0

    /**
     * 当前时间线是否显示圆点
     */
    var current_time_show_point = true

    /**
     * 当前时间线圆点资源
     */
    @DrawableRes var current_time_point_src: Int = 0

    /**
     * 当前时间线圆点宽度
     */
    var current_time_point_width: Int = 0

    /**
     * 当前时间线圆点高度
     */
    var current_time_point_height: Int = 0

    /**
     * 当前时间线圆点左间距
     */
    var current_time_point_margin_start: Int = 0

    /**
     * 当前时间线高度
     */
    var current_time_line_height: Int = 0

    /**
     * 当前时间线左边距
     */
    var current_time_line_margin_start: Int = 0

    /**
     * 当前时间线右边距
     */
    var current_time_line_margin_end: Int = 0

    /**
     * 当前时间线资源
     */
    var current_time_line_src: Int = 0

    /**
     * 时段字体颜色
     */
    var time_text_color: Int = 0

    /**
     * 时段字体大小
     */
    var time_text_size: Int = 0

    /**
     * 时段字体左间距
     */
    var time_text_margin_start: Int = 0

    /**
     * 时段线高度
     */
    var time_line_height: Int = 0

    /**
     * 时段线左间距
     */
    var time_line_margin_start: Int = 0

    /**
     * 时段线右间距
     */
    var time_line_margin_end: Int = 0

    /**
     * 时段线资源
     */
    var time_line_src: Int = 0

    private fun setDefault(resources: Resources) {
        min_minute = 15
        item_space = resources.idp(45)
        item_margin_start = resources.idp(56)
        item_margin_end = resources.idp(15)

        item_background = R.drawable.bg_schedule_item_now
        item_text_color = resources.color(R.color.color_2A2F3C)
        item_text_size = resources.isp(12)
        item_text_max_lines = -1
        item_text_margin_start = resources.idp(7)
        item_text_margin_top = resources.idp(3)
        item_text_margin_end = resources.idp(7)
        item_text_margin_bottom = resources.idp(3)
        item_show_left_icon = true
        item_left_icon_src = R.drawable.bg_schedule_item_now_left

        item_end_mode = ITEM_END_MODE_DEFAULT

        item_end_background = R.drawable.bg_schedule_item_past
        item_end_text_color = resources.color(R.color.color_BCC1CD)
        item_end_text_size = resources.isp(12)
        item_end_text_margin_start = resources.idp(7)
        item_end_text_margin_top = resources.idp(3)
        item_end_text_margin_end = resources.idp(7)
        item_end_text_margin_bottom = resources.idp(3)
        item_end_show_left_icon = true
        item_end_left_icon_src = R.drawable.bg_schedule_item_past_left

        vibrate_time = 5L
        edit_enable = true
        edit_vibrate_enable = true
        edit_modify_date_vibrate_enable = true
        append_enable = true
        append_vibrate_enable = true
        append_modify_date_vibrate_enable = true
        edit_background = R.drawable.bg_schedule_edit
        edit_margin_start = resources.idp(56)
        edit_margin_end = resources.idp(15)
        edit_point_size = resources.idp(6)
        edit_point_src = R.drawable.bg_schedule_point_edit
        edit_point_margin_start = resources.idp(28)
        edit_point_margin_end = resources.idp(28)

        edit_text_color = resources.color(R.color.color_FFFFFF)
        edit_text_size = resources.isp(12)
        edit_text_margin_start = resources.idp(7)
        edit_text_margin_top = resources.idp(3)
        edit_text_margin_end = resources.idp(7)
        edit_text_margin_bottom = resources.idp(3)
        edit_start_text_color = resources.color(R.color.color_A0006B14)
        edit_start_text_size = resources.isp(12)
        edit_start_text_margin_start = resources.idp(16)
        edit_start_text_margin_top = resources.idp(-8)
        edit_end_text_color = resources.color(R.color.color_A0006B14)
        edit_end_text_size = resources.isp(12)
        edit_end_text_margin_start = resources.idp(16)
        edit_end_text_margin_bottom = resources.idp(8)

        current_time_text_color = resources.color(R.color.color_F55656)
        current_time_text_size = resources.isp(12)
        current_time_text_margin_start = resources.idp(16)
        current_time_show_point = true
        current_time_point_src = R.drawable.bg_schedule_point_current_time
        current_time_point_width = resources.idp(6)
        current_time_point_height = resources.idp(6)
        current_time_point_margin_start = resources.idp(48)
        current_time_line_height = resources.idp(1)
        current_time_line_margin_start = resources.idp(50)
        current_time_line_margin_end = resources.idp(15)
        current_time_line_src = resources.color(R.color.color_F55656)

        time_text_color = resources.color(R.color.color_BCC1CD)
        time_text_size = resources.isp(12)
        time_line_height = resources.isp(1)
        time_text_margin_start = resources.idp(16)
        time_line_margin_start = resources.idp(56)
        time_line_margin_end = resources.idp(15)
        time_line_src = resources.color(R.color.color_DCE0E8)
    }

    fun initTypedArray(resources: Resources, typedArray: TypedArray) {
        setDefault(resources)

        min_minute = typedArray.getInteger(R.styleable.ScheduleView_min_minute, min_minute)

        item_space = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_space, item_space)
        item_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_margin_start, item_margin_start)
        item_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_margin_end, item_margin_end)

        item_background = typedArray.getResourceId(R.styleable.ScheduleView_item_background, item_background)
        item_text_color = typedArray.getColor(R.styleable.ScheduleView_item_text_color, item_text_color)
        item_text_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_size, item_text_size)
        item_text_max_lines = typedArray.getInteger(R.styleable.ScheduleView_item_text_max_lines, item_text_max_lines)
        item_text_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_margin_start, item_text_margin_start)
        item_text_margin_top = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_margin_top, item_text_margin_top)
        item_text_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_margin_end, item_text_margin_end)
        item_text_margin_bottom = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_margin_bottom, item_text_margin_bottom)
        item_show_left_icon = typedArray.getBoolean(R.styleable.ScheduleView_item_show_left_icon, item_show_left_icon)
        item_left_icon_src = typedArray.getResourceId(R.styleable.ScheduleView_item_left_icon_src, item_left_icon_src)

        item_end_mode = typedArray.getInteger(R.styleable.ScheduleView_item_end_mode, item_end_mode)
        item_end_background = typedArray.getResourceId(R.styleable.ScheduleView_item_end_background, item_end_background)
        item_end_text_color = typedArray.getColor(R.styleable.ScheduleView_item_end_text_color, item_end_text_color)
        item_end_left_icon_src = typedArray.getResourceId(R.styleable.ScheduleView_item_end_left_icon_src, item_end_left_icon_src)

        vibrate_time = typedArray.getInteger(R.styleable.ScheduleView_vibrate_time, 5) * 1L
        edit_enable = typedArray.getBoolean(R.styleable.ScheduleView_edit_enable, edit_enable)
        edit_vibrate_enable = typedArray.getBoolean(R.styleable.ScheduleView_edit_vibrate_enable, edit_vibrate_enable)
        edit_modify_date_vibrate_enable = typedArray.getBoolean(R.styleable.ScheduleView_edit_modify_date_vibrate_enable, edit_modify_date_vibrate_enable)
        append_enable = typedArray.getBoolean(R.styleable.ScheduleView_append_enable, append_enable)
        append_vibrate_enable = typedArray.getBoolean(R.styleable.ScheduleView_append_vibrate_enable, append_vibrate_enable)
        append_modify_date_vibrate_enable = typedArray.getBoolean(R.styleable.ScheduleView_append_modify_date_vibrate_enable, append_modify_date_vibrate_enable)
        edit_background = typedArray.getResourceId(R.styleable.ScheduleView_edit_background, edit_background)
        edit_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_margin_start, edit_margin_start)
        edit_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_margin_end, edit_margin_end)
        edit_point_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_point_size, edit_point_size)
        edit_point_src = typedArray.getResourceId(R.styleable.ScheduleView_edit_point_src, edit_point_src)
        edit_point_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_point_margin_start, edit_point_margin_start)
        edit_point_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_point_margin_end, edit_point_margin_end)

        edit_text_color = typedArray.getColor(R.styleable.ScheduleView_edit_text_color, edit_text_color)
        edit_text_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_text_size, edit_text_size)
        edit_text_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_text_margin_start, edit_text_margin_start)
        edit_text_margin_top = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_text_margin_top, edit_text_margin_top)
        edit_text_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_text_margin_end, edit_text_margin_end)
        edit_text_margin_bottom = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_text_margin_bottom, edit_text_margin_bottom)
        edit_start_text_color = typedArray.getColor(R.styleable.ScheduleView_edit_start_text_color, edit_start_text_color)
        edit_start_text_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_start_text_size, edit_start_text_size)
        edit_start_text_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_start_text_margin_start, edit_start_text_margin_start)
        edit_start_text_margin_top = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_start_text_margin_top, edit_start_text_margin_top)
        edit_end_text_color = typedArray.getColor(R.styleable.ScheduleView_edit_end_text_color, edit_end_text_color)
        edit_end_text_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_end_text_size, edit_end_text_size)
        edit_end_text_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_end_text_margin_start, edit_end_text_margin_start)
        edit_end_text_margin_bottom = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_end_text_margin_bottom, edit_end_text_margin_bottom)

        current_time_text_color = typedArray.getColor(R.styleable.ScheduleView_current_time_text_color, current_time_text_color)
        current_time_text_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_text_size, current_time_text_size)
        current_time_text_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_text_margin_start, current_time_text_margin_start)
        current_time_show_point = typedArray.getBoolean(R.styleable.ScheduleView_current_time_show_point, current_time_show_point)
        current_time_point_src = typedArray.getResourceId(R.styleable.ScheduleView_current_time_point_src, current_time_point_src)
        current_time_point_width = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_point_width, current_time_point_width)
        current_time_point_height = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_point_height, current_time_point_height)
        current_time_point_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_point_margin_start, current_time_point_margin_start)
        current_time_line_height = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_line_height, current_time_line_height)
        current_time_line_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_line_margin_start, current_time_line_margin_start)
        current_time_line_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_line_margin_end, current_time_line_margin_end)
        current_time_line_src = typedArray.getColor(R.styleable.ScheduleView_current_time_line_src, current_time_line_src)

        time_text_color = typedArray.getColor(R.styleable.ScheduleView_time_text_color, time_text_color)
        time_text_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_text_size, time_text_size)
        time_text_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_text_margin_start, time_text_margin_start)
        time_line_height = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_line_height, time_line_height)
        time_line_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_line_margin_start, time_line_margin_start)
        time_line_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_line_margin_end, time_line_margin_end)
        time_line_src = typedArray.getColor(R.styleable.ScheduleView_time_line_src, time_line_src)

        typedArray.recycle()
    }
}