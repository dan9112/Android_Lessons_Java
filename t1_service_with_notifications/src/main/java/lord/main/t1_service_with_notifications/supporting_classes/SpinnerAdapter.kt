package lord.main.t1_service_with_notifications.supporting_classes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import lord.main.t1_service_with_notifications.R

/**
 * Класс-адаптер для взаимодействия со всеми выпадающими списками приложения.
 *
 * @param context  контекст, в котором находится виджет
 * @param resource уникальный идентификатор ресурса для разметки
 * @param list     список ссылок из ресурсов на объекты списка (либо на значения их атрибутов)
 * @param code     код, определяющий стиль заполнения выпадающего списка:
 *                 0 - заполнения образцами цветов с добавлением в начало элемента с текстом,
 *                 1 - заполнение картинками,
 *                 2 - заполнение образцами цветов
 */
class SpinnerAdapter(context: Context, resource: Int,
                     private var list: ArrayList<Int>,
                     private val code: Int) : ArrayAdapter<Int>(context, resource, list) {
    override fun getCount(): Int {
        return if (code == 0) list.size + 1 else list.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    /**
     * Создание элемента, использующего пользовательский макет элемента
     *
     * @param position позиция элемента в виджете
     * @param parent   родительский виджет
     * @return готовый элемент выпадающего списка
     */
    private fun getCustomView(position: Int, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return when (code) {
            0 -> {
                val row = inflater.inflate(R.layout.dropdown_item_text, parent, false)
                val tv = row.findViewById<TextView>(R.id.tv)
                if (position == 0) {
                    tv.text = "Нет"
                } else {
                    tv.background = ResourcesCompat.getDrawable(context.resources, list[position - 1], null)
                }
                row
            }
            1 -> {
                val row = inflater.inflate(R.layout.dropdown_item_image, parent, false)
                val iv = row.findViewById<ImageView>(R.id.iv)
                iv.setImageDrawable(ResourcesCompat.getDrawable(context.resources, list[position], null))
                row
            }
            else -> {
                val row = inflater.inflate(R.layout.dropdown_item_text, parent, false)
                (row.findViewById<View>(R.id.tv) as TextView).background = ResourcesCompat.getDrawable(context.resources, list[position], null)
                row
            }
        }
    }
}