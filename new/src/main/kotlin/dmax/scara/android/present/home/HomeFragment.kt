package dmax.scara.android.present.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.fragment.app.Fragment
import dmax.scara.android.R
import dmax.scara.android.misc.observe
import dmax.scara.android.present.home.HomeContract.Data
import dmax.scara.android.present.home.HomeContract.Event
import dmax.scara.android.present.home.HomeContract.Model
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val scope = MainScope()
    private val model: Model by viewModel()
    private lateinit var led: AppCompatImageView
    private lateinit var button: AppCompatToggleButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        led = view.findViewById(R.id.image_led)
        button = view.findViewById(R.id.button_connect)

        button.setOnClickListener {
            model.event(Event.Power)
        }

        observe(model.data) {
            when (it) {
                Data.Error -> ledError()
                Data.Connected -> ledOn()
                Data.Connecting -> ledBlink()
                Data.Disconnected -> ledOff()
            }
        }
    }

    private fun ledBlink() {
        scope.launch {
            while (isActive) {
                ledOn()
                delay(200)
                ledOff()
                delay(300)
            }
        }
    }

    private fun ledOff() =
        led.setImageResource(R.drawable.ic_led_off)

    private fun ledOn() =
        led.setImageResource(R.drawable.ic_led_blue)

    private fun ledError() =
        led.setImageResource(R.drawable.ic_led_red)
}
