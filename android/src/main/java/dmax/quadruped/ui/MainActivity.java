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

    @Bind(R.id.log) TextView log;
    @Bind(R.id.connect) Button connect;
    @Bind(R.id.servo) SeekBar seekBar;
    @Bind(R.id.status) ImageView status;

    private ConnectorServiceClient connector = new ConnectorServiceClient();
    private ConnectorServiceClient.Callback connectionCallback = new ConnectionCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        seekBar.setMax(180);
        seekBar.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(new TrackingListener());
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

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int value = seekBar.getProgress();
            int id = 0;
            log.append(String.format("Send servo command: %d %d\n", id, value));
            connector.sendCommand(id, value);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
    }
}
