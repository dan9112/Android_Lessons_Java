package lord.main.t1_service_with_notifications

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import lord.main.t1_service_with_notifications.supporting_classes.SpinnerAdapter
import kotlin.collections.ArrayList

/**
 * Активность добавления нового канала уведомлений
 */
class ActivityAddChannel : AppCompatActivity() {
    /**
     * Экземпляр [сервиса для взаимодействия с БД][ServiceBdConnection] приложения
     */
    private lateinit var bdService: ServiceBdConnection

    /**
     * Кнопка создания и регистрации нового канала уведомлений
     */
    private lateinit var ok: Button

    /**
     * Выпадающее меню выбора уровня важности для уведомлений создаваемого канала уведомлений
     */
    private lateinit var importanceLevel: Spinner

    /**
     * Выпадающее меню выбора цвета индикаторов уведомлений для устройств, поддерживающих эту функцию
     */
    private lateinit var lightColor: Spinner

    /**
     * Выпадающее меню выбора группы, к которой будет принадлежать создаваемый канал уведомлений
     */
    private lateinit var groupId: Spinner

    /**
     * Выпадающее меню выбора звука канала
     */
    private lateinit var soundId: Spinner

    /**
     * Наименование создаваемого канала уведомлений
     */
    private lateinit var name: EditText

    /**
     * Описание создаваемого канала уведомлений
     */
    private lateinit var description: EditText

    /**
     * Чекбокс, являющийся также флагом подключения вибрации к каналу уведомлений
     */
    private lateinit var vibration: CheckBox

    /**
     * Менеджер уведомлений для управления ими
     */
    private lateinit var notificationManager: NotificationManager

    /**
     * Список всех групп каналов уведомлений
     */
    private lateinit var notificationGroups: ArrayList<String>

    /**
     * Флаг подключения к [сервису взаимодействия с БД][ServiceBdConnection]
     */
    private var bdBound = false

