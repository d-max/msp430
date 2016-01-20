package dmax.quadruped.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import dmax.quadruped.Logger;
import dmax.quadruped.R;
import dmax.quadruped.connection.ConnectorServiceClient;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener, ConnectorServiceClient.Callback {

    private Logger log = new Logger("QuadrupedActivity");
    private ConnectorServiceClient connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connector = new ConnectorServiceClient();

        setContentView(R.layout.main);

        initSeekBar(R.id.servo1);
        initSeekBar(R.id.servo2);
        initSeekBar(R.id.servo3);
    }

    @Override
    protected void onStart() {
        super.onStart();
        log.d("bind service");
        connector.bindToService(this, this);
    }

    @Override
    protected void onStop() {
        log.d("unbind service");
        connector.unbindFromService(this);
        super.onStop();
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
        log.d("send servo command: %d %d", id, value);
        connector.sendCommand(id, value);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onSent(boolean result) {
        log.d("sending result: %b", result);
        if (!result) {
            enableSeekBar(R.id.servo1, false);
            enableSeekBar(R.id.servo2, false);
            enableSeekBar(R.id.servo3, false);
        }
    }

    @Override
    public void onReady() {
        log.d("binded");
        enableSeekBar(R.id.servo1, true);
        enableSeekBar(R.id.servo2, true);
        enableSeekBar(R.id.servo3, true);
    }
}
