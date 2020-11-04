package dmax.scara.android.present.control

import dmax.scara.android.present.common.BaseViewModel
import dmax.scara.android.present.common.Data as BaseData
import dmax.scara.android.present.common.Event as BaseEvent

interface ControlContract {

    sealed class Event : BaseEvent {
        object OnInit : Event()
        sealed class SetAngleEvent(open val angle: Int) : Event() {
            data class OnBaseControl(override val angle: Int) : SetAngleEvent(angle)
            data class OnElbowControl(override val angle: Int) : SetAngleEvent(angle)
            data class OnWristControl(override val angle: Int) : SetAngleEvent(angle)
        }
    }

    sealed class Data : BaseData {
        data class Angles(val base: Int, val elbow: Int, val wrist: Int) : Data()
    }

    abstract class Model : BaseViewModel<Event, Data>()

}
