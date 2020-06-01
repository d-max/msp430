package dmax.scara.android.present.home

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.actors.ConnectActor
import dmax.scara.android.actors.ConnectionStateRequest
import dmax.scara.android.actors.DisconnectActor
import dmax.scara.android.present.home.HomeContract.Data
import dmax.scara.android.present.home.HomeContract.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HomeModel(
    private val connect: ConnectActor,
    private val disconnect: DisconnectActor,
    private val isConnected: ConnectionStateRequest
) : HomeContract.Model() {

    private val scope = MainScope()
    private var job: Job? = null

    override val data = MutableLiveData<Data>()

    override fun onCleared() {
        scope.cancel()
    }

    override fun event(e: Event) {
        when(e) {
            Event.OnPower -> {
                // was connecting and failed -> just turn off led
                if (data.value == Data.Error) {
                    data.value = Data.Disconnected
                    return
                }
                // is currently connecting -> cancel and turn off
                if (job?.isActive == true) {
                    job?.cancel()
                    data.value = Data.Disconnected
                    return
                }
                // previous completed -> normal behaviour
                switch()
            }
        }
    }

    private fun switch() {
        job?.cancel()
        job = scope.launch {
            if (isConnected()) {
                disconnect()
                data.value = Data.Disconnected
            } else {
                data.value = Data.Connecting
                connect()
                data.value = if (isConnected()) Data.Connected else Data.Error
            }
        }
    }
}
