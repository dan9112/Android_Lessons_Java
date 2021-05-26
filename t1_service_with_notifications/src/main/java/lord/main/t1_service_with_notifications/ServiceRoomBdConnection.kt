package lord.main.t1_service_with_notifications

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.room.Room
import lord.main.t1_service_with_notifications.room_db.AppDatabase
import lord.main.t1_service_with_notifications.room_db.TableChannels
import lord.main.t1_service_with_notifications.room_db.TableHistory
import java.lang.Exception
import java.util.*

class ServiceRoomBdConnection : Service() {
    /**
     * Объект для взаимодействия с сервисом
     */
    private var binder: Binder = Binder()

    private lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "db").build()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    /**
     * Функция сохранения уведомления в таблицу истории уведомлений БД
     *
     * @param channelId    уникальный идентификатор канала уведомлений
     * @param title        заголовок уведомления
     * @param text         текст уведомления
     * @param iconRes      ссылка на иконку в ресурсах
     * @param iconColorRes ссылка на цвет иконки в ресурсах
     * @return уникальный идентификатор строки в таблице, либо -1, если добавление не произошло
     */
    fun registrNotify(
        channelId: String,
        title: String,
        text: String,
        iconRes: Int,
        iconColorRes: Int
    ): Long {
        return db.historyDao().registerNotify(TableHistory().apply {
            this.channelID = channelId
            this.title = title
            this.text = text
            this.iconRes = iconRes
            this.iconColorRes = iconColorRes
        })
    }

    /**
     * Функция возвращает список, состоящий из подсписков наименований каналов уведомлений и их уникальными идентификаторами
     *
     * @return возвращает двухуровневый список либо null, если таблица пуста
     */
    fun allChannels(): ArrayList<ArrayList<String>>? {
        val names = db.channelsDao().names
        return if (names.isNotEmpty())
            arrayListOf(names, db.channelsDao().ids)
        else null
    }

    /**
     * Функция добавляет в таблицу каналов уведомлений новую строку
     *
     * @param count           количество каналов уведомлений в приложении минус один для получения свободного индекса
     * @param name            наименование
     * @param importanceLevel уровень важности
     * @param description     описание
     * @param lightsColorRes  цвет светового сигнала или null, если отключено
     * @param vibration       1 - включена вибрация, 0 - отключена
     * @param groupId         уникальный идентификатор группы каналов или "null", если канал не сгруппирован
     * @param soundRes        код мелодии: 0 - нет, 1 - по умолчанию, остальные по порядку в ресурсах
     * @return уникальный идентификатор строки в таблице, либо "double insert", если он занят
     */
    fun addChannel(
        count: Int,
        name: String,
        importanceLevel: Int,
        description: String,
        lightsColorRes: Int,
        vibration: Int,
        groupId: String,
        soundRes: Int
    ): String {
        return try {
            db.channelsDao().registrChannel(TableChannels().apply {
                this.id = "My app notification channel $count"
                this.name = name
                this.importanceLevel = importanceLevel
                this.description = description
                this.idLights = lightsColorRes
                this.vibration = vibration
                this.groupId = groupId
                this.soundId = soundRes
            })
        } catch (e: Exception) {
            "double insert"
        }
    }




    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    /**
     * Класс для реализации функций, доступных из связанной с сервисом активности
     */
    inner class Binder : android.os.Binder() {
        /**
         * Функция возвращает объект сервиса для использования его функций
         *
         * @return объект сервиса
         */
        val service: ServiceRoomBdConnection
            get() = this@ServiceRoomBdConnection
    }
}