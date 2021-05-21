package lord.main.t1_service_with_notifications.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static lord.main.t1_service_with_notifications.db.DbNameClass.DB_NAME;
import static lord.main.t1_service_with_notifications.db.DbNameClass.DB_VERSION;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.CREATE_NOTIFICATION_CHANNELS_TABLE;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.DROP_NOTIFICATION_CHANNELS_TABLE;
import static lord.main.t1_service_with_notifications.db.TableNotificationsHistory.CREATE_NOTIFICATIONS_HISTORY_TABLE;
import static lord.main.t1_service_with_notifications.db.TableNotificationsHistory.DROP_NOTIFICATIONS_HISTORY_TABLE;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTIFICATION_CHANNELS_TABLE);
        db.execSQL(CREATE_NOTIFICATIONS_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_NOTIFICATIONS_HISTORY_TABLE);
        db.execSQL(DROP_NOTIFICATION_CHANNELS_TABLE);
        onCreate(db);
    }
}
