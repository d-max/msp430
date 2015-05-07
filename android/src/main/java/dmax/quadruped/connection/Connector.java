package dmax.quadruped.connection;

/**
 * Created by Maksym Dybarskyi | maksym.dybarskyi@symphonyteleca.com
 * on 07.05.15 at 17:32
 */
public interface Connector {

    void connect();

    void disconnect();

    boolean send(int servoId, int angle);
}
