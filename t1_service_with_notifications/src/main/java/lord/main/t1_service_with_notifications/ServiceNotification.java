package lord.main.t1_service_with_notifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceNotification extends Service {
    public ServiceNotification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}