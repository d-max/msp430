package dmax.bt;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter btAdapter;
    private ArrayAdapter<BluetoothDevice> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        adapter = new ArrayAdapter<BluetoothDevice>(this, android.R.layout.test_list_item);
        ListView view = (ListView) findViewById(R.id.list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(this);

        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            showBtDevices();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT) {
            showBtDevices();
        }
    }

    private void showBtDevices() {
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                adapter.add(device);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice selected = adapter.getItem(position);

        BluetoothSocket socket = null;
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            socket = selected.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            Log.d("TAG", "1");
            e.printStackTrace();
        }

        btAdapter.cancelDiscovery();

        try {
            socket.connect();

        } catch (IOException e) {
            Log.d("TAG", "2");
            e.printStackTrace();
        }

        try {
            OutputStream stream = socket.getOutputStream();
            stream.write('a');
            stream.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = in.readLine();

            Toast.makeText(this, str, Toast.LENGTH_LONG).show();

            socket.close();
        } catch (IOException e) {
            Log.d("TAG", "3");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
