package dmax.quadruped.connection;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 16:23
 */
public interface Constants {

    int COMMAND = 0x1;
    int CONNECT = 0x2;
    int DISCONNECT = 0x3;

    int RESPONSE_OK = 0x04;
    int RESPONSE_FAILED = 0x05;
}
