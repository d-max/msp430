package dmax.quadruped.connection;

import android.os.Message;

/**
 * Created by Maksym Dybarskyi | maxim.dybarskyy@gmail.com
 * on 20.01.16 at 18:04
 */
public class MessageHelper {

    public static Message createCommandMessage(int servoId, int angle) {
        Message message = Message.obtain();
        message.what = ConnectorService.COMMAND;
        message.arg1 = servoId;
        message.arg2 = angle;
        return message;
    }

    public static Message createResultMessage(boolean result) {
        Message message = Message.obtain();
        message.obj = result;
        return message;
    }

    public static int getServoId(Message message) {
        return message.arg1;
    }

    public static int getAngle(Message message) {
        return message.arg2;
    }

    public static boolean getResult(Message message) {
        return (Boolean) message.obj;
    }
}
