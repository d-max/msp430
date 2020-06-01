package dmax.scara.android.present.locate

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.actors.MoveCall
import dmax.scara.android.present.locate.LocateContract.Data
import dmax.scara.android.present.locate.LocateContract.Event
import dmax.scara.android.present.locate.LocateContract.Model
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LocateModel(
    private val move: MoveCall
) : Model() {

    private val scope = MainScope()
    override val data = MutableLiveData<Data>()

    override fun onCleared() {
        scope.cancel()
    }

    override fun event(event: Event) {
        (event as? Event.OnLocate)?.let {
            scope.launch {
                move(MoveCall.Input(event.x, event.y))
            }
        }
    }
}
