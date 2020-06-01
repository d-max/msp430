package dmax.scara.android.app

import dmax.scara.android.actors.BendBaseCall
import dmax.scara.android.actors.BendElbowCall
import dmax.scara.android.actors.BendWristCall
import dmax.scara.android.actors.ConnectActor
import dmax.scara.android.actors.ConnectionStateRequest
import dmax.scara.android.actors.DisconnectActor
import dmax.scara.android.actors.MoveCall
import dmax.scara.android.connect.Connector
import dmax.scara.android.connect.bluetooth.BluetoothConnector
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.present.control.ControlContract
import dmax.scara.android.present.control.ControlModel
import dmax.scara.android.present.home.HomeContract
import dmax.scara.android.present.home.HomeModel
import dmax.scara.android.present.locate.LocateContract
import dmax.scara.android.present.locate.LocateModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun core() = module {

    single<Connector> {
        BluetoothConnector()
    }

    single {
        State(arm = Config.createDefaultArm())
    }

    single {
        Dispatcher(
            state = get(),
            connector = get()
        )
    }

}

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
    factory {
        BendBaseCall(dispatcher = get(), state = get())
    }
    factory {
        BendElbowCall(dispatcher = get(), state = get())
    }
    factory {
        BendWristCall(dispatcher = get(), state = get())
    }
    factory {
        MoveCall(dispatcher = get(), state = get())
    }

}
