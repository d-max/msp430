package dmax.scara.android.misc

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(liveData: LiveData<T>, consumer: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(consumer))
