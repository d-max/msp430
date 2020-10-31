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
        ConnectActor(connector = get())
    }
    factory {
        DisconnectActor(connector = get())
    }
    factory {
        ConnectionStateRequest(connector = get())
    }
    factory {
        BendBaseCall(dispatcher = get())
    }
    factory {
        BendElbowCall(dispatcher = get())
    }
    factory {
        BendWristCall(dispatcher = get())
    }
    factory {
        MoveCall(dispatcher = get(), state = get())
    }
    factory {
        ClickActor(dispatcher = get())
    }
}
