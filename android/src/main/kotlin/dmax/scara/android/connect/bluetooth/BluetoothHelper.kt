package dmax.scara.android.connect.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.enableBluetooth() {
    val adapter = BluetoothAdapter.getDefaultAdapter()
    if (adapter.isEnabled) return

    suspendCoroutine<Unit> { continuation ->
        bluetoothReceiver {
            continuation.resume(Unit)
        }
        adapter.enable()
    }
}

private fun Context.bluetoothReceiver(callback: () -> Unit) {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
            if (state == BluetoothAdapter.STATE_ON) {
                unregisterReceiver(this)
                callback()
            }
        }
    }
    registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
}
