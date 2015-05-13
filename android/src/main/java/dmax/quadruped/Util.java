package dmax.quadruped;

import java.io.Closeable;
import java.lang.reflect.Field;

import dmax.quadruped.connection.Constants;

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

    public static String findConstantName(int constant) {
        try {
            for (Field f : Constants.class.getDeclaredFields())
                if (f.getInt(null) == constant)
                    return f.getName();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
