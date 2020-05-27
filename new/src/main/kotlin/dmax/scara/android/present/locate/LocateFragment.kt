package dmax.scara.android.present.locate

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import dmax.scara.android.R
import dmax.scara.android.misc.ViewMeasure
import dmax.scara.android.misc.component1
import dmax.scara.android.misc.component2
import dmax.scara.android.misc.toPoint
import dmax.scara.android.present.common.LocateView

class LocateFragment : Fragment() {

    private val startPoint = PointF(0f, 0f)
    private lateinit var measure: ViewMeasure

    private lateinit var locateView: LocateView
    private lateinit var resetView: AppCompatImageButton
    private lateinit var coordinatesView: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_locate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        measure = ViewMeasure(requireActivity())

        resetView = view.findViewById(R.id.reset)
        locateView = view.findViewById(R.id.locate)
        coordinatesView = view.findViewById(R.id.coordinates)

        locateView.listener = this::locate
        resetView.setOnClickListener {
            locateView.point = startPoint
            locate(startPoint)
        }
    }

    private fun locate(point: PointF) {
        val (xPixels, yPixels) = point.toPoint()
        val xCm = measure.widthInCm(xPixels)
        val yCm = measure.heightInCm(yPixels)
        coordinatesView.showCoordinates(xCm, yCm)
        // todo update view model
    }
    
    private fun AppCompatTextView.showCoordinates(xCm: Float, yCm: Float) {
        text = requireContext().getString(
            R.string.coordinates,
            "%.2f".format(xCm),
            "%.2f".format(yCm)
        )
    }
}
