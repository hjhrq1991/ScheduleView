package com.wittyneko.schedule.adapter

import com.wittyneko.schedule.widget.ScheduleEdit
import com.wittyneko.schedule.widget.ScheduleItem
import com.wittyneko.schedule.widget.ScheduleView
import org.joda.time.Period

/**
 * 适配器基类
 * @author huangrenqiu
 * @since 2023/8/31 14:23
 */
abstract class BaseScheduleAdapter<T>(
    private val scheduleView: ScheduleView
) : ScheduleView.Adapter<Triple<Period, Period, T>>() {

    private val mList = mutableListOf<Triple<Period, Period, T>>()

    fun addAll(elem: List<Triple<Period, Period, T>>?): Boolean {
        return elem.takeIf { !it.isNullOrEmpty() }?.let {
            mList.addAll(it)
            notifyAllChange()
            true
        } ?: false
    }

    fun addAll(index: Int, elem: List<Triple<Period, Period, T>>?): Boolean {
        return elem.takeIf { !it.isNullOrEmpty() }?.let {
            mList.addAll(index, it)
            notifyAllChange()
            true
        } ?: false
    }

    fun remove(elem: Triple<Period, Period, T>) {
        mList.remove(elem)
        notifyAllChange()
    }

    fun add(elem: Triple<Period, Period, T>) {
        mList.add(elem)
        notifyAllChange()
    }

    fun add(index: Int, elem: Triple<Period, Period, T>) {
        mList.add(index, elem)
        notifyAllChange()
    }

    fun remove(index: Int) {
        mList.removeAt(index)
        notifyAllChange()
    }

    fun clear() {
        mList.clear()
        notifyAllChange()
    }

    fun replace(elem: Triple<Period, Period, T>) {
        mList.clear()
        mList.add(elem)
        notifyAllChange()
    }

    fun set(index: Int, elem: Triple<Period, Period, T>) {
        mList[index] = elem
        notifyAllChange()
    }

    fun replaceAll(elem: List<Triple<Period, Period, T>>?) {
        mList.clear()
        if (elem != null) {
            mList.addAll(elem)
        }
        notifyAllChange()
    }

    fun removeAll() {
        mList.clear()
        notifyAllChange()
    }

    override fun getItemCount(): Int = mList.size

    override fun getItem(position: Int): Triple<Period, Period, T> = mList[position]

    fun getItemData(position: Int): T = mList[position].third

    override fun bindView(item: Triple<Period, Period, T>, view: ScheduleItem, position: Int) {
        view.startPeriod = item.first
        view.endPeriod = item.second

        getView(item.third, view, position)
    }

    /**
     * 设置item样式
     */
    abstract fun getView(item: T, view: ScheduleItem, position: Int)

    override fun bindEdit(item: Triple<Period, Period, T>, view: ScheduleEdit, position: Int) {
        view.startPeriod = item.first
        view.endPeriod = item.second

        getEditView(item.third, view, position)
    }

    /**
     * 设置编辑框样式
     */
    abstract fun getEditView(item: T, view: ScheduleEdit, position: Int)

    abstract override fun bindCreate(view: ScheduleEdit)

    fun notifyAllChange() {
        // 编辑Item先退出编辑
        if (scheduleView.editView.isShow) {
            scheduleView.cancelEdit()
        }
        scheduleView.notifyAllItem()
    }
}