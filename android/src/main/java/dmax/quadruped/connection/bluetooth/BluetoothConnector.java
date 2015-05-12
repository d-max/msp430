package dmax.quadruped.connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Semaphore;

import dmax.quadruped.Util;
import dmax.quadruped.connection.Connector;

import static android.bluetooth.BluetoothAdapter.*;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 16:11
 */
public class BluetoothConnector implements Connector, Constants {

    private Semaphore bluetoothEnableLock = new Semaphore(0);
    private BroadcastReceiver receiver;
    private Context context;
    private BluetoothAdapter adapter;
    private BluetoothSocket socket;
    private OutputStream out;
    private InputStream in;

    public BluetoothConnector(Context context) {
        this.context = context;
    }

    public void connect() {
        if (adapter == null) adapter = BluetoothAdapter.getDefaultAdapter();

        if (adapter.isEnabled()) {
            onReady();
        } else {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getIntExtra(EXTRA_STATE, -1) == STATE_ON) onReady();
                }
            };
            context.registerReceiver(receiver, new IntentFilter(ACTION_STATE_CHANGED));
            adapter.enable();
        }
        try {
            bluetoothEnableLock.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean send(int servoId, int angle) {
        int response = RESPONSE_FAILED;
        try {
            String message = String.format(MESSAGE_TEMPLATE, servoId, angle);
            out.write(message.getBytes());
            out.flush();
            response = in.read();
        } catch (IOException ex) {
            ex.printStackTrace();
            Util.close(in);
            Util.close(out);
            Util.close(socket);
        }
        return response == RESPONSE_OK;
    }

    private void onReady() {
        try {
            BluetoothDevice device = adapter.getRemoteDevice(ADDRESS);
            socket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
            socket.connect();
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Util.close(in);
            Util.close(out);
            Util.close(socket);
        } finally {
            bluetoothEnableLock.release();
        }
    }

    public void disconnect() {
        if (receiver != null) context.unregisterReceiver(receiver);

        Util.close(in);
        Util.close(out);
        Util.close(socket);
    }
}
