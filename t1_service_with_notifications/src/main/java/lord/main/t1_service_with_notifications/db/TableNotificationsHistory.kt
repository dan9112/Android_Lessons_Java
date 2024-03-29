package lord.main.t1_service_with_notifications.db

import android.provider.BaseColumns

/**
 * Класс для взаимодействия с таблицей истории уведомлений
 */
object TableNotificationsHistory : BaseColumns {
    /**
     * Наименование таблицы
     */
    const val NOTIFICATIONS_HISTORY_TABLE = "notifications_history"

    /**
     * Уникальный идентификатор канала уведомлений из таблицы "notification_channels"
     */
    const val NOTIFICATIONS_HISTORY_ID_CHANNEL = "channel_id"

    /**
     * Заголовок уведомления
     */
    const val NOTIFICATIONS_HISTORY_TITLE = "notification_title"

    /**
     * Текст уведомления
     */
    const val NOTIFICATIONS_HISTORY_TEXT = "notification_text"

    /**
     * Уникальный идентификатор иконки уведомления из ресурсов
     */
    const val NOTIFICATIONS_HISTORY_ID_ICON = "notification_icon_id"

    /**
     * Уникальный идентификатор цвета иконки уведомления из ресурсов
     */
    const val NOTIFICATIONS_HISTORY_ID_COLOR = "notification_color_id"

    /**
     * Создать таблицу истории уведомлений
     */
    const val CREATE_NOTIFICATIONS_HISTORY_TABLE =
        "CREATE TABLE IF NOT EXISTS $NOTIFICATIONS_HISTORY_TABLE (${BaseColumns._ID} " +
                "INTEGER PRIMARY KEY, $NOTIFICATIONS_HISTORY_ID_CHANNEL TEXT, " +
                "$NOTIFICATIONS_HISTORY_TITLE TEXT, $NOTIFICATIONS_HISTORY_TEXT TEXT, " +
                "$NOTIFICATIONS_HISTORY_ID_ICON INTEGER, $NOTIFICATIONS_HISTORY_ID_COLOR INTEGER);"

    /**
     * Удалить таблицу истории уведомлений
     */
    const val DROP_NOTIFICATIONS_HISTORY_TABLE =
        "DROP TABLE IF EXISTS $NOTIFICATIONS_HISTORY_TABLE;"
}