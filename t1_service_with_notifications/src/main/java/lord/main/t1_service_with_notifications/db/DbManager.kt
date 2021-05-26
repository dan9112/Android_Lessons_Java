package lord.main.t1_service_with_notifications.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import lord.main.t1_service_with_notifications.supporting_classes.RecyclerViewItem
import java.util.*

/**
 * Класс для управления БД
 */
class DbManager(context: Context) {
    /**
     * Помощник для взаимодействия с БД
     */
    private val dbHelper: DbHelper = DbHelper(context)

    /**
     * БД
     */
    private lateinit var db: SQLiteDatabase

    /**
     * Открыть БД
     */
    fun open() {
        db = dbHelper.writableDatabase
    }

    /**
     * Закрыть БД
     */
    fun close() {
        dbHelper.close()
    }

    /**
     * Функция регистрации уведомления в таблице истории уведомлений
     *
     * @param channelId уникальный идентификатор канал, по которому было отправлено уведомление
     * @param title     заголовок уведомления
     * @param text      текст уведомления
     * @param iconRes   уникальный идентификатор изображения иконки уведомления из ресурсов
     * @param colorRes  уникальный идентификатор цвета иконки уведомления из ресурсов
     * @return уникальный идентификатор строки, созданной в случае успеха транзакции, либо -1
     */
    fun registerNotification(
        channelId: String,
        title: String,
        text: String,
        iconRes: Int,
        colorRes: Int
    ): Int {
        val id: Long
        val cv = ContentValues()
        cv.put(TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_CHANNEL, channelId)
        cv.put(TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_COLOR, colorRes)
        cv.put(TableNotificationsHistory.NOTIFICATIONS_HISTORY_TITLE, title)
        cv.put(TableNotificationsHistory.NOTIFICATIONS_HISTORY_TEXT, text)
        cv.put(TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_ICON, iconRes)
        db.beginTransaction()
        try {
            id = db.insert(TableNotificationsHistory.NOTIFICATIONS_HISTORY_TABLE, null, cv)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        return id.toInt()
    }

    /**
     * Функция регистрации канала в таблице каналов уведомлений
     *
     * @param id              уникальный идентификатор канала
     * @param name            наименование канала
     * @param importanceLevel уровень важности канала
     * @param description     описание канала
     * @param lights          цвет светового сигнала или 13, если отключено
     * @param vibration       1 - включена вибрация, 0 - отключена
     * @param groupId         уникальный идентификатор группы каналов или "null", если канал не сгруппирован
     * @param sound           код мелодии: 0 - нет, 1 - по умолчанию, остальные по порядку в ресурсах
     * @return уникальный идентификатор строки, созданной в случае успеха транзакции, либо -1
     */
    fun registerNotificationChannel(
        id: String?,
        name: String?,
        importanceLevel: Int,
        description: String?,
        lights: Int,
        vibration: Int,
        groupId: String?,
        sound: Int
    ): Long {
        val rawId: Long
        val cv = ContentValues()
        cv.put(TableNotificationChannels.NOTIFICATION_CHANNELS_IDS, id)
        cv.put(TableNotificationChannels.NOTIFICATION_CHANNELS_NAME, name)
        cv.put(TableNotificationChannels.NOTIFICATION_CHANNELS_IMPORTANCE_LEVEL, importanceLevel)
        cv.put(TableNotificationChannels.NOTIFICATION_CHANNELS_DESCRIPTION, description)
        cv.put(TableNotificationChannels.NOTIFICATION_CHANNELS_ID_LIGHTS, lights)
        cv.put(TableNotificationChannels.NOTIFICATION_CHANNELS_VIBRATION, vibration)
        cv.put(TableNotificationChannels.NOTIFICATION_CHANNELS_ID_GROUP, groupId)
        cv.put(TableNotificationChannels.NOTIFICATION_CHANNELS_ID_SOUND, sound)
        db.beginTransaction()
        try {
            rawId = db.insert(TableNotificationChannels.NOTIFICATION_CHANNELS_TABLE, null, cv)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        return rawId
    }

    /**
     * Функция удаления строки из истории уведомлений
     *
     * @param id уникальный идентификатор строки
     * @return
     */
    fun removeHistoryRow(id: Int): Int {
        return db.delete(
            TableNotificationsHistory.NOTIFICATIONS_HISTORY_TABLE,
            BaseColumns._ID + " = " + id,
            null
        )
    }

    /**
     * Функция получения всей записанной в БД истории уведомлений
     *
     * @return курсор с данными либо null, если таблица пуста
     */
    val allHistory: ArrayList<RecyclerViewItem>?
        get() {
            val c = db.query(
                TableNotificationsHistory.NOTIFICATIONS_HISTORY_TABLE, arrayOf(
                    BaseColumns._ID,
                    TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_CHANNEL,
                    TableNotificationsHistory.NOTIFICATIONS_HISTORY_TITLE,
                    TableNotificationsHistory.NOTIFICATIONS_HISTORY_TEXT,
                    TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_ICON,
                    TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_COLOR
                ),
                null,
                null,
                null,
                null,
                BaseColumns._ID
            )
            val notifies = ArrayList<RecyclerViewItem>()
            return if (c.moveToFirst()) {
                do {
                    val item = RecyclerViewItem
                    item.id = c.getInt(0)
                    item.channelId = c.getString(1)
                    item.title = c.getString(2)
                    item.text = c.getString(3)
                    item.iconId = c.getInt(4)
                    item.iconColor = c.getInt(5)
                    notifies.add(item)
                } while (c.moveToNext())
                c.close()
                notifies
            } else null
        }

    /**
     * Функция получения имён всех каналов уведомлений из БД
     *
     * @return курсор с данными либо null, если таблица пуста
     */
    val notificationChannelNames: ArrayList<ArrayList<String>>?
        get() {
            val list =
                ArrayList<ArrayList<String>>() // объединённый список, который возвращается в результате
            val names = ArrayList<String>() // список со всеми наименованиями каналов
            val ids = ArrayList<String>() // список со всеми уникальными идентификаторами каналов
            val c = db.query(
                TableNotificationChannels.NOTIFICATION_CHANNELS_TABLE,
                arrayOf(
                    TableNotificationChannels.NOTIFICATION_CHANNELS_NAME,
                    TableNotificationChannels.NOTIFICATION_CHANNELS_IDS
                ),
                null,
                null,
                null,
                null,
                TableNotificationChannels.NOTIFICATION_CHANNELS_NAME
            )
            return if (c.moveToFirst()) {
                while (c.moveToNext()) {
                    names.add(c.getString(0))
                    ids.add(c.getString(1))
                }
                c.close()
                list.add(names)
                list.add(ids)
                list
            } else {
                null
            }
        }
}