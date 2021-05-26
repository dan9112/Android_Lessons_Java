package lord.main.t1_service_with_notifications.room_db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;

/**
 * Интерфейс для работы с таблицой каналов уведомлений БД
 */
@Dao
public interface TableChannelsDao {

    /**
     * Зарегистрировать канал уведомлений в БД
     *
     * @param channel сохраняемые в БД атрибуты канала
     * @return уникальный идентификатор записи в таблице
     */
    @Insert
    String registrChannel(TableChannels channel) throws Exception;

    /* Объединить две функции ниже в сервисе! */

    /**
     * Функция получения уникальных идентификаторов всех зарегистрированных каналов
     *
     * @return список уникальных идентификаторов
     */
    @Query("SELECT id FROM TableChannels")
    ArrayList<String> getIds();

    /**
     * Функция получения наименований всех зарегистрированных каналов
     *
     * @return список наименований
     */
    @Query("SELECT name FROM TableChannels")
    ArrayList<String> getNames();
}
