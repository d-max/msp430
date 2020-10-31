package dmax.scara.android.present.shapes

import dmax.scara.android.present.common.BaseViewModel
import dmax.scara.android.present.common.Data as BaseData
import dmax.scara.android.present.common.Event as BaseEvent

interface ShapesContract {

    sealed class Event : BaseEvent {
        object OnCLick : Event()
        object DrawCircle : Event()
        object DrawSquare: Event()
        object DrawTriangle: Event()
    }

    object Data : BaseData

    abstract class Model : BaseViewModel<Event, Data>()

}
