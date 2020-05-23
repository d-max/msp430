package dmax.scara.android.present.home

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.present.home.HomeContract.Data
import dmax.scara.android.present.home.HomeContract.Event
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeModel : HomeContract.Model() {

    private val scope = MainScope()

    override val data = MutableLiveData<Data>()

    override fun event(e: Event) {
        when(e) {
            Event.Power -> {
                scope.launch {
                    data.value = Data.Connecting
                    delay(3000)
                    data.value = Data.Connected
                }
            }
        }
    }
}
