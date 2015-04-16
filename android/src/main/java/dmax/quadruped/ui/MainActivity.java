package dmax.quadruped.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import dmax.quadruped.R;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

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


    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    private void initSeekBar(int id) {
        SeekBar seekBar = (SeekBar) findViewById(id);
        seekBar.setMax(180);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int value = seekBar.getProgress();
        Toast.makeText(this, value + "", Toast.LENGTH_SHORT).show();
        switch (seekBar.getId()) {
            case R.id.servo1: {

                return;
            }
            case R.id.servo2: {

                return;
            }
            case R.id.servo3: {

                return;
            }
            case R.id.servo4: {

                return;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
}
