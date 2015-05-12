package dmax.quadruped.connection;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dmax.quadruped.Logger;
import dmax.quadruped.connection.bluetooth.BluetoothConnector;

/**
 * Created by Maksym Dybarskyi | maxim.dybarskyy@gmail.com
 * on 07.05.15 at 17:11
 */
public class ConnectorService extends Service {

    static final int COMMAND = 0x45;

    private Logger log = new Logger("ConnectorService");
    private Messenger localMessenger;
    private Handler commandProcessor;
    private ExecutorService worker;
    private Connector connector;

    @Override
    public void onCreate() {
        log.d("start");
        super.onCreate();

        connector = new BluetoothConnector(this);
        commandProcessor = new CommandProcessor();
        localMessenger = new Messenger(commandProcessor);

        worker = Executors.newSingleThreadExecutor();
        worker.execute(new Runnable() {
            @Override
            public void run() {
                connector.connect();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        log.d("binding");
        return localMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        log.d("stop");
        worker.execute(new Runnable() {
            @Override
            public void run() {
                connector.disconnect();
            }
        });
        worker.shutdown();
    }

    //~

    private class CommandProcessor extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what != COMMAND) {
                log.e("wrong message");
                return;
            }
            final Messenger replyTo = msg.replyTo;
            final int servoId = msg.arg1;
            final int servoAngle = msg.arg2;

            log.d("handle command: %d %d", servoId, servoAngle);
            worker.execute(new Runnable() {
                @Override
                public void run() {
                    boolean result = connector.send(servoId, servoAngle);
                    sendResult(result, replyTo);
                }
            });
        }

        private void sendResult(boolean result, Messenger replyTo) {
            log.d("reply message");
            try {
                Message reply = Message.obtain();
                reply.obj = result;
                replyTo.send(reply);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
