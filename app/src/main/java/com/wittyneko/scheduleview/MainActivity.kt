package com.wittyneko.scheduleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wittyneko.schedule.extensions.asView
import com.wittyneko.schedule.widget.ScheduleEdit
import com.wittyneko.schedule.widget.ScheduleItem
import com.wittyneko.schedule.widget.ScheduleView
import com.wittyneko.scheduleview.adapter.ScheduleAdapter
import org.joda.time.Period

class MainActivity : AppCompatActivity() {

    private val mScheduleView by lazy { findViewById<ScheduleView>(R.id.schedule_view) }

    private val mAdapter by lazy { ScheduleAdapter(mScheduleView) }

    // 日程创建
    private val onCreateClickListener = View.OnClickListener {
        val edit = it.asView<ScheduleEdit>()
        mAdapter.apply {
            add(Triple(edit.startPeriod, edit.endPeriod, "计划${getItemCount() + 1}"))
        }
    }

    // 日程点击监听
    private val onItemClickListener = { view: ScheduleItem, position: Int ->
        Toast.makeText(this, "${view.tvContent.text}", Toast.LENGTH_SHORT).show()
    }

    // 日程修改监听
    private val onItemChangeListener = listener@{ view: ScheduleItem, position: Int ->
        val item = mAdapter.getItem(position)
        mAdapter.set(position, item.copy(view.startPeriod, view.endPeriod))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initScheduleView()
    }

    private fun initScheduleView() {
        mScheduleView.onCreateClickListener = onCreateClickListener
        mScheduleView.onItemClickListener = onItemClickListener
        mScheduleView.onItemChangeListener = onItemChangeListener
        mScheduleView.setAdapter(mAdapter)
        mAdapter.replaceAll(
            listOf(
                Triple(Period.hours(3), Period.hours(4), "日程1"),
                Triple(Period.hours(3).withMinutes(30), Period.hours(4).withMinutes(30), "日程2"),
                Triple(Period.hours(3).withMinutes(45), Period.hours(5), "日程3"),
                Triple(Period.hours(5).withMinutes(30), Period.hours(7), "日程4"),
                Triple(Period.hours(7), Period.hours(9), "日程5"),
                Triple(Period.hours(7).withMinutes(8), Period.hours(9), "日程6"),
            )
        )
    }
}