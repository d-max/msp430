package dmax.scara.android.actions.connection

import dmax.scara.android.actions.Request
import dmax.scara.android.connect.Connector

class ConnectionStateRequest(private val connector: Connector) : Request<Boolean> {
    override suspend operator fun invoke() = connector.isConnected
}
