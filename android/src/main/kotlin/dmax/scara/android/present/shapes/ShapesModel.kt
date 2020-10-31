package dmax.scara.android.present.shapes

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.actions.motion.ClickActor
import dmax.scara.android.present.shapes.ShapesContract.Data
import dmax.scara.android.present.shapes.ShapesContract.Event
import dmax.scara.android.present.shapes.ShapesContract.Model
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShapesModel(
    private val click: ClickActor,
) : Model() {

    private val scope = MainScope()
    override val data = MutableLiveData<Data>()

    override fun onCleared() {
        scope.cancel()
    }

    override fun event(event: Event) {
        when(event) {
            Event.OnCLick -> scope.launch { click() }
            else -> Unit
//            Event.DrawCircle -> Unit
//            Event.DrawSquare -> Unit
//            Event.DrawTriangle -> Unit
        }
    }
}
