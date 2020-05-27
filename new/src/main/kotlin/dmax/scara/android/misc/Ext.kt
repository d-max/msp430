package dmax.scara.android.misc

import android.graphics.Point
import android.graphics.PointF
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(liveData: LiveData<T>, consumer: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(consumer))

fun PointF.toPoint() = Point(x.toInt(), y.toInt())
operator fun Point.component1() = x
operator fun Point.component2() = y
