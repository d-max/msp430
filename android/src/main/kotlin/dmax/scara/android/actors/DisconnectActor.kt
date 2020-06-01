package dmax.scara.android.actors

import dmax.scara.android.connect.Connector

class DisconnectActor(private val connector: Connector) : Actor {
    override suspend operator fun invoke() = connector.disconnect()
}
