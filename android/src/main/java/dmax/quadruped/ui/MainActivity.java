package dmax.quadruped.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dmax.quadruped.R;
import dmax.quadruped.connection.ConnectorServiceClient;

public class MainActivity extends Activity {

    TextView log;
    Button connect;
    SeekBar seekBar;
    SeekBar seekBar2;
    SeekBar seekBar3;
    ImageView status;

    private ConnectorServiceClient connector = new ConnectorServiceClient();
    private ConnectorServiceClient.Callback connectionCallback = new ConnectionCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        log = findViewById(R.id.log);
        connect = findViewById(R.id.connect);
        seekBar = findViewById(R.id.servo);
        seekBar2 = findViewById(R.id.servo2);
        seekBar3 = findViewById(R.id.servo3);
        status = findViewById(R.id.status);

        initViews();
    }

    private void initViews() {
        seekBar.setMax(180);
        seekBar.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(new TrackingListener(0));
        seekBar2.setMax(180);
        seekBar2.setEnabled(false);
        seekBar2.setOnSeekBarChangeListener(new TrackingListener(1));
        seekBar3.setMax(180);
        seekBar3.setEnabled(false);
        seekBar3.setOnSeekBarChangeListener(new TrackingListener(2));
        connect.setOnClickListener(new ConnectClickListener());
        log.setOnClickListener(new ClearListener());
        status.setBackgroundResource(R.drawable.disconnected);
    }

    //~

    class ConnectionCallback implements ConnectorServiceClient.Callback {

        @Override
        public void onConnected() {
            log.append("Binded\nConnecting...\n");
            status.setBackgroundResource(R.drawable.binded);
        }

        @Override
        public void onSent(boolean result) {
            log.append(String.format("Sending result: %b\n", result));
        }

        @Override
        public void onReady() {
            log.append("Ready\n");
            status.setBackgroundResource(R.drawable.ready);
            connect.setEnabled(true);
            connect.setText(R.string.disconnect);
            seekBar.setEnabled(true);
            seekBar2.setEnabled(true);
            seekBar3.setEnabled(true);
        }
    }

    class ConnectClickListener implements  View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (connector.isConnected()) {
                log.append("Unbind...\n");
                status.setBackgroundResource(R.drawable.disconnected);
                connect.setText(R.string.connect);
                seekBar.setEnabled(false);
                seekBar2.setEnabled(false);
                seekBar3.setEnabled(false);
                connector.unbindFromService(MainActivity.this);
            } else {
                log.append("Bind...\n");
                connect.setEnabled(false);
                connector.bindToService(MainActivity.this, connectionCallback);
            }
        }
    }

    class ClearListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            log.setText("");
        }
    }

    class TrackingListener implements SeekBar.OnSeekBarChangeListener {

        private int servoId;

        public TrackingListener(int servoId) {
            this.servoId = servoId;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int value = seekBar.getProgress();
            log.append(String.format("Send servo command: %d %d\n", servoId, value));
            connector.sendCommand(servoId, value);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
    }
}
