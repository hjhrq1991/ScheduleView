package com.wittyneko.schedule.extensions

import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import com.wittyneko.schedule.R

/**
 * 计划代理类，存储各种属性
 * @author huangrenqiu
 * @since 2023/8/31 11:08
 */
class ScheduleDelegate {

    companion object {
        val instance: ScheduleDelegate by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ScheduleDelegate()
        }

        val ITEM_END_MODE_DEFAULT = 0
        val ITEM_END_MODE_USER_SET = 1
    }

    var min_minute: Int = 0
    var item_space: Int = 0
    var item_margin_start: Int = 0
    var item_margin_end: Int = 0

    lateinit var item_background: Drawable
    var item_text_color: Int = 0
    var item_text_size: Int = 0
    var item_text_margin_start: Int = 0
    var item_text_margin_top: Int = 0
    var item_text_margin_end: Int = 0
    var item_text_margin_bottom: Int = 0
    var item_show_left_icon = true
    lateinit var item_left_icon_src: Drawable

    var item_end_mode = ITEM_END_MODE_DEFAULT

    lateinit var item_end_background: Drawable
    var item_end_text_color: Int = 0
    var item_end_text_size: Int = 0
    var item_end_text_margin_start: Int = 0
    var item_end_text_margin_top: Int = 0
    var item_end_text_margin_end: Int = 0
    var item_end_text_margin_bottom: Int = 0
    var item_end_show_left_icon = true
    lateinit var item_end_left_icon_src: Drawable

    var edit_enable = true
    var append_enable = true
    lateinit var edit_background: Drawable
    var edit_margin_start: Int = 0
    var edit_margin_end: Int = 0
    var edit_point_size: Int = 0
    lateinit var edit_point_src: Drawable
    var edit_text_color: Int = 0
    var edit_text_size: Int = 0
    var edit_text_margin_start: Int = 0
    var edit_text_margin_top: Int = 0
    var edit_text_margin_end: Int = 0
    var edit_text_margin_bottom: Int = 0
    var edit_start_text_color: Int = 0
    var edit_start_text_size: Int = 0
    var edit_start_text_margin_start: Int = 0
    var edit_start_text_margin_top: Int = 0
    var edit_end_text_color: Int = 0
    var edit_end_text_size: Int = 0
    var edit_end_text_margin_start: Int = 0
    var edit_end_text_margin_bottom: Int = 0

    var current_time_text_color: Int = 0
    var current_time_text_size: Int = 0
    var current_time_text_margin_start: Int = 0
    var current_time_show_point = true
    lateinit var current_time_point_src: Drawable
    var current_time_point_width: Int = 0
    var current_time_point_height: Int = 0
    var current_time_point_margin_start: Int = 0
    var current_time_line_margin_start: Int = 0
    var current_time_line_margin_end: Int = 0
    var current_time_line_src: Int = 0

    var time_text_color: Int = 0
    var time_text_size: Int = 0
    var time_text_margin_start: Int = 0
    var time_line_margin_start: Int = 0
    var time_line_margin_end: Int = 0
    var time_line_src: Int = 0

    private fun setDefault(resources: Resources) {
        min_minute = 15
        item_space = resources.idp(45)
        item_margin_start = resources.idp(56)
        item_margin_end = resources.idp(15)

        item_background = resources.drawable(R.drawable.bg_schedule_item_now)
        item_text_color = resources.color(R.color.color_2A2F3C)
        item_text_size = resources.isp(12)
        item_text_margin_start = resources.idp(7)
        item_text_margin_top = resources.idp(3)
        item_text_margin_end = resources.idp(7)
        item_text_margin_bottom = resources.idp(3)
        item_show_left_icon = true
        item_left_icon_src = resources.drawable(R.drawable.bg_schedule_item_now_left)

        item_end_mode = ITEM_END_MODE_DEFAULT

        item_end_background = resources.drawable(R.drawable.bg_schedule_item_past)
        item_end_text_color = resources.color(R.color.color_BCC1CD)
        item_end_text_size = resources.isp(12)
        item_end_text_margin_start = resources.idp(7)
        item_end_text_margin_top = resources.idp(3)
        item_end_text_margin_end = resources.idp(7)
        item_end_text_margin_bottom = resources.idp(3)
        item_end_show_left_icon = true
        item_end_left_icon_src = resources.drawable(R.drawable.bg_schedule_item_past_left)

        edit_enable = true
        append_enable = true
        edit_background = resources.drawable(R.drawable.bg_schedule_edit)
        edit_margin_start = resources.idp(56)
        edit_margin_end = resources.idp(15)
        edit_point_size = resources.idp(6)
        edit_point_src = resources.drawable(R.drawable.bg_schedule_point_edit)
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
        edit_end_text_margin_bottom = resources.idp(-8)

        current_time_text_color = resources.color(R.color.color_F55656)
        current_time_text_size = resources.isp(12)
        current_time_text_margin_start = resources.idp(16)
        current_time_show_point = true
        current_time_point_src = resources.drawable(R.drawable.bg_schedule_point_current_time)
        current_time_point_width = resources.idp(6)
        current_time_point_height = resources.idp(6)
        current_time_point_margin_start = resources.idp(48)
        current_time_line_margin_start = resources.idp(50)
        current_time_line_margin_end = resources.idp(15)
        current_time_line_src = resources.color(R.color.color_F55656)

        time_text_color = resources.color(R.color.color_BCC1CD)
        time_text_size = resources.isp(12)
        time_text_margin_start = resources.idp(16)
        time_line_margin_start = resources.idp(56)
        time_line_margin_end = resources.idp(15)
        time_line_src = resources.color(R.color.color_DCE0E8)
    }

    fun initTypedArray(resources: Resources, typedArray: TypedArray){
        setDefault(resources)

        min_minute = typedArray.getInteger(R.styleable.ScheduleView_min_minute, min_minute)

        item_space = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_space, item_space)
        item_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_margin_start, item_margin_start)
        item_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_margin_end, item_margin_end)

        item_background = typedArray.getDrawable(R.styleable.ScheduleView_item_background).takeIf { it!=null } ?: item_background
        item_text_color = typedArray.getColor(R.styleable.ScheduleView_item_text_color, item_text_color)
        item_text_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_size, item_text_size)
        item_text_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_margin_start, item_text_margin_start)
        item_text_margin_top = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_margin_top, item_text_margin_top)
        item_text_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_margin_end, item_text_margin_end)
        item_text_margin_bottom = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_item_text_margin_bottom, item_text_margin_bottom)
        item_show_left_icon = typedArray.getBoolean(R.styleable.ScheduleView_item_show_left_icon, item_show_left_icon)
        item_left_icon_src = typedArray.getDrawable(R.styleable.ScheduleView_item_left_icon_src).takeIf { it!=null } ?: item_left_icon_src

        item_end_mode = typedArray.getInteger(R.styleable.ScheduleView_item_end_mode, item_end_mode)
        item_end_background = typedArray.getDrawable(R.styleable.ScheduleView_item_end_background).takeIf { it!=null } ?: item_end_background
        item_end_text_color = typedArray.getColor(R.styleable.ScheduleView_item_end_text_color, item_end_text_color)
        item_end_left_icon_src = typedArray.getDrawable(R.styleable.ScheduleView_item_end_left_icon_src).takeIf { it!=null } ?: item_end_left_icon_src

        edit_enable = typedArray.getBoolean(R.styleable.ScheduleView_edit_enable, edit_enable)
        append_enable = typedArray.getBoolean(R.styleable.ScheduleView_append_enable, append_enable)
        edit_background = typedArray.getDrawable(R.styleable.ScheduleView_edit_background).takeIf { it!=null } ?: edit_background
        edit_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_margin_start, edit_margin_start)
        edit_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_margin_end, edit_margin_end)
        edit_point_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_edit_point_size, edit_point_size)
        edit_point_src = typedArray.getDrawable(R.styleable.ScheduleView_edit_point_src).takeIf { it!=null } ?: edit_point_src
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
        current_time_point_src = typedArray.getDrawable(R.styleable.ScheduleView_current_time_point_src).takeIf { it!=null } ?: current_time_point_src
        current_time_point_width = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_point_width, current_time_point_width)
        current_time_point_height = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_point_height, current_time_point_height)
        current_time_point_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_point_margin_start, current_time_point_margin_start)
        current_time_line_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_line_margin_start, current_time_line_margin_start)
        current_time_line_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_current_time_line_margin_end, current_time_line_margin_end)
        current_time_line_src = typedArray.getColor(R.styleable.ScheduleView_current_time_line_src, current_time_line_src)

        time_text_color = typedArray.getColor(R.styleable.ScheduleView_time_text_color, time_text_color)
        time_text_size = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_text_size, time_text_size)
        time_text_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_text_margin_start, time_text_margin_start)
        time_line_margin_start = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_line_margin_start, time_line_margin_start)
        time_line_margin_end = typedArray.getDimensionPixelSize(R.styleable.ScheduleView_time_line_margin_end, time_line_margin_end)
        time_line_src = typedArray.getColor(R.styleable.ScheduleView_time_line_src, time_line_src)

        typedArray.recycle()
    }
}