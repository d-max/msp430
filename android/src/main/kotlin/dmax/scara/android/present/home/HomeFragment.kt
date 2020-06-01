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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val model: Model by viewModel()

    private lateinit var scope: CoroutineScope
    private lateinit var job: Job
    private lateinit var led: AppCompatImageView
    private lateinit var button: AppCompatToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = MainScope()
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        led = view.findViewById(R.id.image_led)
        button = view.findViewById(R.id.button_connect)

        button.setOnClickListener {
            model.event(Event.OnPower)
        }

        observe(model.data) {
            when (it) {
                Data.Connecting -> {
                    ledBlink()
                }
                Data.Error -> {
                    stopBlink()
                    ledError()
                }
                Data.Connected -> {
                    stopBlink()
                    ledOn()
                }
                Data.Disconnected -> {
                    stopBlink()
                    ledOff()
                }
            }
        }
    }

    private fun stopBlink() {
        if (this::job.isInitialized) job.cancel()
    }

    private fun ledBlink() {
        job = scope.launch {
            while (isActive) {
                ledOn()
                delay(200)
                ledOff()
                delay(300)
            }
        }
    }

    private fun ledOff() {
        led.setImageResource(R.drawable.ic_led_off)
    }

    private fun ledOn() {
        led.setImageResource(R.drawable.ic_led_blue)
    }

    private fun ledError() {
        led.setImageResource(R.drawable.ic_led_red)
    }
}
