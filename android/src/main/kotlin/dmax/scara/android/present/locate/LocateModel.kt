package dmax.scara.android.present.locate

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.actions.motion.MoveCall
import dmax.scara.android.actions.state.PositionRequest
import dmax.scara.android.present.locate.LocateContract.Data
import dmax.scara.android.present.locate.LocateContract.Event
import dmax.scara.android.present.locate.LocateContract.Model
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LocateModel(
    private val move: MoveCall,
    private val locate: PositionRequest,
) : Model() {

    private val scope = MainScope()
    override val data = MutableLiveData<Data>()

    override fun onCleared() {
        scope.cancel()
    }

    override fun event(event: Event) {
        when (event) {
            Event.OnInit -> scope.launch {
                val (x, y) = locate()
                data.postValue(Data.Position(x, y))
            }
            is Event.OnLocate -> scope.launch {
                move(MoveCall.Input(event.x, event.y))
            }
        }
    }
}