    /**
     * Объект для подключения к [сервису взаимодействия с БД][ServiceBdConnection]
     */
    private lateinit var bdServiceConn: ServiceConnection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_channel)
        ok = findViewById(R.id.btnCreateChannel)
        bdServiceConn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                bdService = (service as ServiceBdConnection.Binder).service
                bdBound = true
                ok.isEnabled = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                bdBound = false
            }
        }
        bindService(Intent(this, ServiceBdConnection::class.java), bdServiceConn, BIND_AUTO_CREATE)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        importanceLevel = findViewById(R.id.importance_level)
        importanceLevel.setSelection(1) // Установить выбранный по умолчанию на "По умолчанию"
        lightColor = findViewById(R.id.light_color)
        lightColor.adapter = SpinnerAdapter(
                this,
                R.layout.dropdown_item_text,
            arrayListOf(
                R.drawable.color_example_with_stroke_white,
                R.drawable.color_example_with_stroke_blue,
                R.drawable.color_example_with_stroke_red,
                R.drawable.color_example_with_stroke_green),
                0)
        groupId = findViewById(R.id.group_id)
        soundId = findViewById(R.id.sound_id)
        soundId.setSelection(1) // Установить выбранный по умолчанию на "По умолчанию"
        name = findViewById(R.id.channel_name)
        description = findViewById(R.id.channel_description)
        vibration = findViewById(R.id.vibration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createGroups()
        } else Toast.makeText(this, "У вас устаревшая версия ОС для корректной работы функционала каналов уведомлений! Необходима не ниже Oreo(26)!", Toast.LENGTH_SHORT).show()
    }

    /**
     * Функция создания групп каналов уведомлений. В данном примере их фиксированное количество - 4
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createGroups() {
        notificationGroups = ArrayList(resources.getStringArray(R.array.group_ids).asList())
        notificationGroups.removeAt(0)
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(notificationGroups[0], notificationGroups[0]))
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(notificationGroups[1], notificationGroups[1]))
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(notificationGroups[2], notificationGroups[2]))
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(notificationGroups[3], notificationGroups[3]))
    }

    /**
     * Функция обработки нажатия на кнопку создания канала
     *
     * @param view виджет кнопки
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun onClickCreate(view: View?) {
        if (name.text.isNotEmpty()) {
            // уровень важности создаваемого канала
            val l: Int
            // цвет светового сигнала создаваемого канала
            val sI: Int // код мелодии
            val gI: String // уникальный идентификатор группы каналов
            val iL: Int = when (importanceLevel.selectedItemPosition) {
                0 -> NotificationManager.IMPORTANCE_HIGH
                2 -> NotificationManager.IMPORTANCE_LOW
                3 -> NotificationManager.IMPORTANCE_MIN
                4 -> NotificationManager.IMPORTANCE_NONE
                else -> NotificationManager.IMPORTANCE_DEFAULT
            }
            val channel = NotificationChannel("My app notification channel " + notificationManager.notificationChannels.size,
                    name.text, iL)

            // Добавление описания каналу
            channel.description = description.text.toString()
            when (lightColor.selectedItemPosition) {
                1 -> {
                    l = Color.WHITE
                    channel.enableLights(true)
                    channel.lightColor = l
                }
                2 -> {
                    l = Color.BLUE
                    channel.enableLights(true)
                    channel.lightColor = l
                }
                3 -> {
                    l = Color.RED
                    channel.enableLights(true)
                    channel.lightColor = l
                }
                4 -> {
                    l = Color.GREEN
                    channel.enableLights(true)
                    channel.lightColor = l
                }
                else -> {
                    l = 13
                    channel.enableLights(false)
                }
            }
            // Добавление вибрации
            channel.enableVibration(vibration.isChecked)
            when (groupId.selectedItemPosition) {
                1 -> {
                    gI = notificationGroups[0]
                    channel.group = gI
                }
                2 -> {
                    gI = notificationGroups[1]
                    channel.group = gI
                }
                3 -> {
                    gI = notificationGroups[2]
                    channel.group = gI
                }
                4 -> {
                    gI = notificationGroups[3]
                    channel.group = gI
                }
                else -> gI = "null"
            }
            when (soundId.selectedItemPosition) {
                0 -> {
                    sI = 0
                    channel.setSound(null, null)
                }
                2 -> {
                    sI = 2
                    channel.setSound(Uri.parse("android.resource://" + packageName + "/" + R.raw.game_with_fire_cut),
                            AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .build())
                }
                3 -> {
                    sI = 3
                    channel.setSound(Uri.parse("android.resource://" + packageName + "/" + R.raw.song_of_the_pale_lady_cut),
                            AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .build())
                }
                else -> sI = 1
            }
            notificationManager.createNotificationChannel(channel)
            if (bdService.addChannel(notificationManager.notificationChannels.size - 1,
                            name.text.toString(),
                            iL,
                            description.text.toString(),
                            l,
                            if (vibration.isChecked) 1 else 0,
                            gI,
                            sI) != -1L) {
                Toast.makeText(this, "Канал успешно создан!", Toast.LENGTH_SHORT).show()
                finish()
            } else Toast.makeText(this, "Ошибка записи в БД!", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "Назовите как-нибудь канал! Пустое имя недопустимо!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!bdBound) return
        unbindService(bdServiceConn)
        bdBound = false
    }

    /**
     * Функция обработки нажатия на layout со [спиннером выбора цвета индикации][lightColor] и подсказкой.
     *
     * Открывает [спиннер выбора цвета индикации][lightColor]
     *
     * @param view виджет layout
     */
    fun click0(view: View?) {
        lightColor.performClick()
    }

    /**
     * Функция обработки нажатия на layout со [спиннером выбора группы уведомлений][groupId] и подсказкой.
     *
     * Открывает [спиннер выбора группы уведомлений][groupId]
     *
     * @param view виджет layout
     */
    fun click1(view: View?) {
        groupId.performClick()
    }

    /**
     * Функция обработки нажатия на layout со [спиннером выбора мелодии][soundId] и подсказкой.
     *
     * Открывает [спиннер выбора мелодии][soundId]
     *
     * @param view виджет layout
     */
    fun click2(view: View?) {
        soundId.performClick()
    }
}