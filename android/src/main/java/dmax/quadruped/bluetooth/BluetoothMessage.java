package dmax.quadruped.bluetooth;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 17:29
 */
public class BluetoothMessage {
    final int servoId;
    final int angle;

    public BluetoothMessage(int servoId, int angle) {
        this.servoId = servoId;
        this.angle = angle;
    }
}
