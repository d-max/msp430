package dmax.bt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SeekBar;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private static final String DEVICE = "94:51:03:0A:8B:1F";
    private static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private BluetoothAdapter adapter;

    private BufferedWriter out;
    private InputStream in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        initSeekBar(R.id.servo1);
        initSeekBar(R.id.servo2);
        initSeekBar(R.id.servo3);
        initSeekBar(R.id.servo4);
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.enable();
    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.disable();
    }

    @Override
    protected void onResume() {
        super.onResume();

        BluetoothDevice device = adapter.getRemoteDevice(DEVICE);
        BluetoothSocket socket = null;
        out = null;
        in = null;

        if (device == null) return;

        try {
            UUID uuid = java.util.UUID.fromString(UUID);
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
            in = socket.getInputStream();
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(out);
            close(in);
            close(socket);
        }
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ex) {
                // silent
            }
        }
    }

    private void initSeekBar(int id) {
        SeekBar seekBar = (SeekBar) findViewById(id);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int value = seekBar.getProgress() * 57 + 1500;
        switch (seekBar.getId()) {
            case R.id.servo1: {
                new Sender().execute(1, value);
                return;
            }
            case R.id.servo2: {
                new Sender().execute(2, value);
                return;
            }
            case R.id.servo3: {
                new Sender().execute(3, value);
                return;
            }
            case R.id.servo4: {
                new Sender().execute(4, value);
                return;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    class Sender extends AsyncTask<Integer, Void, Void> {

        int current = 0;
        String[] messages = new String[3];

        @Override
        protected Void doInBackground(Integer... params) {
            messages[0] = "S" + params[0] + "\n";
            messages[1] = "T" + params[1] + "\n";
            messages[2] = "P\n";

            while (current < 3) {
                try {
                    out.write(messages[current]);
                    out.flush();
                    if (in.read() == 1) {
                        current ++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
