package dmax.quadruped.connection;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.os.Process;

import dmax.quadruped.Logger;
import dmax.quadruped.Util;
import dmax.quadruped.connection.bluetooth.BluetoothConnector;

/**
 * Created by Maksym Dybarskyi | maxim.dybarskyy@gmail.com
 * on 07.05.15 at 17:11
 */
public class ConnectorService extends Service {

    public static final int COMMAND = 0x11;
    public static final int CONNECT = 0x12;
    public static final int DISCONNECT = 0x13;

    private static Logger log = new Logger("ConnectorService");

    private Messenger localMessenger;
    private HandlerThread worker;

    @Override
    public void onCreate() {
        log.d("start");
        super.onCreate();

        worker = new HandlerThread("ConnectorServiceThread", Process.THREAD_PRIORITY_BACKGROUND);
        worker.start();

        Connector connector = new BluetoothConnector(this);
        CommandProcessor processor = new CommandProcessor(connector);
        Handler commandProcessor = new Handler(worker.getLooper(), processor);
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

        public CommandProcessor(Connector connector) {
            this.connector = connector;
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
                    int servoId = MessageHelper.getServoId(msg);
                    int servoAngle = MessageHelper.getAngle(msg);
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
                Message msg = MessageHelper.createResultMessage(result);
                replyTo.send(msg);
            } catch (RemoteException e) {
                log.e("ReplyTo is dead");
                e.printStackTrace();
            }
        }
    }
}
