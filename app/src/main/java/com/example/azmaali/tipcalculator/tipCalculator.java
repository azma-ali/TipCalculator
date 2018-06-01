package com.example.azmaali.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class tipCalculator extends AppCompatActivity {

    private float tipPercent = 0.15f;
    private float totalAmount = 0;
    private float tipAmount = 0;
    private EditText billAmountEditText;
    private TextView tipPercentTextView;
    private TextView totalAmountTextView;
    private TextView tipAmountTextView;
    private Button percentUpButton;
    private Button percentDownButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        billAmountEditText = findViewById(R.id.billAmountEditView);
        tipPercentTextView = findViewById(R.id.tipPercentTextView);
        totalAmountTextView =  findViewById(R.id.totalAmountTextView);
        tipAmountTextView = findViewById(R.id.tipAmountTextView);

        percentUpButton = (Button) findViewById(R.id.percentUpButton);
        percentDownButton = (Button) findViewById(R.id.percentDownButton);




        percentDownButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tipPercent > .14) {
                    tipPercent = tipPercent - 0.01f;
                    calculateTotals();
                }
            }
        });


        percentUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tipPercent < 1) {
                    tipPercent = tipPercent + 0.01f;
                    calculateTotals();
                }
            }
        });


        billAmountEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                calculateTotals();
                return false;
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

        tipAmount = tipPercent * billAmount;
        totalAmount = billAmount+ tipAmount;
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipAmountTextView.setText(currency.format(tipAmount));
        totalAmountTextView.setText(currency.format(totalAmount));
        NumberFormat percent = NumberFormat.getPercentInstance();
        tipPercentTextView.setText(percent.format(tipPercent));

    }

}

