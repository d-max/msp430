package dmax.scara.android.app

import dmax.scara.android.actors.ConnectActor
import dmax.scara.android.actors.ConnectionStateRequest
import dmax.scara.android.actors.DisconnectActor
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
        HomeModel(
            connect = get(),
            disconnect = get(),
            isConnected = get()
        ) as HomeContract.Model
    }

}

fun actors() = module {

    factory {
        ConnectActor(connector = get())
    }
    factory {
        DisconnectActor(connector = get())
    }
    factory {
        ConnectionStateRequest(connector = get())
    }


}
