package com.wittyneko.scheduleview.adapter

import com.wittyneko.schedule.adapter.BaseScheduleAdapter
import com.wittyneko.schedule.widget.ScheduleEdit
import com.wittyneko.schedule.widget.ScheduleItem
import com.wittyneko.schedule.widget.ScheduleView
import org.joda.time.Period

class ScheduleAdapter(view: ScheduleView) : BaseScheduleAdapter<String>(view) {

    override fun getView(item: String, view: ScheduleItem, position: Int) {
        view.tvContent.text = item
    }

    override fun getEditView(item: String, view: ScheduleEdit, position: Int) {
        view.tvContent.text = item
    }

    override fun bindCreate(view: ScheduleEdit) {
        view.tvContent.text = "新建日程"
    }
}