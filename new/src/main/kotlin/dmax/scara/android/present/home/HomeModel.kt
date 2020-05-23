package dmax.scara.android.present.home

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.connect.Connector
import dmax.scara.android.present.home.HomeContract.Data
import dmax.scara.android.present.home.HomeContract.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HomeModel(
    private val connector: Connector
) : HomeContract.Model() {

    private val scope = MainScope()
    private var job: Job? = null

    override val data = MutableLiveData<Data>()

    override fun onCleared() {
        scope.cancel()
    }

    override fun event(e: Event) {
        when(e) {
            Event.Power -> {
                if (data.value == Data.Error) {
                    data.value = Data.Disconnected
                    return
                }
                if (connector.isConnected) disconnect() else connect()
            }
        }
    }

    private fun connect() {
        job?.cancel()
        job = scope.launch {
            data.value = Data.Connecting
            connector.connect()
            data.value = if (connector.isConnected) Data.Connected else Data.Error
        }
    }

    private fun disconnect() {
        job?.cancel()
        job = scope.launch {
            connector.disconnect()
            data.value = Data.Disconnected
        }
    }
}
