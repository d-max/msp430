package dmax.scara.android.misc

import android.app.Activity
import android.util.DisplayMetrics
import kotlin.math.round

class ViewMeasure(activity: Activity) {

    private val mmPerInch = 25.4f
    private val metrics: DisplayMetrics = DisplayMetrics()

    init {
        activity.windowManager.defaultDisplay.getMetrics(metrics)
    }

    fun width2mm(pixels: Int): Float {
        val xInches = pixels / metrics.xdpi
        val xMm = xInches * mmPerInch
        return round(xMm)
    }

    fun height2mm(pixels: Int): Float {
        val yInches = pixels / metrics.ydpi
        return yInches * mmPerInch
    }
}
