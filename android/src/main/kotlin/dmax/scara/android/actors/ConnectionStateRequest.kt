package dmax.scara.android.actors

import dmax.scara.android.connect.Connector

class ConnectionStateRequest(private val connector: Connector) : Request<Boolean> {
    override suspend operator fun invoke() = connector.isConnected
}
