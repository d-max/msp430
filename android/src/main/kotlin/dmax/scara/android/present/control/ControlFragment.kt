package dmax.scara.android.present.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import dmax.scara.android.R
import dmax.scara.android.present.control.ControlContract.Event
import dmax.scara.android.present.control.ControlContract.Model
import dmax.scara.android.present.misc.SeekBarChangeListenerAdapter
import dmax.scara.android.present.misc.observe
import dmax.scara.android.present.misc.view
import org.koin.android.viewmodel.ext.android.viewModel

class ControlFragment : Fragment() {

    private val model: Model by viewModel()
    private val baseAngle: AppCompatTextView by view(R.id.base_angle)
    private val elbowAngle: AppCompatTextView by view(R.id.elbow_angle)
    private val wristAngle: AppCompatTextView by view(R.id.wrist_angle)
    private val baseBar: AppCompatSeekBar by view(R.id.seekbar_base)
    private val elbowBar: AppCompatSeekBar by view(R.id.seekbar_elbow)
    private val wristBar: AppCompatSeekBar by view(R.id.seekbar_wrist)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_control, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseBar.onProgressChanged {
            baseAngle.text = it.toString()
            model.event(Event.SetAngleEvent.OnBaseControl(angle = it))
        }
        elbowBar.onProgressChanged {
            elbowAngle.text = it.toString()
            model.event(Event.SetAngleEvent.OnElbowControl(angle = it))
        }
        wristBar.onProgressChanged {
            wristAngle.text = it.toString()
            model.event(Event.SetAngleEvent.OnWristControl(angle = it))
        }
        observe(model.data) { data ->
            val (base, elbow, wrist) = data as? ControlContract.Data.Angles ?: return@observe
            baseAngle.text = base.toString()
            elbowAngle.text = elbow.toString()
            wristAngle.text = wrist.toString()
            baseBar.progress = base
            elbowBar.progress = elbow
            wristBar.progress = wrist
        }
        model.event(Event.OnInit)
    }

    private fun AppCompatSeekBar.onProgressChanged(callback: (Int) -> Unit) {
        setOnSeekBarChangeListener(object : SeekBarChangeListenerAdapter() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) callback(progress)
            }
        })
    }
}
