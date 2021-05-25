package lord.main.t1_service_with_notifications.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import lord.main.t1_service_with_notifications.supporting_classes.RecyclerViewItem;

import static android.provider.BaseColumns._ID;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_DESCRIPTION;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_IDS;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_ID_GROUP;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_ID_LIGHTS;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_ID_SOUND;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_IMPORTANCE_LEVEL;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_NAME;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_TABLE;
import static lord.main.t1_service_with_notifications.db.TableNotificationChannels.NOTIFICATION_CHANNELS_VIBRATION;
import static lord.main.t1_service_with_notifications.db.TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_CHANNEL;
import static lord.main.t1_service_with_notifications.db.TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_COLOR;
import static lord.main.t1_service_with_notifications.db.TableNotificationsHistory.NOTIFICATIONS_HISTORY_ID_ICON;
import static lord.main.t1_service_with_notifications.db.TableNotificationsHistory.NOTIFICATIONS_HISTORY_TABLE;
import static lord.main.t1_service_with_notifications.db.TableNotificationsHistory.NOTIFICATIONS_HISTORY_TEXT;
import static lord.main.t1_service_with_notifications.db.TableNotificationsHistory.NOTIFICATIONS_HISTORY_TITLE;

/**
 * Класс для управления БД
 */
public class DbManager {
    /**
     * Помощник для взаимодействия с БД
     */
    private final DbHelper dbHelper;
    /**
     * БД
     */
    private SQLiteDatabase db;

