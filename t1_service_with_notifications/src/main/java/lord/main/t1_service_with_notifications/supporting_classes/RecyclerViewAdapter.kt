package lord.main.t1_service_with_notifications.supporting_classes

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lord.main.t1_service_with_notifications.R
import lord.main.t1_service_with_notifications.ServiceRoomBdConnection
import lord.main.t1_service_with_notifications.room_db.TableHistory
import java.util.*

/**
 * Клас-адаптер для заполнения [виджета списка][RecyclerView] [записями][TableHistory]
 */
class RecyclerViewAdapter(
    context: Context,
    /**
     * Объект для управления БД
     */
    var service: ServiceRoomBdConnection
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    /**
     * Список [записей][TableHistory], который отображается в [виджете списка][RecyclerView]
     */
    var notifies: ArrayList<TableHistory>

    /**
     * Контекст, в котором создан [виджет списка][RecyclerView]
     */
    var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notify_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(notifies[position], position)
    }

    override fun getItemCount(): Int {
        return notifies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var icon: ImageView = itemView.findViewById(R.id.imageView)
        private var id: TextView = itemView.findViewById(R.id.not_id)
        private var channelId: TextView = itemView.findViewById(R.id.not_channel_id)
        private var title: TextView = itemView.findViewById(R.id.not_title)
        private var text: TextView = itemView.findViewById(R.id.not_text)

        /**
         * Функция заполнения элемента [виджета списка][RecyclerView]
         *
         * @param item [запись][TableHistory] с данными, которые необходимо продемонстрировать пользователю
         */
        fun setData(item: TableHistory, position: Int) {
            icon.setImageResource(item.iconRes)
            icon.setColorFilter(item.iconColorRes, PorterDuff.Mode.MULTIPLY) //SRC_IN
            id.text = item.id.toString()
            channelId.text = item.channelId
            title.text = item.title
            text.text = item.text
            itemView.setOnClickListener { v: View? ->
                val adb = AlertDialog.Builder(context)
                adb.setMessage("Вы уверены, что хотите удалить строку из истории?")
                adb.setCancelable(true)
                adb.setPositiveButton("Да") { dialog: DialogInterface, which: Int ->
                    service.unregistrNotify(item.id)
                    notifies.removeAt(position)
                    notifyItemRemoved(position)
                }
                adb.setNegativeButton("Нет", null)
                adb.show()
            }
        }

    }

    init {
        notifies = service.getHistory()
        this.context = context
    }
}