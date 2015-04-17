package dmax.quadruped.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

import dmax.quadruped.Util;

import static android.bluetooth.BluetoothAdapter.*;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 16:11
 */
public class BluetoothConnector implements Constants {

    private CountDownLatch lock = new CountDownLatch(1);
    private Context context;
    private HandlerThread worker;
    private Handler btHandler;
    private Handler uiHandler;

    public BluetoothConnector(Context context, BluetoothCallback callback) {
        this.context = context;
        this.uiHandler = new Handler(new CallbackInvoker(callback));
        this.worker = new BluetoothWorker();
        this.worker.start();
    }

    public void connect() {
        getBtHandler().obtainMessage(MESSAGE_CONNECT).sendToTarget();
    }

    public void disconnect() {
        getBtHandler().obtainMessage(MESSAGE_DISCONNECT).sendToTarget();
    }

    public void send(BluetoothMessage message) {
        getBtHandler().obtainMessage(MESSAGE_COMMAND, message.servoId, message.angle).sendToTarget();
    }

    public void quit() {
        this.worker.quit();
    }

    private Handler getBtHandler() {
        try {
            lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return btHandler;
    }

    //~

    private class CallbackInvoker implements Handler.Callback {

        private BluetoothCallback callback;

        public CallbackInvoker(BluetoothCallback callback) {
            this.callback = callback;
        }

        @Override
        public boolean handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CALLBACK_CONNECTED:
                    callback.onConnected();
                    return true;
                case CALLBACK_FAILED:
                    callback.onFailed();
                    return true;
                case CALLBACK_RESPONSE:
                    callback.onResponse(BluetoothCallback.Response.valueOf(msg.arg1));
                    return true;
            }
            return false;
        }
    }

    private class MessagesProcessor implements Handler.Callback {

        private BroadcastReceiver receiver;

        private BluetoothAdapter adapter;
        private BluetoothSocket socket;
        private OutputStream out;
        private InputStream in;

        @Override
        public boolean handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MESSAGE_CONNECT:
                    onConnect();
                    return true;
                case MESSAGE_DISCONNECT:
                    onDisconnect();
                    return true;
                case MESSAGE_COMMAND:
                    onCommand(msg.arg1, msg.arg2);
                    return true;
                default: return false;
            }
        }

        private void onConnect() {
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
        }

        private void onReady() {
            try {
                BluetoothDevice device = adapter.getRemoteDevice(ADDRESS);
                socket = device.createInsecureRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
                socket.connect();
                out = socket.getOutputStream();
                in = socket.getInputStream();
                uiHandler.obtainMessage(CALLBACK_CONNECTED).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
                Util.close(in);
                Util.close(out);
                Util.close(socket);
                uiHandler.obtainMessage(CALLBACK_FAILED).sendToTarget();
            }
        }

        private void onDisconnect() {
            if (receiver != null) context.unregisterReceiver(receiver);

            Util.close(in);
            Util.close(out);
            Util.close(socket);
        }

        private void onCommand(int servoId, int angle) {
            try {
                String message = String.format("S%02dA%03d\n", servoId, angle);
                out.write(message.getBytes());
                out.flush();
                int response = in.read();
                uiHandler.obtainMessage(CALLBACK_RESPONSE, response).sendToTarget();
            } catch (IOException ex) {
                ex.printStackTrace();
                Util.close(in);
                Util.close(out);
                Util.close(socket);
                uiHandler.obtainMessage(CALLBACK_FAILED).sendToTarget();
            }
        }
    }

    private class BluetoothWorker extends HandlerThread {

        public BluetoothWorker() {
            super("bt", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        }

        @Override
        protected void onLooperPrepared() {
            btHandler = new Handler(getLooper(), new MessagesProcessor());
            lock.countDown();
        }
    }
}
