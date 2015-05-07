package dmax.quadruped.connection.bluetooth;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 16:23
 */
interface Constants {

    String ADDRESS = "00:12:06:21:88:70";
    String UUID = "00001101-0000-1000-8000-00805F9B34FB";

    int MESSAGE_CONNECT = 0x01;
    int MESSAGE_DISCONNECT = 0x02;
    int MESSAGE_COMMAND = 0x03;

    int CALLBACK_CONNECTED = 0x07;
    int CALLBACK_FAILED = 0x08;
    int CALLBACK_RESPONSE = 0x09;
}
