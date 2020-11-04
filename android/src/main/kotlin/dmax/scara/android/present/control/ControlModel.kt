package dmax.scara.android.present.control

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.actions.motion.BendBaseCall
import dmax.scara.android.actions.motion.BendElbowCall
import dmax.scara.android.actions.motion.BendWristCall
import dmax.scara.android.actions.state.AnglesRequest
import dmax.scara.android.present.control.ControlContract.Data
import dmax.scara.android.present.control.ControlContract.Event
import dmax.scara.android.present.control.ControlContract.Model
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ControlModel(
    private val getAngles: AnglesRequest,
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
                Event.OnInit -> scope.launch {
                    val (base, elbow, wrist) = getAngles()
                    data.postValue(Data.Angles(base, elbow, wrist))
                }
                is Event.SetAngleEvent.OnBaseControl -> bendBase(BendBaseCall.Input(event.angle))
                is Event.SetAngleEvent.OnElbowControl -> bendElbow(BendElbowCall.Input(event.angle))
                is Event.SetAngleEvent.OnWristControl -> bendWrist(BendWristCall.Input(event.angle))
            }
        }
    }
}
