package dmax.scara.android.present.shapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import dmax.scara.android.R
import dmax.scara.android.present.misc.view
import dmax.scara.android.present.shapes.ShapesContract.Event.DrawCircle
import dmax.scara.android.present.shapes.ShapesContract.Event.DrawSquare
import dmax.scara.android.present.shapes.ShapesContract.Event.DrawTriangle
import dmax.scara.android.present.shapes.ShapesContract.Event.OnCLick
import org.koin.android.viewmodel.ext.android.viewModel

class ShapesFragment : Fragment() {

    private val model: ShapesContract.Model by viewModel()
    private val clickButton: MaterialButton by view(R.id.button_click)
    private val drawCircleButton: MaterialButton by view(R.id.button_circle)
    private val drawSquareButton: MaterialButton by view(R.id.button_square)
    private val drawTriangleButton: MaterialButton by view(R.id.button_triangle)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shapes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clickButton.bindEvent(OnCLick)
        drawCircleButton.bindEvent(DrawCircle)
        drawSquareButton.bindEvent(DrawSquare)
        drawTriangleButton.bindEvent(DrawTriangle)
    }

    private fun MaterialButton.bindEvent(event: ShapesContract.Event) = setOnClickListener { model.event(event) }
}
