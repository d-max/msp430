package dmax.scara.android.actions.connection

import dmax.scara.android.actions.Actor
import dmax.scara.android.connect.Connector

class DisconnectActor(private val connector: Connector) : Actor {
    override suspend operator fun invoke() = connector.disconnect()
}
