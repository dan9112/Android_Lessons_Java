package lord.main.p0591_datepickerdialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int DIALOG_DATE = 1,
            myYear = 2021,
            myMonth = 4,// нумерация от "0"
            myDay = 11;

    TextView tvDate;

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myYear = year;
            myMonth = month;
            myDay = dayOfMonth;
            tvDate.setText(getString(R.string.today_date, myDay, myMonth + 1, myYear));// нумерация месяцев от "0"
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tvDate = findViewById(R.id.tvDate);
    }

    public void onclick(View view) {
        showDialog(DIALOG_DATE);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return dpd;
        }
        return super.onCreateDialog(id);
    }
}