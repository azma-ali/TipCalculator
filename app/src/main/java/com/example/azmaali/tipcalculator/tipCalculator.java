package com.example.azmaali.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.NumberFormat;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.AdapterView;



public class tipCalculator extends AppCompatActivity {

    private EditText billAmountEditText;
    private TextView tipPercentTextView;
    private TextView totalAmountTextView;
    private TextView tipAmountTextView;
    private TextView perPersonAmountTextView;
    private SeekBar seekBar;
    private RadioGroup roundingRadioGroup;
    private Spinner splitBillSpinner;


    private float tipPercent = 0.15f;
    private float totalAmount = 0;
    private float tipAmount = 0;
    private int rounding;
    private float perPersonAmount;
    private int split;

    private final int ROUND_NONE = 0;
    private final int ROUND_TIP = 1;
    private final int ROUND_TOTAL = 2;
    private final int NO_SPLIT = 0;
    private final int TWO_WAY_SPLIT = 1;
    private final int THREE_WAY_SPLIT = 2;
    private final int FOUR_WAY_SPLIT = 3;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tip_calculator);
        billAmountEditText = findViewById(R.id.billAmountEditView);
        tipPercentTextView = findViewById(R.id.tipPercentTextView);
        totalAmountTextView =  findViewById(R.id.totalAmountTextView);
        tipAmountTextView = findViewById(R.id.tipAmountTextView);
        roundingRadioGroup = findViewById(R.id.roundingRadioGroup);
        splitBillSpinner = findViewById(R.id.splitBillSpinner);
        perPersonAmountTextView = findViewById(R.id.perPersonTextView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(85);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.splitbill_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        splitBillSpinner.setAdapter(adapter);




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 15;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress += progressChanged;
                tipPercentTextView.setText(String.valueOf(progress+"%"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                tipPercent = (float) (progress+progressChanged)/100;
                calculateTotals();

            }

        });

        billAmountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                calculateTotals();
                return false;
            }
        });

        roundingRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.noRoundingRadioButton){
                    calculateTotals();
                }
                else if (checkedId == R.id.tipRoundingRadioButton){
                    rounding = ROUND_TIP;
                    calculateTotals();
                }

                else if (checkedId == R.id.totalRoundingRadioButton){
                    rounding = ROUND_TOTAL;
                    calculateTotals();

                }
            }

        });

        splitBillSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                split = position + 1;
                calculateTotals();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void calculateTotals(){
        String billAmountStr = billAmountEditText.getText().toString();
        float billAmount;
        if(billAmountStr.equals("")){
            billAmount =  0;
        }
        else{
           billAmount = Float.parseFloat(billAmountStr);
        }

        //Calculate totals
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipAmount = tipPercent * billAmount;
        totalAmount = billAmount+ tipAmount;
        perPersonAmount = totalAmount/split;

        //Check rounding
        if (rounding == ROUND_TOTAL){
            totalAmount = Math.round(totalAmount);

        }
        else if (rounding == ROUND_TIP){
            tipAmount = Math.round(tipAmount);
        }


        // Display amounts
        if (split > 1 && !billAmountStr.isEmpty()){
            perPersonAmountTextView.setText(String.format("%.2f",perPersonAmount));
        }
        else {
            perPersonAmountTextView.setText("");
        }
        tipAmountTextView.setText(String.format("%.2f", tipAmount));
        totalAmountTextView.setText(String.format("%.2f", totalAmount));



    }



}

