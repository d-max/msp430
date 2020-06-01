package dmax.scara.android.connect

interface Connector {
    val isConnected: Boolean
    suspend fun connect()
    suspend fun disconnect()
    suspend fun send(command: Command)
}
