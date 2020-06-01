package dmax.scara.android.present.control

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.actors.BendBaseCall
import dmax.scara.android.actors.BendElbowCall
import dmax.scara.android.actors.BendWristCall
import dmax.scara.android.present.control.ControlContract.Data
import dmax.scara.android.present.control.ControlContract.Event
import dmax.scara.android.present.control.ControlContract.Model
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ControlModel(
    private val bendBase: BendBaseCall,
    private val bendElbow: BendElbowCall,
    private val bendWrist: BendWristCall
) : Model() {

    private val scope = MainScope()
    override val data = MutableLiveData<Data>()

    override fun onCleared() {
        scope.cancel()
    }

    override fun event(e: Event) {
        scope.launch {
            when(e) {
                is Event.OnBaseControl -> bendBase(BendBaseCall.Input(e.angle))
                is Event.OnElbowControl -> bendElbow(BendElbowCall.Input(e.angle))
                is Event.OnWristControl -> bendWrist(BendWristCall.Input(e.angle))
            }
        }
    }
}
