package dmax.scara.android.app

import dmax.scara.android.connect.Connector
import dmax.scara.android.connect.bluetooth.BluetoothConnector
import dmax.scara.android.present.home.HomeContract
import dmax.scara.android.present.home.HomeModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun core() = module {

    single<Connector> {
        BluetoothConnector()
    }

}

fun mvvm() = module {

    viewModel {
        HomeModel(connector = get()) as HomeContract.Model
    }

}
