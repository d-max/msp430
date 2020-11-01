package dmax.scara.android.present.shapes

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.actions.motion.BendBaseInfiniteActor
import dmax.scara.android.actions.motion.BendElbowInfiniteActor
import dmax.scara.android.actions.motion.BendWristInfiniteActor
import dmax.scara.android.actions.motion.ClickActor
import dmax.scara.android.present.shapes.ShapesContract.Data
import dmax.scara.android.present.shapes.ShapesContract.Event
import dmax.scara.android.present.shapes.ShapesContract.Model
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class ShapesModel(
    private val click: ClickActor,
    private val bendBaseInfiniteActor: BendBaseInfiniteActor,
    private val bendElbowInfiniteActor: BendElbowInfiniteActor,
    private val bendWristInfiniteActor: BendWristInfiniteActor,
) : Model() {

    private val job = Job()
    private val scope = MainScope() + job
    override val data = MutableLiveData<Data>()

    override fun onCleared() {
        scope.cancel()
    }

    override fun event(event: Event) {
        when(event) {
            Event.Stop -> job.cancelChildren()
            Event.Click -> scope.launch { click() }
            Event.BendBaseInfinite -> scope.launch { bendBaseInfiniteActor() }
            Event.BendElbowInfinite -> scope.launch { bendElbowInfiniteActor() }
            Event.BendWristInfinite -> scope.launch { bendWristInfiniteActor() }
            else -> Unit
//            Event.DrawCircle -> Unit
//            Event.DrawSquare -> Unit
//            Event.DrawTriangle -> Unit
        }
    }
}
