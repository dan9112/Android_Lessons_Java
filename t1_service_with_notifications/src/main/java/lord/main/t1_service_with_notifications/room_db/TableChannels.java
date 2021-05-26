package lord.main.t1_service_with_notifications.room_db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Класс, представляющий собой представление таблицы каналов уведомлений в БД
 */
@Entity
public class TableChannels {

    /**
     * Уникальный идентификатор канала уведомлений
     */
    @PrimaryKey
    public String id;

    /**
     * Наименование канала уведомлений
     */
    public String name;

    /**
     * Уровень важности сообщений канала уведомлений
     */
    public int importanceLevel;

    /**
     * Описание канала уведомлений
     */
    public String description;

    /**
     * Уникальный идентификатор цвета индикации о пришедшем уведомлении по каналу в таблице цветов.
     * <p>
     * null - индикация цветом отключена для канала
     * </p>
     */
    public int idLights;

    /**
     * Флаг включения вибрации для индикации о пришедшем уведомлении по каналу
     */
    public int vibration;

    /**
     * Уникальный идентификатор группы, к которой принадлежит канал.
     * <p>
     * null - канал не сгруппирован
     * </p>
     */
    public String groupId;

    /**
     * Уникальный индикатор мелодии уведомления из ресурсов.
     * <p>
     * null - звуковые уведомления отключены, default - уведомления по умолчанию
     * </p>
     */
    public int soundId;
}
