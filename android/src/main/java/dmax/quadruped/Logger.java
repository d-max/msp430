package dmax.quadruped;

import android.util.Log;

/**
 * Created by Maksym Dybarskyi | maxim.dybarskyy@gmail.com
 * on 12.05.15 at 15:50
 */
public class Logger {

    private String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    public void d(String message, Object... args) {
        Log.d(tag, String.format(message, args));
    }

    public void e(String message, Object... args) {
        Log.e(tag, String.format(message, args));
    }
}
