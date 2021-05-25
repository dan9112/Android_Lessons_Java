package lord.main.t1_service_with_notifications.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DbNameClass.DB_NAME, null, DbNameClass.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TableNotificationChannels.CREATE_NOTIFICATION_CHANNELS_TABLE)
        db.execSQL(TableNotificationsHistory.CREATE_NOTIFICATIONS_HISTORY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(TableNotificationsHistory.DROP_NOTIFICATIONS_HISTORY_TABLE)
        db.execSQL(TableNotificationChannels.DROP_NOTIFICATION_CHANNELS_TABLE)
        onCreate(db)
    }
}