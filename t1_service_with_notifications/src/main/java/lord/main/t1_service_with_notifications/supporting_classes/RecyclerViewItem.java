package lord.main.t1_service_with_notifications.supporting_classes;

import lord.main.t1_service_with_notifications.ActivityShowNotifyHistory;

/**
 * Шаблон со всеми необходимыми данными для элемента RecyclerView в {@link ActivityShowNotifyHistory активности демонстрации истории уведомлений}
 */
public class RecyclerViewItem {
    /**
     * Уникальный идентификатор уведомления
     */
    public int id,
    /**
     * Уникальный идентификатор иконки из ресурсов
     */
    iconId,
    /**
     * Цвет заливки иконки
     */
    iconColor;
    /**
     * Уникальный идентификатор канала уведомлений
     */
    public String channelId,
    /**
     * Заголовок уведомления
     */
    title,
    /**
     * Текст уведомления
     */
    text;
}
