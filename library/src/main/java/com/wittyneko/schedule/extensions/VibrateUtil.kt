package com.wittyneko.schedule.extensions

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.Vibrator


/**
 * 震动管理工具
 * @author huangrenqiu
 * @since 2023/10/31 13:54
 */
object VibrateUtils {

    /**
     * 震动milliseconds毫秒
     */
    fun vibrate(context: Context, milliseconds: Long) {
        getVibrate(context).vibrate(milliseconds)
    }


    /**
     * 以pattern[]方式震动
     * @param repeat -1 不重复  0一直震动
     */
    fun vibrate(context: Context, pattern: LongArray, repeat: Int) {
        getVibrate(context).vibrate(pattern, repeat)
    }

    /**
     * 取消震动
     */
    fun cancelVibrate(context: Context) {
        kotlin.runCatching {
            getVibrate(context).cancel()
        }.onFailure {
            it.printStackTrace()
        }
    }

    /**
     * 获取震动器
     */
    fun getVibrate(context: Context) = Build.VERSION.SDK_INT.takeIf { it >= 31 }?.let {
//        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
//        vibratorManager.defaultVibrator
        context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    } ?: context.getSystemService(VIBRATOR_SERVICE) as Vibrator
}