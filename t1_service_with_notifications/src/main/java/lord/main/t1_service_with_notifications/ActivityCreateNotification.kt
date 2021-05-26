package lord.main.t1_service_with_notifications

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import lord.main.t1_service_with_notifications.supporting_classes.SpinnerAdapter

/**
 * Активность создания уведомлений
 */
class ActivityCreateNotification : AppCompatActivity() {
    /**
     * Экземпляр [сервиса для взаимодействия с БД][ServiceBdConnection] приложения
     */
    private lateinit var bdRoomService: ServiceRoomBdConnection

    /**
     * Выпадающее меню выбора иконки для уведомления
     */
    private lateinit var icon: Spinner

    /**
     * Выпадающее меню выбора цвета иконки уведомления
     */
    private lateinit var iconColor: Spinner

    /**
     * Выпадающее меню выбора канала, в котором будет отправлено уведомление
     */
    private lateinit var channelId: Spinner

    /**
     * Чекбокс, являющийся также флагом автоматического сокрытия уведомления
     */
    private lateinit var autoCancel: CheckBox

    /**
     * Чекбокс, являющийся также флагом отображения времени создания уведомления
     */
    private lateinit var showWhen: CheckBox

    /**
     * Текстовое окно для ввода заголовка уведомления
     */
    private lateinit var title: EditText

    /**
     * Текстовое окно для ввода текста уведомления
     */
    private lateinit var text: EditText

    /**
     * Список уникальных идентификаторов всех каналов уведомлений из БД
     */
    private var channels = ArrayList<String>()

    /**
     * Флаг подключения к [сервису взаимодействия с БД][ServiceBdConnection]
     */
    private var bdRoomBound = false

    /**
     * Объект для подключения к [сервису взаимодействия с БД][ServiceBdConnection]
     */
    private lateinit var bdRoomServiceConnection: ServiceConnection

    /**
     * Функция заполнения таблицы
     */
    private fun fillTable() {
        val list = bdRoomService.allChannels()
        if (list == null) {
            Toast.makeText(this, "Ошибка чтения каналов уведомлений из БД!", Toast.LENGTH_SHORT)
                .show()
            finish()
        } else {
            channels = list[1]
            channelId.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list[0])
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_notification)
        channelId = findViewById(R.id.notify_channel_id)
        bdRoomServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                bdRoomService = (service as ServiceRoomBdConnection.Binder).service
                bdRoomBound = true
                fillTable()
            }

            override fun onServiceDisconnected(name: ComponentName) {
                bdRoomBound = false
            }
        }
        bindService(
            Intent(this, ServiceBdConnection::class.java),
            bdRoomServiceConnection,
            BIND_AUTO_CREATE
        )
        icon = findViewById(R.id.notify_icon)
        icon.adapter = SpinnerAdapter(
            this,
            R.layout.dropdown_item_text,
            arrayListOf(
                R.drawable.ic_baseball,
                R.drawable.ic_star,
                R.drawable.ic_umbrella,
                R.drawable.ic_info
            ),
            1
        )
        iconColor = findViewById(R.id.notify_icon_color)
        iconColor.adapter = SpinnerAdapter(
            this,
            R.layout.dropdown_item_text,
            arrayListOf(
                R.drawable.color_example_with_stroke_white,
                R.drawable.color_example_with_stroke_blue,
                R.drawable.color_example_with_stroke_red,
                R.drawable.color_example_with_stroke_green
            ),
            2
        )
        autoCancel = findViewById(R.id.notify_auto_cancel)
        showWhen = findViewById(R.id.notify_show_when)
        title = findViewById(R.id.notify_title)
        text = findViewById(R.id.notify_text)
    }

    /**
     * Функция обработки нажатия на кнопку создания уведомления.
     *
     * Создаёт уведомление с заданными параметрами и сохраняет их в БД
     *
     * @param view виджет кнопки
     */
    fun onClickCreate(view: View) {
        val iconId: Int = when (icon.selectedItemPosition) {
            0 -> R.drawable.ic_baseball
            1 -> R.drawable.ic_star
            2 -> R.drawable.ic_umbrella
            else -> R.drawable.ic_info
        }
        val color: Int = when (iconColor.selectedItemPosition) {
            1 -> Color.BLUE
            2 -> Color.RED
            3 -> Color.GREEN
            else -> Color.WHITE
        }
        val id = bdRoomService.registrNotify(
            channels[channelId.selectedItemPosition],
            title.text.toString(),
            text.text.toString(),
            iconId,
            color
        )
        val builder = NotificationCompat.Builder(this, channels[channelId.selectedItemPosition])
            .setContentTitle(title.text)
            .setContentText(text.text)
            .setSmallIcon(iconId)
            .setColor(color)
            .setAutoCancel(autoCancel.isChecked)
            .setShowWhen(showWhen.isChecked)
            .build()
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).notify(id.toInt(), builder)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!bdRoomBound) return
        unbindService(bdRoomServiceConnection)
        bdRoomBound = false
    }

    /**
     * Функция обработки нажатия на layout со [спиннером выбора иконки уведомления][icon] и подсказкой.
     *
     * Открывает [спиннер выбора иконки уведомления][icon]
     *
     * @param view виджет layout
     */
    fun click00(view: View) {
        icon.performClick()
    }

    /**
     * Функция обработки нажатия на layout со [спиннером выбора цвета иконки уведомления][iconColor] и подсказкой.
     *
     * Открывает [спиннер выбора цвета иконки уведомления][iconColor]
     *
     * @param view виджет layout
     */
    fun click01(view: View) {
        iconColor.performClick()
    }

    /**
     * Функция обработки нажатия на layout со [спиннером выбора канала уведомлений, по которому оно будет отправлено][channelId], и подсказкой.
     *
     * Открывает [спиннер выбора цвета канала уведомлений, по которому оно будет отправлено][channelId]
     *
     * @param view виджет layout
     */
    fun click02(view: View) {
        channelId.performClick()
    }
}