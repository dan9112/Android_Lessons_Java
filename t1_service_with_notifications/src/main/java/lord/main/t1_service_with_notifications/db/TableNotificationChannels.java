package lord.main.t1_service_with_notifications.db;

import android.provider.BaseColumns;

/**
 * Класс для взаимодействия с таблицей каналов уведомлений
 */
public class TableNotificationChannels implements BaseColumns {
    /**
     * Наименование таблицы
     */
    public static final String NOTIFICATION_CHANNELS_TABLE = "notification_channels",
    /**
     * Наименование канала уведомлений
     */
    NOTIFICATION_CHANNELS_NAME = "name",
    /**
     * Уникальный идентификатор канала уведомлений
     */
    NOTIFICATION_CHANNELS_IDS = "id",
    /**
     * Уровень важности сообщений канала уведомлений
     */
    NOTIFICATION_CHANNELS_IMPORTANCE_LEVEL = "importance_level",
    /**
     * Описание канала уведомлений
     */
    NOTIFICATION_CHANNELS_DESCRIPTION = "description",
    /**
     * Уникальный идентификатор цвета индикации о пришедшем уведомлении по каналу в таблице цветов.
     * <p>
     * null - индикация цветом отключена для канала
     * </p>
     */
    NOTIFICATION_CHANNELS_ID_LIGHTS = "id_lights",
    /**
     * Флаг включения вибрации для индикации о пришедшем уведомлении по каналу
     */
    NOTIFICATION_CHANNELS_VIBRATION = "vibration",
    /**
     * Уникальный идентификатор группы, к которой принадлежит канал.
     * <p>
     * null - канал не сгруппирован
     * </p>
     */
    NOTIFICATION_CHANNELS_ID_GROUP = "group_id",
    /**
     * Уникальный индикатор мелодии уведомления из ресурсов
     * <p>
     * null - звуковые уведомления отключены, default - уведомления по умолчанию
     * </p>
     */
    NOTIFICATION_CHANNELS_ID_SOUND = "sound_id",

    /**
     * Создать таблицу каналов уведомлений
     */
    CREATE_NOTIFICATION_CHANNELS_TABLE = "CREATE TABLE IF NOT EXISTS " + NOTIFICATION_CHANNELS_TABLE + " ( " +
            BaseColumns._ID + " INTEGER PRIMARY KEY, " + NOTIFICATION_CHANNELS_NAME + " TEXT, " +
            NOTIFICATION_CHANNELS_IDS + " TEXT, " + NOTIFICATION_CHANNELS_IMPORTANCE_LEVEL + " INTEGER, " +
            NOTIFICATION_CHANNELS_DESCRIPTION + " TEXT, " + NOTIFICATION_CHANNELS_ID_LIGHTS + " TEXT, " +
            NOTIFICATION_CHANNELS_VIBRATION + " TEXT, " + NOTIFICATION_CHANNELS_ID_GROUP + " TEXT, " +
            NOTIFICATION_CHANNELS_ID_SOUND + " INTEGER);",

    /**
     * Удалить таблицу каналов уведомлений
     */
    DROP_NOTIFICATION_CHANNELS_TABLE = "DROP TABLE IF EXISTS " + NOTIFICATION_CHANNELS_TABLE + ";";
}
