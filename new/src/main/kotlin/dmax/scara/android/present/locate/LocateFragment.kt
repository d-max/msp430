package dmax.scara.android.present.locate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import dmax.scara.android.R
import dmax.scara.android.misc.ViewMeasure
import dmax.scara.android.misc.component1
import dmax.scara.android.misc.component2
import dmax.scara.android.misc.toPoint
import dmax.scara.android.present.common.LocateView

class LocateFragment : Fragment() {

    private lateinit var measure: ViewMeasure
    private lateinit var locateView: LocateView
    private lateinit var coordinatesView: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_locate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        measure = ViewMeasure(requireActivity())
        locateView = view.findViewById(R.id.locate)
        coordinatesView = view.findViewById(R.id.coordinates)

        locateView.listener = { point ->
            val (x, y) = point.toPoint()
            val mmX = measure.width2mm(x)
            val mmY = measure.height2mm(y)

            coordinatesView.text = requireContext().getString(
                R.string.coordinates,
                "%.2f".format(mmX),
                "%.2f".format(mmY)
            )
        }
    }
}
