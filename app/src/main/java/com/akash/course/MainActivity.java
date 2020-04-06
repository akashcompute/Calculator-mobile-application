package com.akash.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    private TextView value, output;
    private int[] numericButtons = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button9, R.id.button0, R.id.button8};
    private int[] OperatorButtons = {R.id.buttonplus, R.id.buttondiv, R.id.buttonequal, R.id.buttonmin, R.id.buttonmul, R.id.buttonclear, R.id.buttondot};
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastDot;
    private Button back;
    private ImageButton feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = findViewById(R.id.value);
        output = findViewById(R.id.output);
        back = findViewById(R.id.buttonback);
        feedback = findViewById(R.id.feedback);

        numericOnClick();
        operatorOnClick();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = value.getText().toString();
                if (str.length() > 1) {
                    str = str.substring(0, str.length() - 1);
                    value.setText(str);
                } else if (str.length() <= 1) {
                    value.setText("");
                    value.setHint("0");
                }
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),formfilling.class);
                startActivity(intent);
            }
        });
    }

    private void numericOnClick() {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Button buttonclicked = (Button) view;
                if (stateError) {

                    value.setText(buttonclicked.getText());
                    stateError = false;
                } else {
                    value.append(buttonclicked.getText());
                }
                lastNumeric = true;
            }
        };

        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void operatorOnClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastNumeric && !stateError) {
                    Button button = (Button) view;
                    value.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;
                }
            }
        };

        for (int id : OperatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.buttondot).
                setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (lastNumeric && !stateError && !lastDot) {
                            value.append(".");
                            lastNumeric = false;
                            lastDot = true;
                        }
                    }
                });

        findViewById(R.id.buttonclear).
                setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        value.setText("");
                        lastNumeric = false;
                        stateError = false;
                        lastDot = false;
                    }
                });

        findViewById(R.id.buttonequal).

                setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if (lastNumeric && !stateError) {
                            String txt = value.getText().toString();
                            Expression expression = new ExpressionBuilder(txt).build();

                            try {
                                double result = expression.evaluate();
                                output.setText(Double.toString(result));
                                lastDot = true;
                            } catch (Exception ex) {
                                output.setText("Error");
                                stateError = true;
                                lastNumeric = false;
                            }
                        }
                    }
                });
    }
}