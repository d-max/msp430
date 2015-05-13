package dmax.quadruped.connection;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.os.Process;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dmax.quadruped.Logger;
import dmax.quadruped.Util;
import dmax.quadruped.connection.bluetooth.BluetoothConnector;

/**
 * Created by Maksym Dybarskyi | maxim.dybarskyy@gmail.com
 * on 07.05.15 at 17:11
 */
public class ConnectorService2 extends Service implements Constants {

    private Logger log = new Logger("ConnectorService");
    private Messenger localMessenger;
    private HandlerThread worker;

    @Override
    public void onCreate() {
        log.d("start");
        super.onCreate();

        worker = new HandlerThread("ConnectorServiceThread", Process.THREAD_PRIORITY_BACKGROUND);
        worker.start();

        Connector connector = new BluetoothConnector(this);
        Handler commandProcessor = new Handler(worker.getLooper(), new CommandProcessor(connector, log));
        localMessenger = new Messenger(commandProcessor);

        sendCommand(CONNECT);
    }

    @Override
    public IBinder onBind(Intent intent) {
        log.d("binding");
        return localMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        log.d("stop");
        sendCommand(DISCONNECT);
        worker.quitSafely();
    }

    private void sendCommand(int what) {
        log.d("send command: %s", Util.findConstantName(what));
        try {
            Message message = Message.obtain();
            message.what = what;
            localMessenger.send(message);
        } catch (RemoteException e) {
            log.e("Thread is dead");
            e.printStackTrace();
        }
    }

    //~

    private static class CommandProcessor implements Handler.Callback {

        private Connector connector;
        private Logger log;

        public CommandProcessor(Connector connector, Logger log) {
            this.connector = connector;
            this.log = log;
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case CONNECT:
                    connector.connect();
                    return true;
                case DISCONNECT:
                    connector.disconnect();
                    return true;
                case COMMAND:
                    Messenger replyTo = msg.replyTo;
                    int servoId = msg.arg1;
                    int servoAngle = msg.arg2;
                    boolean result = connector.send(servoId, servoAngle);
                    sendResult(result, replyTo);
                    return true;
                default:
                    log.e("wrong message");
                    return false;
            }
        }

        private void sendResult(boolean result, Messenger replyTo) {
            log.d("reply message");
            try {
                Message reply = Message.obtain();
                reply.obj = result;
                replyTo.send(reply);
            } catch (RemoteException e) {
                log.e("ReplyTo is dead");
                e.printStackTrace();
            }
        }
    }
}
