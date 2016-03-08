package dmax.quadruped.connection;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;

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
    public static final int RESPONSE_READY = 0x21;
    public static final int RESPONSE_SENT = 0x22;
    public static final String REPLY_TO = "replyToMessenger";

    private static Logger log = new Logger("ConnectorService");

    private Messenger localMessenger;
    private Messenger replyTo;
    private HandlerThread worker;


    public static Intent createIntent(Context context, Messenger replyTo) {
        Intent intent = new Intent(context, ConnectorService.class);
        intent.putExtra(REPLY_TO, replyTo);
        return intent;
    }


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
    }

    @Override
    public IBinder onBind(Intent intent) {
        log.d("binding");

        replyTo = intent.getParcelableExtra(REPLY_TO);
        sendCommand(CONNECT);

        return localMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        log.d("unbinding");

        sendCommand(DISCONNECT);
        worker.quitSafely();
        return false;
    }

    private void sendCommand(int what) {
        log.d("send command: %s", Util.findConstantName(what));
        try {
            Message message = Message.obtain();
            message.what = what;
            message.replyTo = replyTo;
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
            Messenger replyTo = msg.replyTo;
            switch (msg.what) {
                case CONNECT:
                    connector.connect();
                    sendResponse(replyTo, RESPONSE_READY);
                    return true;

                case DISCONNECT:
                    connector.disconnect();
                    return true;

                case COMMAND:
                    int servoId = MessageHelper.getServoId(msg);
                    int servoAngle = MessageHelper.getAngle(msg);
                    boolean result = connector.send(servoId, servoAngle);
                    sendResponse(replyTo, RESPONSE_SENT, result);
                    return true;

                default:
                    log.e("wrong message");
                    return false;
            }
        }

        private void sendResponse(Messenger replyTo, int responseType, Object... args) {
            log.d("reply message");
            Message msg;
            try {
                switch (responseType) {
                    case RESPONSE_READY:    msg = MessageHelper.createReadyResponseMessage();   break;
                    case RESPONSE_SENT:     msg = MessageHelper.createResultResponseMessage((Boolean) args[0]); break;
                    default:                msg = null;
                }
                if (msg != null) replyTo.send(msg);
            } catch (RemoteException e) {
                log.e("ReplyTo is dead");
                e.printStackTrace();
            }
        }
    }
}
