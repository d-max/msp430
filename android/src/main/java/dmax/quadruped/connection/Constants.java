package dmax.quadruped.connection;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 16:23
 */
public interface Constants {

    int COMMAND = 0x11;
    int CONNECT = 0x12;
    int DISCONNECT = 0x13;

    int RESPONSE_OK = 0x01;
    int RESPONSE_FAILED = 0x02;
}
