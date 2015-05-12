package dmax.quadruped.connection;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by Maksym Dybarskyi | maxim.dybarskyy@gmail.com
 * on 11.05.15 at 17:22
 */
public class ConnectorServiceClient implements ServiceConnection {

    public interface Callback {
        void onReady();
        void onSent(boolean result);
    }

    //~

    private Callback callback;
    private Messenger requestMessenger;
    private Messenger responseMessenger;

    public void bind(Context context, Callback callback) {
        this.callback = callback;
        this.responseMessenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ConnectorServiceClient.this.callback.onSent((Boolean) msg.obj);
            }
        });
        Intent intent = new Intent(context, ConnectorService.class);
        context.bindService(intent, this, Service.BIND_AUTO_CREATE);
    }

    public void unbind(Context context) {
        context.unbindService(this);
    }

    public void sendCommand(int servoId, int angle) {
        try {
            Message message = Message.obtain();
            message.arg1 = servoId;
            message.arg2 = angle;
            message.replyTo = responseMessenger;
            requestMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        requestMessenger = new Messenger(service);
        callback.onReady();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        requestMessenger = null;
    }
}
