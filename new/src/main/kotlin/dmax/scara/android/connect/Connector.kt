package dmax.scara.android.connect

interface Connector {
    suspend fun connect()
    suspend fun disconnect()
    suspend fun send(command: Command)
}
