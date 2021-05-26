package lord.main.t1_service_with_notifications.room_db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Класс, представляющий собой представление таблицы истории уведомлений в БД
 */
@Entity
public class TableHistory {

    /**
     * Уникальный идентификатор строки в таблице истории уведомлений
     */
    @PrimaryKey(autoGenerate = true)
    public long id;

    /**
     * Уникальный идентификатор канала уведомлений
     */
    public String channelID;

    /**
     * Заголовок уведомления
     */
    public String title;

    /**
     * Текст уведомления
     */
    public String text;

    /**
     * Уникальный идентификатор иконки уведомления из ресурсов
     */
    public int iconRes;

    /**
     * Уникальный идентификатор цвета иконки уведомления из ресурсов
     */
    public int iconColorRes;
}
