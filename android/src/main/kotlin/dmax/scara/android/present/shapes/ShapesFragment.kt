package dmax.scara.android.present.shapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import dmax.scara.android.R
import dmax.scara.android.present.misc.view
import dmax.scara.android.present.shapes.ShapesContract.Event.BendBaseInfinite
import dmax.scara.android.present.shapes.ShapesContract.Event.BendElbowInfinite
import dmax.scara.android.present.shapes.ShapesContract.Event.BendWristInfinite
import dmax.scara.android.present.shapes.ShapesContract.Event.Click
import dmax.scara.android.present.shapes.ShapesContract.Event.DrawCircle
import dmax.scara.android.present.shapes.ShapesContract.Event.DrawSquare
import dmax.scara.android.present.shapes.ShapesContract.Event.DrawTriangle
import dmax.scara.android.present.shapes.ShapesContract.Event.Stop
import org.koin.android.viewmodel.ext.android.viewModel

class ShapesFragment : Fragment() {

    private val model: ShapesContract.Model by viewModel()
    private val stopButton: MaterialButton by view(R.id.button_stop)
    private val clickButton: MaterialButton by view(R.id.button_click)
    private val bendBaseButton: MaterialButton by view(R.id.button_bend_base)
    private val bendElbowButton: MaterialButton by view(R.id.button_bend_elbow)
    private val bendWristButton: MaterialButton by view(R.id.button_bend_wrist)
    private val drawCircleButton: MaterialButton by view(R.id.button_circle)
    private val drawSquareButton: MaterialButton by view(R.id.button_square)
    private val drawTriangleButton: MaterialButton by view(R.id.button_triangle)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shapes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stopButton.bindEvent(Stop)
        clickButton.bindEvent(Click)
        bendBaseButton.bindEvent(BendBaseInfinite)
        bendElbowButton.bindEvent(BendElbowInfinite)
        bendWristButton.bindEvent(BendWristInfinite)
        drawCircleButton.bindEvent(DrawCircle)
        drawSquareButton.bindEvent(DrawSquare)
        drawTriangleButton.bindEvent(DrawTriangle)
    }

    private fun MaterialButton.bindEvent(event: ShapesContract.Event) = setOnClickListener { model.event(event) }
}
