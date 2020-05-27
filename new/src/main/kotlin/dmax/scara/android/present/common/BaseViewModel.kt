package dmax.scara.android.present.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

interface Event
interface Data

abstract class BaseViewModel<E : Event, D : Data> : ViewModel() {
    abstract val data: LiveData<D>
    abstract fun event(e: E)
}
