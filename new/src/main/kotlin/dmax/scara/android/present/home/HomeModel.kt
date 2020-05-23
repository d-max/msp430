package dmax.scara.android.present.home

import androidx.lifecycle.MutableLiveData
import dmax.scara.android.present.home.HomeContract.Data
import dmax.scara.android.present.home.HomeContract.Event

class HomeModel : HomeContract.Model() {

    override val data = MutableLiveData<Data>()

    override fun event(e: Event) {
        when(e) {
            Event.Power -> data.value = Data.Connecting
        }
    }
}
