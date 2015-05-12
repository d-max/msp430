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

import dmax.quadruped.Logger;
import dmax.quadruped.Util;
import dmax.quadruped.connection.Connector;

import static android.bluetooth.BluetoothAdapter.*;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 16:11
 */
public class BluetoothConnector implements Connector, Constants {

    private Logger log = new Logger("BluetoothConnector");
    private Semaphore bluetoothEnableLock;
    private BroadcastReceiver receiver;
    private Context context;
    private BluetoothAdapter adapter;
    private BluetoothSocket socket;
    private OutputStream out;
    private InputStream in;

    public BluetoothConnector(Context context) {
        this.context = context;
        this.adapter = BluetoothAdapter.getDefaultAdapter();

        if (!adapter.isEnabled()) {
            log.d("enable bluetooth");
            bluetoothEnableLock = new Semaphore(0);
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getIntExtra(EXTRA_STATE, -1) == STATE_ON) {
                        log.d("enabled");
                        bluetoothEnableLock.release();
                    }
                }
            };
            context.registerReceiver(receiver, new IntentFilter(ACTION_STATE_CHANGED));
            adapter.enable();
        }
    }

    public void connect() {
        log.d("connect");
        try {
            if (bluetoothEnableLock != null) {
                log.d("wait for bluetooth");
                bluetoothEnableLock.acquire();
            }
            BluetoothDevice device = adapter.getRemoteDevice(ADDRESS);
            socket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
            socket.connect();
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Util.close(in);
            Util.close(out);
            Util.close(socket);
        }
    }

    public boolean send(int servoId, int angle) {
        log.d("send: %d %d", servoId, angle);
        if (!socket.isConnected()) {
            log.e("connection error");
            return false;
        }
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

    public void disconnect() {
        log.d("disconnect");
        if (receiver != null) context.unregisterReceiver(receiver);

        Util.close(in);
        Util.close(out);
        Util.close(socket);
    }
}
