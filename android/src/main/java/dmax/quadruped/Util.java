package dmax.quadruped;

import java.io.Closeable;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.04.15 at 16:59
 */
public class Util {

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ex) {
                // silent
            }
        }
    }
}
