package dmax.scara.android.connect.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import dmax.scara.android.connect.Command
import dmax.scara.android.connect.Command.Servo
import dmax.scara.android.connect.Connector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class BluetoothConnector(
    private val socketConfig: SocketConfig,
    private val servoToPort: (Servo) -> Byte
) : Connector {

    data class SocketConfig(
        val address: String,
        val uuid: String,
    )

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO) + job
    private var socket: BluetoothSocket? = null
    private var inStream: InputStream? = null
    private var outStream: OutputStream? = null

    override val isConnected: Boolean
        get() = outStream != null

    override suspend fun connect() =
        withContext(Dispatchers.IO) {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val device = adapter.getRemoteDevice(socketConfig.address)
            val uuid = UUID.fromString(socketConfig.uuid)
            try {
                socket = device.createRfcommSocketToServiceRecord(uuid).apply {
                    connect()
                    inStream = inputStream
                    outStream = outputStream
                }
                listen()
            } catch (_: IOException) {
                close()
            }
        }

    override suspend fun send(command: Command) =
        withContext(Dispatchers.IO) {
            if (socket?.isConnected != true) return@withContext

            val (servo, angle) = command
            val port = servoToPort(servo)
            val message = byteArrayOf(port, angle.toByte())
//            printDutyCycles(port, angle)
            try {
                outStream?.let {
                    it.write(message)
                    it.flush()
                }
            } catch (_: IOException) {
                close()
            }
        }

    override suspend fun disconnect() = withContext(Dispatchers.IO) {
        close()
    }

    private fun listen() {
        scope.launch {
            inStream?.let {
//                val appender = Appender()
                while (true) {
                    try {
                        if (it.available() > 0) {
                            val data = it.read()
                            println("received: $data")
//                            appender.append(data)
                        }
                    } catch (_ : IOException) {
                        close()
                    }
                }
            }
        }
    }

    private fun close() {
        outStream?.close()
        outStream = null
        inStream?.close()
        inStream = null
        socket?.close()
        socket = null
    }
}
