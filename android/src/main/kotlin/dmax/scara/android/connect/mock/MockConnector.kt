package dmax.scara.android.connect.mock

import dmax.scara.android.connect.Command
import dmax.scara.android.connect.Connector
import kotlinx.coroutines.delay

class MockConnector(private val delay: Long) : Connector {

    private var _isConnected: Boolean = false
    override val isConnected: Boolean
        get() = _isConnected

    override suspend fun connect() = log("connecting") { _isConnected = true }
    override suspend fun disconnect() = log("disconnecting") { _isConnected = false }
    override suspend fun send(command: Command) = log("sending $command")

    private suspend fun log(message: String, block: () -> Unit = {}) {
        println("$message : ")
        block()
        delay(delay)
        println("done")
    }
}
