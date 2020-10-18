package dmax.scara.android.present.control

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.actions.motion.BendBaseCall
import dmax.scara.android.actions.motion.BendElbowCall
import dmax.scara.android.actions.motion.BendWristCall
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

    override fun event(event: Event) {
        scope.launch {
            when(event) {
                is Event.OnBaseControl -> bendBase(BendBaseCall.Input(event.angle))
                is Event.OnElbowControl -> bendElbow(BendElbowCall.Input(event.angle))
                is Event.OnWristControl -> bendWrist(BendWristCall.Input(event.angle))
            }
        }
    }
}
