package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * HW 01
 * MainActivity.java
 * Devin Bonney and David Hotaran
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = "Tip Calculator";
    RadioGroup radioGroup;
    SeekBar seekBar;
    TextView customTipTextView;
    EditText billTotalEditText;
    TextView tipTotalTextView;
    TextView totalTextView;
    Button editButton;
    double tipValue = 10;
    double billValue;
    final double ROUND_PRECISION = 100.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.customTipRadioGroup);
        seekBar = findViewById(R.id.seekBar);
        customTipTextView = findViewById(R.id.customPercentValue);
        billTotalEditText = findViewById(R.id.billTotalEditView);
        tipTotalTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);
        editButton = findViewById(R.id.exitButton);

        // add event listener for exit button
        findViewById(R.id.exitButton).setOnClickListener(this);
        // add event listener and handler for EditText view
        billTotalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: ");
                try {
                    // convert the billTotalEditText to a string and parse it for a double
                    String billText = billTotalEditText.getText().toString();
                    // if the billText is currently empty Toast an appropriate message
                    if (billText.isEmpty()) {
                        // clear tip and total text views
                        tipTotalTextView.setText("");
                        totalTextView.setText("");
                        Toast.makeText(MainActivity.this, "Enter Bill Total", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // obtain double from bill text
                    billValue = Double.parseDouble(billText);
                    // if billValue is negative, throw
                    if (billValue < 0) {
                        throw new NumberFormatException("Negative bill value entered; only positive values accepted");
                    }

                    // obtain raw total and set tip and total text views to reflect user decisions
                    double rawTotalValue = (tipValue / 100) * billValue + billValue;
                    // use rounding precision for total
                    double roundedTotal = Math.round(rawTotalValue * ROUND_PRECISION) / 100.0;

                    tipTotalTextView.setText(String.valueOf(tipValue));
                    totalTextView.setText(String.valueOf(roundedTotal));
                    Log.d(TAG, "afterTextChanged: rawTotalValue: " + rawTotalValue +
                            "\nroundedTotalValue: " + roundedTotal);
                }
                catch (NumberFormatException e) {
                    Log.d(TAG, "afterTextChanged: NumberFormatException thrown: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Please enter valid, positive number/decimal format", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(TAG, "afterTextChanged: Exception thrown: " + e.getMessage());
                }
            }
        });

        // add event listener and handler for radioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: ");
                // handle different cases of checkedId to set respective tipValue
                if (checkedId == R.id.radioButton10Percent) {
                    tipValue = 10.0;
                } else if (checkedId == R.id.radioButton15Percent) {
                    tipValue = 15.0;
                } else if (checkedId == R.id.radioButton18Percent) {
                    tipValue = 18.0;
                } else if (checkedId == R.id.radioButtonCustomPercent) {
                    tipValue = seekBar.getProgress();
                }
                // set tip and total TextViews' text to reflect new tipValue if applicable
                if (!billTotalEditText.getText().toString().isEmpty()) {
                    // obtain raw total and set tip and total text views to reflect user decisions
                    double rawTotalValue = (tipValue / 100) * billValue + billValue;
                    // use rounding precision for total
                    double roundedTotal = Math.round(rawTotalValue * ROUND_PRECISION) / 100.0;

                    tipTotalTextView.setText(String.valueOf(tipValue));
                    totalTextView.setText(String.valueOf(roundedTotal));
                    Log.d(TAG, "onCheckedChanged: rawTotalValue: " + rawTotalValue +
                            "\nroundedTotalValue: " + roundedTotal);
                } else {
                    // if billTotalEditText empty, send Toast requesting input
                    Toast.makeText(MainActivity.this, "Enter Bill Total", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // add event listener and handler for seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: ");
                String customTipText = progress + "%";
                customTipTextView.setText(customTipText);

                // if custom radio button is currently checked, make following modifications
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioButtonCustomPercent) {
                    tipValue = progress;
                    // set tip and total TextViews' text to reflect new tipValue if applicable
                    if (!billTotalEditText.getText().toString().isEmpty()) {
                        // obtain raw total and set tip and total text views to reflect user decisions
                        double rawTotalValue = (tipValue / 100) * billValue + billValue;
                        // use rounding precision for total
                        double roundedTotal = Math.round(rawTotalValue * ROUND_PRECISION) / 100.0;

                        tipTotalTextView.setText(String.valueOf(tipValue));
                        totalTextView.setText(String.valueOf(roundedTotal));
                        Log.d(TAG, "onProgressChanged: rawTotalValue: " + rawTotalValue +
                                "\nroundedTotalValue: " + roundedTotal);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: ");
            }
        });

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
       finish();
    }
}