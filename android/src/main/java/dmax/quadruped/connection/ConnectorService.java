package dmax.quadruped.connection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Maksym Dybarskyi | maxim.dybarskyy@gmail.com
 * on 07.05.15 at 17:11
 */
public class ConnectorService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
