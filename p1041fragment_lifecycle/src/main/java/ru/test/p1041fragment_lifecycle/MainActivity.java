package ru.test.p1041fragment_lifecycle;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    final String LOG_TAG = "myLogs";
    Button button;
    LinearLayout frag2;
    TextView text2;

    int butcounter = 1;

    private int i = 0;
    private final int[] colors = new int[]{Color.BLACK, Color.RED};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        frag2 = findViewById(R.id.frag2);

        text2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //frag2.setBackground(getResources().getColor(R.color.black));
                text2.setTextColor(Color.WHITE);
                frag2.setBackgroundColor(colors[i]);
                text2.setText("Кнопка нажата " + butcounter + " раз");
                Log.d(LOG_TAG, "Кнопка нажата " + butcounter + " раз");
                butcounter++;
                i++;
                if (i > colors.length - 1) i = 0;
            }
        });

        Log.d(LOG_TAG, "MainActivity onCreate");
    }

    //public void onClick(View view) {
    //
    //}

    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "MainActivity onStart");
    }

    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "MainActivity onResume");
    }

    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "MainActivity onPause");
    }

    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "MainActivity onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MainActivity onDestroy");
    }
}