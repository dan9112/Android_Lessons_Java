package ru.startandroid.p0201simple_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // константы для ID пунктов меню
    //final int MENU_ALPHA_ID = 1, MENU_SCALE_ID = 2, MENU_TRANSLATE_ID = 3, MENU_ROTATE_ID = 4, MENU_COMBO_ID = 5;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

        // регистрируем контекстное меню для компонента tv
        registerForContextMenu(tv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.tv) {
            getMenuInflater().inflate(R.menu.contextmenu0, menu);

                /*// добавляем пункты
                menu.add(0, MENU_ALPHA_ID, 0, "alpha");
                menu.add(0, MENU_SCALE_ID, 0, "scale");
                menu.add(0, MENU_TRANSLATE_ID, 0, "translate");
                menu.add(0, MENU_ROTATE_ID, 0, "rotate");
                menu.add(0, MENU_COMBO_ID, 0, "combo");*/
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation anim = null;
        // определяем какой пункт был нажат
        switch (item.getItemId()) {
            case R.id.al:
                anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;
            case R.id.sc:
                anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
                break;
            case R.id.tr:
                anim = AnimationUtils.loadAnimation(this, R.anim.mytrans);
                break;
            case R.id.ro:
                anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                break;
            case R.id.co:
                anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
                break;

            /*case MENU_ALPHA_ID:
                // создаем объект анимации из файла anim/myalpha
                anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;
            case MENU_SCALE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
                break;
            case MENU_TRANSLATE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.mytrans);
                break;
            case MENU_ROTATE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                break;
            case MENU_COMBO_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
                break;*/
        }
        // запускаем анимацию для компонента tv
        tv.startAnimation(anim);
        return super.onContextItemSelected(item);
    }
}
