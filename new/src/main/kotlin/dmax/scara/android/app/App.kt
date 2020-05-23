package dmax.scara.android.app

import android.app.Application
import dmax.scara.android.connect.bluetooth.enableBluetooth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                core(),
                mvvm()
            )
        }

        GlobalScope.launch {
            enableBluetooth()
        }
    }
}
