package dmax.scara.android.app.injection

import dmax.scara.android.actions.connection.ConnectActor
import dmax.scara.android.actions.connection.ConnectionStateRequest
import dmax.scara.android.actions.connection.DisconnectActor
import dmax.scara.android.actions.motion.BendBaseCall
import dmax.scara.android.actions.motion.BendElbowCall
import dmax.scara.android.actions.motion.BendWristCall
import dmax.scara.android.actions.motion.ClickActor
import dmax.scara.android.actions.motion.MoveCall
import org.koin.dsl.module

fun actors() = module {
    factory {
        ConnectActor(connector = get(qualifier = Qualifiers.connectorMock))
    }
    factory {
        DisconnectActor(connector = get(qualifier = Qualifiers.connectorMock))
    }
    factory {
        ConnectionStateRequest(connector = get(qualifier = Qualifiers.connectorMock))
    }
    factory {
        BendBaseCall(dispatcher = get(qualifier = Qualifiers.dispatcherSimple))
    }
    factory {
        BendElbowCall(dispatcher = get(qualifier = Qualifiers.dispatcherSimple))
    }
    factory {
        BendWristCall(dispatcher = get(qualifier = Qualifiers.dispatcherSimple))
    }
    factory {
        MoveCall(dispatcher = get(qualifier = Qualifiers.dispatcherSimple), state = get())
    }
    factory {
        ClickActor(dispatcher = get(qualifier = Qualifiers.dispatcherSimple))
    }
}
