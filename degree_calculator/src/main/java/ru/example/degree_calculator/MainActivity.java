package ru.example.degree_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    EditText textIn;
    float inputFloat;
    TextView textOut;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textIn = findViewById(R.id.textIn);
        textOut = findViewById(R.id.textOut);
    }

    //функция сокрытия клавиатуры оля textIn
    private void hideSoftKeyboard(){
        Log.d(TAG, "Функция сокрытия клавиатуры оля textIn");
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textIn.getWindowToken(), 0);
        }
    }
    public void onClickStart(View v){
        hideSoftKeyboard();
        switch (v.getId()){
            case R.id.button5:
                //Кнопка Count
                Log.d(TAG, "Кнопка Count");
                if (textIn.getText().toString().length() == 0) textIn.setText("0");
                textOut.setText("");
                inputFloat = Float.parseFloat(textIn.getText().toString());//число на входе
                for (int i=1; i<=10; i++) {
                    String s;
                    double p = Math.pow(inputFloat, i);//Возведение в степень

                    //Округление до 2 знака
                    BigDecimal result = new BigDecimal(p);
                    result = result.setScale(2, RoundingMode.HALF_UP);

                    if (i<10) s = textOut.getText().toString() + result + "\n";
                    else s = textOut.getText().toString() + result;
                    textOut.setText(s);
                }
                break;

            case R.id.button6:
                //кнопка Exit
                Log.d(TAG, "Кнопка Exit");
                System.exit(0);
                break;
        }
    }

    public void but(View view) {
    }
}
