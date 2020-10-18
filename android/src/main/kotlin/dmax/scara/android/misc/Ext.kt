package dmax.scara.android.misc

import android.graphics.Point
import android.graphics.PointF
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dmax.scara.android.R
import dmax.scara.android.present.common.LocateView

fun <T> Fragment.observe(liveData: LiveData<T>, consumer: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(consumer))

fun <T : View> Fragment.view(@IdRes id: Int) = lazy { requireView().findViewById<T>(id) }

fun PointF.toPoint() = Point(x.toInt(), y.toInt())
operator fun Point.component1() = x
operator fun Point.component2() = y
