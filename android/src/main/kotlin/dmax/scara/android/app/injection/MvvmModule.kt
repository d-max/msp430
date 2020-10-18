package dmax.scara.android.app.injection

import dmax.scara.android.present.control.ControlContract
import dmax.scara.android.present.control.ControlModel
import dmax.scara.android.present.home.HomeContract
import dmax.scara.android.present.home.HomeModel
import dmax.scara.android.present.locate.LocateContract
import dmax.scara.android.present.locate.LocateModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun mvvm() = module {
    viewModel<HomeContract.Model> {
        HomeModel(
            connect = get(),
            disconnect = get(),
            isConnected = get()
        )
    }
    viewModel<ControlContract.Model> {
        ControlModel(
            bendBase = get(),
            bendElbow = get(),
            bendWrist = get()
        )
    }
    viewModel<LocateContract.Model> {
        LocateModel(move = get())
    }
}
