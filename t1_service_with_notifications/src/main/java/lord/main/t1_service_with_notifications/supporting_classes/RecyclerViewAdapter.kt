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
import lord.main.t1_service_with_notifications.db.DbManager
import java.util.*

/**
 * Клас-адаптер для заполнения [виджета списка][RecyclerView] [элементами][RecyclerViewItem]
 */
class RecyclerViewAdapter(
    context: Context,
    /**
     * Объект для управления БД
     */
    var manager: DbManager
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    /**
     * Список [элементов][RecyclerViewItem], который отображается в [виджете списка][RecyclerView]
     */
    var notifies: ArrayList<RecyclerViewItem>

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
         * @param item [элемент списка][RecyclerViewItem] с данными, которые необходимо продемонстрировать пользователю
         */
        fun setData(item: RecyclerViewItem, position: Int) {
            icon.setImageResource(item.iconId)
            icon.setColorFilter(item.iconColor, PorterDuff.Mode.MULTIPLY) //SRC_IN
            id.text = item.id.toString()
            channelId.text = item.channelId
            title.text = item.title
            text.text = item.text
            itemView.setOnClickListener { v: View? ->
                val adb = AlertDialog.Builder(context)
                adb.setMessage("Вы уверены, что хотите удалить строку из истории?")
                adb.setCancelable(true)
                adb.setPositiveButton("Да") { dialog: DialogInterface, which: Int ->
                    manager.open()
                    manager.removeHistoryRow(item.id)
                    manager.close()
                    notifies.removeAt(position)
                    notifyItemRemoved(position)
                }
                adb.setNegativeButton("Нет", null)
                adb.show()
            }
        }

    }

    init {
        manager.open()
        notifies = manager.allHistory!!
        manager.close()
        this.context = context
    }
}