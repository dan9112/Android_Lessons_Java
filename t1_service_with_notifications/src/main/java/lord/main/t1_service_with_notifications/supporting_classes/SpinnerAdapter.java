package lord.main.t1_service_with_notifications.supporting_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lord.main.t1_service_with_notifications.R;

/**
 * Класс-адаптер для взаимодействия со всеми выпадающими списками приложения.
 * <p>
 * Стиль заполнения зависит кода, отправляемого при заполнении
 * </p>
 */
public class SpinnerAdapter extends ArrayAdapter<Integer> {
    /**
     * Контекс, в котором находится виджет выпадающего списка
     */
    Context context;
    /**
     * Список ссылок на ресурсы, которые необходимо отобразить в выпадающем списке
     */
    List<Integer> list;
    /**
     * Код, определяющий стиль заполнения выпадающего списка:
     * 0 - заполнения образцами цветов с добавлением в начало элемента с текстом,
     * 1 - заполнение картинками,
     * 2 - заполнение образцами цветов
     */
    int code;

    /**
     * Конструктор для создания экземпляра класса
     *
     * @param context  контекст, в котором находится виджет
     * @param resource уникальный идентификатор ресурса для разметки
     * @param objects  список ссылок из ресурсов на объекты списка (либо на значения их атрибутов)
     * @param code     код, определяющий стиль заполнения выпадающего списка:
     *                 0 - заполнения образцами цветов с добавлением в начало элемента с текстом,
     *                 1 - заполнение картинками,
     *                 2 - заполнение образцами цветов
     */
    public SpinnerAdapter(Context context, int resource, List<Integer> objects, int code) {
        super(context, resource, objects);
        list = objects;
        this.code = code;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (code == 0) return list.size() + 1;
        else return list.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    /**
     * Создание элемента, использующего пользовательский макет элемента
     *
     * @param position позиция элемента в виджете
     * @param parent   родительский виджет
     * @return готовый элемент выпадающего списка
     */
    public View getCustomView(int position, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row;
        switch (code) {
            case 0:
                row = inflater.inflate(R.layout.dropdown_item_text, parent, false);
                TextView tv = row.findViewById(R.id.tv);
                if (position == 0) {
                    tv.setText("Нет");
                } else {
                    tv.setBackground(context.getResources().getDrawable(list.get(position - 1)));
                }
                return row;
            case 1:
                row = inflater.inflate(R.layout.dropdown_item_image, parent, false);
                ImageView iv = row.findViewById(R.id.iv);
                iv.setImageDrawable(context.getResources().getDrawable(list.get(position)));
                return row;
            default:
                row = inflater.inflate(R.layout.dropdown_item_text, parent, false);
                ((TextView) row.findViewById(R.id.tv)).setBackground(context.getResources().getDrawable(list.get(position)));
                return row;
        }
    }
}
