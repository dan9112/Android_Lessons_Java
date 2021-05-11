package lord.main.p0571_gridview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    GridView gvMain;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        adapter = new ArrayAdapter<>(this, R.layout.item, R.id.tvText, data);
        gvMain = findViewById(R.id.gvMain);
        gvMain.setAdapter(adapter);
        adjustGridView();
    }

    private void adjustGridView() {
        gvMain.setNumColumns(GridView.AUTO_FIT);// Количество столбцов
        gvMain.setColumnWidth(80);// ширина столбцов
        gvMain.setVerticalSpacing(5);// вертикальный отступ между ячейками
        gvMain.setHorizontalSpacing(5);// горизонтальный отступ между ячейками
        gvMain.setStretchMode(GridView.NO_STRETCH);// мод использования свободного пространства
    }
}