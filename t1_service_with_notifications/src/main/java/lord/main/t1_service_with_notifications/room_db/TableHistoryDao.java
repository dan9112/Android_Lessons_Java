package lord.main.t1_service_with_notifications.room_db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface TableHistoryDao {

    /**
     * Зарегистрировать уведомление в БД
     *
     * @param notify сохраняемые в БД атрибуты уведомления
     * @return уникальный идентификатор записи в таблице
     */
    @Insert
    long registerNotify(TableHistory notify);

    /**
     * Функция удаления записи из таблицы истории уведомлений по её уникальному идентификатору
     *
     * @param id уникальный идентификатор записи
     * @return количество удалённых строк
     */
    @Query("DELETE FROM TableHistory WHERE id = :id")
    int unregistrNotify(long id);

    /**
     * Функция получения всех записей в истории уведомлений
     *
     * @return список всех зарегистрированных уведомлений
     */
    @Query("SELECT * FROM TableHistory")
    ArrayList<TableHistory> getAll();
}
