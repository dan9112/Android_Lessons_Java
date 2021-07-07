package lord.main.t1_handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.Arrays;

import lord.main.t1_handler.databinding.MainBinding;

public class MainActivity extends AppCompatActivity {

    final int
            CURRENT_RESULT = 0,
            FINAL_RESULT = 1;

    Button dev,
            one, two, three, multi,
            four, five, six, sub,
            seven, eight, nine,
            zero, point, equals, add;

    TextView display;

    Handler handler;

    boolean dr = false, zn = false;
    double currentResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBinding binding = DataBindingUtil.setContentView(this, R.layout.main);

        display = binding.display;
        dev = binding.button19;
        zero = binding.button;
        point = binding.button2;
        equals = binding.button3;
        add = binding.button4;
        seven = binding.button5;
        eight = binding.button6;
        nine = binding.button7;
        four = binding.button8;
        five = binding.button9;
        six = binding.button10;
        sub = binding.button11;
        one = binding.button12;
        two = binding.button13;
        three = binding.button14;
        multi = binding.button15;

        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case CURRENT_RESULT:
                        String[] substrings = display.getText().toString().split("\n");
                        ArrayList<ArrayList<String>> numbers = getNumbers(substrings[substrings.length - 1]);
                        ArrayList<Double> nums = new ArrayList<>();
                        ArrayList<Byte> notNums = new ArrayList<>();
                        for (String s : numbers.get(0)) nums.add(Double.parseDouble(s));
                        for (String s : numbers.get(1)) {
                            if (s.equals(multi.getText().toString())) notNums.add((byte) 0);
                            else if (s.equals(dev.getText().toString())) notNums.add((byte) 1);
                            else if (s.equals(add.getText().toString())) notNums.add((byte) 2);
                            else if (s.equals(sub.getText().toString())) notNums.add((byte) 3);
                        }

                        int i = 0;
                        int end = notNums.size();
                        while (i < end) {
                            if (notNums.get(i) == (byte) 0) {
                                nums.set(i, nums.get(i) * nums.get(i + 1));
                                nums.remove(i + 1);
                                notNums.remove(i);
                                end--;
                            } else if (notNums.get(i) == (byte) 1) {
                                if (nums.get(i + 1) == 0) {
                                    Toast.makeText(MainActivity.this, "Ошибка! Деление на ноль!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                nums.set(i, nums.get(i) / nums.get(i + 1));
                                nums.remove(i + 1);
                                notNums.remove(i);
                                end--;
                            }
                            i++;
                        }
                        i = 0;
                        end = notNums.size();
                        while (i < end) {
                            if (notNums.get(i) == (byte) 2) {
                                nums.set(i, nums.get(i) + nums.get(i + 1));
                                nums.remove(i + 1);
                                notNums.remove(i);
                                end--;
                            } else if (notNums.get(i) == (byte) 3) {
                                nums.set(i, nums.get(i) - nums.get(i + 1));
                                nums.remove(i + 1);
                                notNums.remove(i);
                                end--;
                            }
                            i++;
                        }
                        currentResult = nums.get(0);
                        Log.d("CurrentAnswer", String.valueOf(currentResult));
                        break;
                    case FINAL_RESULT:
                        display.setText(String.format("%s\n=%s", display.getText(), currentResult));
                        break;
                }
            }
        };
    }

    private ArrayList<ArrayList<String>> getNumbers(String s) {
        ArrayList<String> nums = new ArrayList<>();
        ArrayList<String> not_nums = new ArrayList<>();

        String current = s;
        if (s.charAt(0) == '=') current = s.substring(1);
        int a = indexFirstNotNumber(current.indexOf(multi.getText().charAt(0)),
                current.indexOf(sub.getText().charAt(0)),
                current.indexOf(add.getText().charAt(0)),
                current.indexOf(dev.getText().charAt(0)));

        while (a != -1) {
            nums.add(current.substring(0, a));
            not_nums.add(current.substring(a, a + 1));
            current = current.substring(a + 1);
            a = indexFirstNotNumber(current.indexOf(multi.getText().charAt(0)),
                    current.indexOf(dev.getText().charAt(0)),
                    current.indexOf(sub.getText().charAt(0)),
                    current.indexOf(add.getText().charAt(0)));
        }
        if (current.length() == 0) nums.add("0");
        else nums.add(current);
        return new ArrayList<>(Arrays.asList(nums, not_nums));
    }

    private int indexFirstNotNumber(int... indexes) {
        int min = -1;
        ArrayList<Integer> notNumbers = new ArrayList<>();
        for (int index : indexes) if (index != min) notNumbers.add(index);
        if (notNumbers.size() == 0) return min;
        if (notNumbers.size() == 1) return notNumbers.get(0);
        min = notNumbers.get(0);
        for (int i = 1; i < notNumbers.size(); i++) {
            min = Math.min(notNumbers.get(i), min);
        }
        return min;
    }

    public void onclick(View view) {
        if (view.getId() != R.id.button2 && view.getId() != R.id.button3 && view.getId() != R.id.button4 &&
                view.getId() != R.id.button11 && view.getId() != R.id.button15 && view.getId() != R.id.button16 &&
                view.getId() != R.id.button17 && view.getId() != R.id.button18 && view.getId() != R.id.button19) {
            display.setText(String.format("%s%s", display.getText(), ((Button) view).getText()));
            zn = false;
            handler.sendEmptyMessage(CURRENT_RESULT);
        } else if (view.getId() == R.id.button2) {
            if (!dr) {
                display.setText(String.format("%s%s", display.getText(), ((Button) view).getText()));
                dr = true;
            }
            handler.sendEmptyMessage(CURRENT_RESULT);
        } else if (view.getId() == R.id.button15 || view.getId() == R.id.button11 || view.getId() == R.id.button4 || view.getId() == R.id.button19) {
            if (!zn) {
                display.setText(String.format("%s%s", display.getText(), ((Button) view).getText()));
                dr = false;
                zn = true;
            }
            handler.sendEmptyMessage(CURRENT_RESULT);
        } else if (view.getId() == R.id.button3) {
            handler.sendEmptyMessage(FINAL_RESULT);
        }
    }
}