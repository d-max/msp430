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

/**
 * Created by Maksym Dybarskyi | maxim.dybarskyy@gmail.com
 * on 07.05.15 at 17:11
 */
public class ConnectorService extends Service {

    static final int COMMAND = 0x45;

    private Messenger localMessenger;
    private Handler commandProcessor;
    private ExecutorService worker;
    private Connector connector;

    @Override
    public void onCreate() {
        super.onCreate();

        connector = null; // todo init
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
        return localMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
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
                return;
            }
            final Messenger replyTo = msg.replyTo;
            final int servoId = msg.arg1;
            final int servoAngle = msg.arg2;

            worker.execute(new Runnable() {
                @Override
                public void run() {
                    boolean result = connector.send(servoId, servoAngle);
                    sendResult(result, replyTo);
                }
            });
        }

        private void sendResult(boolean result, Messenger replyTo) {
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
