package dmax.scara.android.present.control

import dmax.scara.android.present.common.BaseViewModel
import dmax.scara.android.present.common.Data as BaseData
import dmax.scara.android.present.common.Event as BaseEvent

interface ControlContract {

    sealed class Event(open val angle: Int) : BaseEvent {
        data class OnBaseControl(override val angle: Int) : Event(angle)
        data class OnElbowControl(override val angle: Int) : Event(angle)
        data class OnWristControl(override val angle: Int) : Event(angle)
    }

    sealed class Data : BaseData

    abstract class Model : BaseViewModel<Event, Data>()

}
