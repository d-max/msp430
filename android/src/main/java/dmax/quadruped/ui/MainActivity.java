package dmax.quadruped.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import dmax.quadruped.R;
import dmax.quadruped.connection.bluetooth.BluetoothCallback;
import dmax.quadruped.connection.bluetooth.BluetoothConnector;
import dmax.quadruped.connection.bluetooth.BluetoothMessage;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private BluetoothConnector bluetooth;
    private BluetoothCallback callback = new BluetoothCallback() {
        @Override
        public void onConnected() {
            enableSeekBar(R.id.servo1, true);
            enableSeekBar(R.id.servo2, true);
            enableSeekBar(R.id.servo3, true);
        }

        @Override
        public void onFailed() {
            enableSeekBar(R.id.servo1, false);
            enableSeekBar(R.id.servo2, false);
            enableSeekBar(R.id.servo3, false);
        }

        @Override
        public void onResponse(Response response) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bluetooth = new BluetoothConnector(this, callback);

        setContentView(R.layout.main);

        initSeekBar(R.id.servo1);
        initSeekBar(R.id.servo2);
        initSeekBar(R.id.servo3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetooth.connect();
    }

    @Override
    protected void onPause() {
        bluetooth.disconnect();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        bluetooth.quit();
        super.onDestroy();
    }

    private void initSeekBar(int id) {
        SeekBar seekBar = (SeekBar) findViewById(id);
        seekBar.setMax(180);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setEnabled(false);
    }

    private void enableSeekBar(int id, boolean enabled) {
        findViewById(id).setEnabled(enabled);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int value = seekBar.getProgress();
        int id = -1;
        switch (seekBar.getId()) {
            case R.id.servo1: id = 0; break;
            case R.id.servo2: id = 1; break;
            case R.id.servo3: id = 2; break;
        }
        BluetoothMessage message = new BluetoothMessage(id, value);
        bluetooth.send(message);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
}
