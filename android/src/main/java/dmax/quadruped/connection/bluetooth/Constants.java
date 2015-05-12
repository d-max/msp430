package dmax.quadruped.connection.bluetooth;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 16:23
 */
interface Constants {

    String ADDRESS = "00:12:06:21:88:70";
    String UUID = "00001101-0000-1000-8000-00805F9B34FB";
    String MESSAGE_TEMPLATE = "S%02dA%03d\n";

    int RESPONSE_OK = 0x01;
    int RESPONSE_FAILED = 0x02;
}
