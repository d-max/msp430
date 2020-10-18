package dmax.scara.android.app.injection

import dmax.scara.android.app.Config
import dmax.scara.android.app.misc.State
import dmax.scara.android.connect.Connector
import dmax.scara.android.connect.bluetooth.BluetoothConnector
import dmax.scara.android.connect.mock.MockConnector
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.impl.ProgressiveDispatcher
import dmax.scara.android.dispatch.impl.SimpleDispatcher
import org.koin.dsl.module

fun core() = module {
    single {
        State(
            arm = Config.defaultArm()
        )
    }
    single<Connector>(qualifier = Qualifiers.connectorBluetooth) {
        BluetoothConnector(
            socketConfig = Config.socketConfig(),
            servoToPort = Config::servoPortMapper
        )
    }
    single<Connector>(qualifier = Qualifiers.connectorMock) {
        MockConnector(
            delay = Config.loggingDelay()
        )
    }
    single<Dispatcher>(qualifier = Qualifiers.dispatcherSimple) {
        SimpleDispatcher(
            state = get(),
            connector = get()
        )
    }
    single<Dispatcher>(qualifier = Qualifiers.dispatcherProgressive) {
        ProgressiveDispatcher(
            speedConfig = Config.speedConfig(),
            state = get(),
            connector = get()
        )
    }
}
