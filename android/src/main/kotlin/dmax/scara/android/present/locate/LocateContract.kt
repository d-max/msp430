package dmax.scara.android.present.locate

import dmax.scara.android.present.common.BaseViewModel
import dmax.scara.android.present.common.Data as BaseData
import dmax.scara.android.present.common.Event as BaseEvent

interface LocateContract {

    sealed class Event : BaseEvent {
        object OnInit : Event()
        data class OnLocate(val x: Int, val y: Int) : Event()
    }

    sealed class Data : BaseData {
        data class Position(val x: Int, val y: Int) : Data()
    }

    abstract class Model : BaseViewModel<Event, Data>()

}
