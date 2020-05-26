package dmax.scara.android.present.home

import dmax.scara.android.present.common.BaseViewModel
import dmax.scara.android.present.common.Data as BaseData
import dmax.scara.android.present.common.Event as BaseEvent

interface HomeContract {

    sealed class Event : BaseEvent {
        object Power : Event()
    }

    sealed class Data : BaseData {
        object Connecting : Data()
        object Connected : Data()
        object Disconnected : Data()
        object Error : Data()
    }

    abstract class Model : BaseViewModel<Event, Data>()

}


