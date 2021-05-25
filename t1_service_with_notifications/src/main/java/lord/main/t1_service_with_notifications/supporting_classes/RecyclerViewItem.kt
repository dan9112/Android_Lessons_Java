package lord.main.t1_service_with_notifications.supporting_classes

import lord.main.t1_service_with_notifications.ActivityShowNotifyHistory

/**
 * Шаблон со всеми необходимыми данными для элемента RecyclerView в [активности демонстрации истории уведомлений][ActivityShowNotifyHistory]
 */
object RecyclerViewItem {
    /**
     * Уникальный идентификатор уведомления
     */
    var id = 0

    /**
     * Уникальный идентификатор иконки из ресурсов
     */
    var iconId = 0

    /**
     * Цвет заливки иконки
     */
    var iconColor = 0

    /**
     * Уникальный идентификатор канала уведомлений
     */
    var channelId = ""

    /**
     * Заголовок уведомления
     */
    var title = ""

    /**
     * Текст уведомления
     */
    var text = ""
}