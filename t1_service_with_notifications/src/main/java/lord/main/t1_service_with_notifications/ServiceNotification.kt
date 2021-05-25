package lord.main.t1_service_with_notifications

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ServiceNotification : Service() {
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
}