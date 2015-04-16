package dmax.quadruped.bluetooth;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 17:28
 */
public interface BluetoothCallback {

    enum Response {
        OK,
        FAILED;

        public static Response valueOf(int response) {
            switch (response) {
                case 1: return OK;
                case 2: return FAILED;
                default: return null;
            }
        }
    }

    void onConnected();

    void onFailed();

    void onResponse(Response response);
}
