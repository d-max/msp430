package dmax.scara.android.misc

import android.app.Activity
import android.util.DisplayMetrics
import kotlin.math.round

class ViewMeasure(activity: Activity) {

    private val cmPerInch = 2.54f
    private val metrics: DisplayMetrics = DisplayMetrics()

    init {
        activity.windowManager.defaultDisplay.getMetrics(metrics)
    }

    fun widthInCm(pixels: Int): Float {
        val xInches = pixels / metrics.xdpi
        val xMm = xInches * cmPerInch
        return round(xMm)
    }

    fun heightInCm(pixels: Int): Float {
        val yInches = pixels / metrics.ydpi
        return yInches * cmPerInch
    }
}
