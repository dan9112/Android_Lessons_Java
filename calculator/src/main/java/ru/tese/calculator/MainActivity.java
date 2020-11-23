package ru.tese.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView stack;
    byte counter = 0;
    TextView display;
    double d1;
    byte operation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        stack = (TextView)findViewById(R.id.stack);
        display = (TextView)findViewById(R.id.display);
    }

    void StackInput ()
    {
        String s;
        if (operation == 1) {
        s = stack.getText().toString() + d1 + " + ";
        }
        else if (operation == 2) {
            s = stack.getText().toString() + d1 + " - ";
        }
        else if (operation == 3) {
            s = stack.getText().toString() + d1 + " x ";
        }
        else if (operation == 4) {
            s = stack.getText().toString() + d1 + " / ";
        }
        else if ((operation == 5)||(operation == 9)) {
            s = stack.getText().toString() + d1 + "\n";
            if (counter<13) counter++;
        }
        else {
            s = stack.getText().toString() + "\n";
        }
        if (counter == 13) {
         String[] ss = s.split("\n");
         ss[0] = "";
         s = "";
         for (String si : ss) s += si;
        }
        stack.setText(s);
    }

    boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public void but(View v) {
        String s;
        if (operation == 9){
            operation = 0;
            stack.setText("");
        }
        switch (v.getId()) {
            case R.id.cl0: {
                display.setText("");
                break;
            }
            case R.id.cl: {
                display.setText("");
                break;
            }
            case R.id.one: {
                s = display.getText().toString() + "1";
                display.setText(s);
                break;
            }
            case R.id.two: {
                s = display.getText().toString() + "2";
                display.setText(s);
                break;
            }
            case R.id.three: {
                s = display.getText().toString() + "3";
                display.setText(s);
                break;
            }
            case R.id.four: {
                s = display.getText().toString() + "4";
                display.setText(s);
                break;
            }
            case R.id.five: {
                s = display.getText().toString() + "5";
                display.setText(s);
                break;
            }
            case R.id.six: {
                s = display.getText().toString() + "6";
                display.setText(s);
                break;
            }
            case R.id.seven: {
                s = display.getText().toString() + "7";
                display.setText(s);
                break;
            }
            case R.id.eight: {
                s = display.getText().toString() + "8";
                display.setText(s);
                break;
            }
            case R.id.nine: {
                s = display.getText().toString() + "9";
                display.setText(s);
                break;
            }
            case R.id.zero: {
                if ((display.getText().toString().length() > 1) || (!display.getText().toString().equals("0"))) {
                    s = display.getText().toString() + "0";
                    display.setText(s);
                }
                break;
            }
            case R.id.po: {
                if (display.getText().toString().length() > 0) {
                    s = display.getText().toString() + ".";
                } else {
                    s = "0.";
                }
                display.setText(s);
                break;
            }
            case R.id.div: {
                if (display.getText().toString().length() > 0)
                    d1 = Double.parseDouble(display.getText().toString());
                else d1 = 0;
                display.setText("");
                operation = 4;
                StackInput();
                break;
            }
            case R.id.add: {
                if (display.getText().toString().length() > 0)
                    d1 = Double.parseDouble(display.getText().toString());
                else d1 = 0;
                display.setText("");
                operation = 1;
                StackInput();
                break;
            }
            case R.id.sub: {
                if (display.getText().toString().length() > 0)
                    d1 = Double.parseDouble(display.getText().toString());
                else d1 = 0;
                display.setText("");
                operation = 2;
                StackInput();
                break;
            }
            case R.id.mul: {
                if (display.getText().toString().length() > 0)
                    d1 = Double.parseDouble(display.getText().toString());
                else d1 = 0;
                display.setText("");
                operation = 3;
                StackInput();
                break;
            }
            case R.id.eq: {
                //второе число в стек
                if (operation != 0) s = stack.getText().toString() + display.getText().toString() + " = ";
                else s = stack.getText().toString() + display.getText().toString();
                stack.setText(s);

                if (operation == 1) {
                    s = display.getText().toString();
                        d1 += Double.parseDouble(s);
                        display.setText(Double.toString(d1));
                        operation = 5;
                } else if (operation == 2) {
                    s = display.getText().toString();
                        d1 -= Double.parseDouble(s);
                        display.setText(Double.toString(d1));
                        operation = 5;
                } else if (operation == 3) {
                    s = display.getText().toString();
                        d1 *= Double.parseDouble(s);
                        display.setText(Double.toString(d1));
                        operation = 5;
                } else if (operation == 4) {
                    s = display.getText().toString();
                    if (Double.parseDouble(s) == 0) {
                        display.setText("Error! Dividing by zero!");
                        operation = 9;
                    }
                    else {
                        d1 /= Double.parseDouble(s);
                        display.setText(Double.toString(d1));
                        operation = 5;
                    }
                }
                StackInput();
                break;
            }
        }
    }
}