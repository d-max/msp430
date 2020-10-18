package dmax.scara.android.present.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import dmax.scara.android.R
import dmax.scara.android.present.misc.view
import dmax.scara.android.present.control.ControlContract.Event
import dmax.scara.android.present.control.ControlContract.Model
import org.koin.android.viewmodel.ext.android.viewModel

class ControlFragment : Fragment() {

    private val model: Model by viewModel()
    private val baseAngle: AppCompatTextView by view(R.id.base_angle)
    private val elbowAngle: AppCompatTextView by view(R.id.elbow_angle)
    private val wristAngle: AppCompatTextView by view(R.id.wrist_angle)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_control, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.initSeekBar(R.id.seekbar_base, R.id.base) {
            baseAngle.text = it.toString()
            model.event(Event.OnBaseControl(angle = it))
        }
        view.initSeekBar(R.id.seekbar_elbow, R.id.elbow) {
            elbowAngle.text = it.toString()
            model.event(Event.OnElbowControl(angle = it))
        }
        view.initSeekBar(R.id.seekbar_wrist, R.id.wrist) {
            wristAngle.text = it.toString()
            model.event(Event.OnWristControl(angle = it))
        }
    }

    private fun View.initSeekBar(@IdRes seekBarId: Int, @IdRes jointId: Int, callback: (Int) -> Unit) {
        val seekBarListener = SeekBarListener(findViewById(jointId), callback)
        findViewById<AppCompatSeekBar>(seekBarId).setOnSeekBarChangeListener(seekBarListener)
    }

    private inner class SeekBarListener(
        private val joint: View,
        private val callback: (Int) -> Unit
    ) : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser) callback(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            joint.setBackgroundResource(R.drawable.shape_rectangle_accent)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            joint.setBackgroundResource(R.drawable.shape_rectangle_surface)
        }
    }
}
