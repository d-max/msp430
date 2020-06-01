package dmax.scara.android.present.locate

import dmax.scara.android.present.common.BaseViewModel
import dmax.scara.android.present.common.Data as BaseData
import dmax.scara.android.present.common.Event as BaseEvent

interface LocateContract {

    sealed class Event : BaseEvent {
        data class OnLocate(val x: Int, val y: Int) : Event()
    }

    object Data : BaseData

    abstract class Model : BaseViewModel<Event, Data>()

}