    /**
     * Создание объекта для взаимодействия с БД
     *
     * @param context контекст, в котором производятся операции с БД
     */
    public DbManager(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Открыть БД
     */
    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Закрыть БД
     */
    public void close() {
        dbHelper.close();
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
    public int registerNotification(String channelId, String title, String text, int iconRes, int colorRes) {
        long id;

        ContentValues cv = new ContentValues();
        cv.put(NOTIFICATIONS_HISTORY_ID_CHANNEL, channelId);
        cv.put(NOTIFICATIONS_HISTORY_ID_COLOR, colorRes);
        cv.put(NOTIFICATIONS_HISTORY_TITLE, title);
        cv.put(NOTIFICATIONS_HISTORY_TEXT, text);
        cv.put(NOTIFICATIONS_HISTORY_ID_ICON, iconRes);
        db.beginTransaction();
        try {
            id = db.insert(NOTIFICATIONS_HISTORY_TABLE, null, cv);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return (int) id;
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
    public long registerNotificationChannel(String id, String name, int importanceLevel, String description, int lights, int vibration, String groupId, int sound) {
        long id_raw;

        ContentValues cv = new ContentValues();
        cv.put(NOTIFICATION_CHANNELS_IDS, id);
        cv.put(NOTIFICATION_CHANNELS_NAME, name);
        cv.put(NOTIFICATION_CHANNELS_IMPORTANCE_LEVEL, importanceLevel);
        cv.put(NOTIFICATION_CHANNELS_DESCRIPTION, description);
        cv.put(NOTIFICATION_CHANNELS_ID_LIGHTS, lights);
        cv.put(NOTIFICATION_CHANNELS_VIBRATION, vibration);
        cv.put(NOTIFICATION_CHANNELS_ID_GROUP, groupId);
        cv.put(NOTIFICATION_CHANNELS_ID_SOUND, sound);
        db.beginTransaction();
        try {
            id_raw = db.insert(NOTIFICATION_CHANNELS_TABLE, null, cv);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return id_raw;
    }

    // /**
    //  * Функция обновления канала в таблице каналов уведомлений
    //  *
    //  * @param id              уникальный идентификатор строки таблицы каналов уведомлений, в которой записана информация об изменяемом канале
    //  * @param name            наименование канала
    //  * @param importanceLevel уровень важности канала
    //  * @param description     описание канала
    //  * @param lights          цвет светового сигнала или 13, если отключено
    //  * @param vibration       1 - включена вибрация, 0 - отключена
    //  * @param groupId         уникальный идентификатор группы каналов или "null", если канал не сгруппирован
    //  * @param soundId         уникальный идентификатор мелодии из ресурсов
    //  * @return уникальный идентификатор строки, созданной в случае успеха транзакции, либо -1
    //  */
    // public void updateNotificationChannel(int id, String name, int importanceLevel, String description, int lights, int vibration, String groupId, int soundId) {
    //     ContentValues cv = new ContentValues();
    //     cv.put(NOTIFICATION_CHANNELS_NAME, name);
    //     cv.put(NOTIFICATION_CHANNELS_IMPORTANCE_LEVEL, importanceLevel);
    //     cv.put(NOTIFICATION_CHANNELS_DESCRIPTION, description);
    //     cv.put(NOTIFICATION_CHANNELS_ID_LIGHTS, lights);
    //     cv.put(NOTIFICATION_CHANNELS_VIBRATION, vibration);
    //     cv.put(NOTIFICATION_CHANNELS_ID_GROUP, groupId);
    //     cv.put(NOTIFICATION_CHANNELS_ID_SOUND, soundId);
    //     String clause = _ID + "= ?";
    //     db.beginTransaction();
    //     try {
    //         db.update(NOTIFICATION_CHANNELS_TABLE, cv, clause, new String[]{String.valueOf(id)});
    //         db.setTransactionSuccessful();
    //     } finally {
    //         db.endTransaction();
    //     }
    // }

    /**
     * Функция удаления строки из истории уведомлений
     *
     * @param id уникальный идентификатор строки
     * @return
     */
    public int removeHistoryRow(int id) {
        return db.delete(NOTIFICATIONS_HISTORY_TABLE, _ID + " = " + id, null);
    }

    /**
     * Функция получения всей записанной в БД истории уведомлений
     *
     * @return курсор с данными либо null, если таблица пуста
     */
    public ArrayList<RecyclerViewItem> getAllHistory() {
        Cursor c = db.query(NOTIFICATIONS_HISTORY_TABLE,
                new String[]{
                        _ID,
                        NOTIFICATIONS_HISTORY_ID_CHANNEL,
                        NOTIFICATIONS_HISTORY_TITLE,
                        NOTIFICATIONS_HISTORY_TEXT,
                        NOTIFICATIONS_HISTORY_ID_ICON,
                        NOTIFICATIONS_HISTORY_ID_COLOR
                },
                null,
                null,
                null,
                null,
                _ID);

        ArrayList<RecyclerViewItem> notifies = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                RecyclerViewItem item = new RecyclerViewItem();
                item.id = c.getInt(0);
                item.channelId = c.getString(1);
                item.title = c.getString(2);
                item.text = c.getString(3);
                item.iconId = c.getInt(4);
                item.iconColor = c.getInt(5);
                notifies.add(item);
            } while (c.moveToNext());
            c.close();
            return notifies;
        } else return null;
    }

    // /**
    //  * Функция получения всех зарегистрированных каналов уведомлений из БД
    //  *
    //  * @return курсор с данными либо null, если таблица пуста
    //  */
    // public Cursor getAllNotificationChannels() {
    //     Cursor c = db.query(NOTIFICATION_CHANNELS_TABLE,
    //             new String[]{
    //                     _ID,
    //                     NOTIFICATION_CHANNELS_NAME,
    //                     NOTIFICATION_CHANNELS_DESCRIPTION,
    //                     NOTIFICATION_CHANNELS_ID_GROUP,
    //                     NOTIFICATION_CHANNELS_IMPORTANCE_LEVEL,
    //                     NOTIFICATION_CHANNELS_ID_LIGHTS,
    //                     NOTIFICATION_CHANNELS_VIBRATION,
    //                     NOTIFICATION_CHANNELS_ID_SOUND
    //             },
    //             null,
    //             null,
    //             null,
    //             null,
    //             _ID);
    //     if (c.moveToFirst()) return c;
    //     return null;
    // }

    /**
     * Функция получения имён всех каналов уведомлений из БД
     *
     * @return курсор с данными либо null, если таблица пуста
     */
    public Cursor getAllNotificationChannelNames() {
        Cursor c = db.query(NOTIFICATION_CHANNELS_TABLE,
                new String[]{NOTIFICATION_CHANNELS_NAME, NOTIFICATION_CHANNELS_IDS}, null, null, null, null, NOTIFICATION_CHANNELS_NAME);
        if (c.moveToFirst()) return c;
        else return null;
    }

    // /**
    //  * Метод возвращает наименование канала уведомлений по уникальному идентификатору
    //  *
    //  * @param id уникальный идентификатор канала уведомлений
    //  * @return наименование канала уведомлений
    //  */
    // public String getChannelNameById(int id) {
    //     Cursor c = db.query(NOTIFICATION_CHANNELS_TABLE, new String[]{NOTIFICATION_CHANNELS_NAME}, null, null, null, null, null);
    //     c.moveToFirst();
    //     return c.getString(0);
    // }
}
