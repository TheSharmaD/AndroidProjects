package com.example.tipsplitcalculator;
/**
 * An app that calculates total dining cost with a selected tip added
 * For user to split that total evenly for each person dining, specifying the amount owed by each person.
 * @author Diksha Sharma
 */

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Inputs
    /**
     * The original bill total input is a positive whole or decimal number (no negative values allowed).
    */
    private EditText totalBillWithTax;
    /**
     * The desired tip percentage is chosen via 4 radio buttons, with the values 12%, 15%, 18% and 20%.
     */
    private RadioGroup RGroup;
    /**
     * The number of people to split the bill is a positive whole number (no zero, negative or decimal values).
     */
    private EditText totalNumberPeople;

    //Outputs
    /**
     * These values should all be displayed out to 2 decimal places preceded by a dollar sign
     */
    private TextView totalTipAmount;
    private TextView totalWithTip;
    /**
     * These values should be displayed out to 2 decimal places preceded by a dollar sign
     */
    private TextView totalPerPerson;

    //Validations
    /**
     * If the bill total field is empty, selecting a tip percentage should do nothing (and the selected tip
     * percentage radio button should then be automatically un-checked)
     */
    //NOTE: the calculated amount per person should always be rounded UP to the nearest cent. For
    //example, a calculated amount per person of 28.394 should be rounded UP to 28.40; a
    //calculated amount per person of 15.999 should be rounded UP to 16.00. If you do not do this,
    //the amount per person multiplied by the number of people can be less than the total with tip.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding the Variables to the screen Widget
        totalBillWithTax = findViewById(R.id.edText_totalBill);
        RGroup = findViewById(R.id.RGroup);
        totalTipAmount = findViewById(R.id.tView_totalTipAmount);
        totalWithTip = findViewById(R.id.tView_totalWithTip);
        totalNumberPeople = findViewById(R.id.edText_totalPeople);
        totalPerPerson = findViewById(R.id.tView_totalPerPerson);
        Log.d("MyApp", "Variables Initialised");
        // Restore state variables from the savedInstanceState bundle
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Save state variables to the savedInstanceState bundle
        outState.putString("Total Tip Amount Output:", totalTipAmount.getText().toString());
        outState.putString("Total WIth Tip Amount Output:", totalWithTip.getText().toString());
        outState.putString("Total Number of People Output:", totalNumberPeople.getText().toString());
        outState.putString("Total Per Person Amount Output:", totalPerPerson.getText().toString());
        Log.d("MyApp", "Saved Variables");
        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        // Restore state variables to the OnRestoreInstanceState bundle
        totalTipAmount.setText(savedInstanceState.getString("Total Tip Amount Output:"));
        totalWithTip.setText(savedInstanceState.getString("Total WIth Tip Amount Output:"));
        totalNumberPeople.setText(savedInstanceState.getString("Total Number of People Output:"));
        totalPerPerson.setText(savedInstanceState.getString("Total Per Person Amount Output:"));
        Log.d("MyApp", "Restored Variables");
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);
    }

    // Method to calculate the Tip Amount and Total Bill Amount with Tip
    public void TipCalculator(View v) {

        double tipAmount = 0.0;
        double totalAmountWithTip;
        String bill = totalBillWithTax.getText().toString();
        //Log.d(TAG, "TipCalculator: "+bill);

        //validation
        if (bill.matches("")) {
            Toast.makeText(this, "Incorrect!!", Toast.LENGTH_SHORT).show();
            RGroup.clearCheck();
            totalTipAmount.setText("");
            totalWithTip.setText("");
            totalNumberPeople.setText("");
            totalPerPerson.setText("");
            return;
        }
        double input = Double.valueOf(bill);
        // Calculating Tip Percentage/ Tip amount
        if(v.getId() == R.id.twelvePercent_RB) {
            tipAmount = 0.12 * input;
        }
        else if(v.getId() == R.id.fifteenPercent_RB) {
            tipAmount = 0.15 * input;
        }
        else if(v.getId() == R.id.eighteenPercent_RB) {
            tipAmount = 0.18 * input;
        }
        else if(v.getId() == R.id.twentyPercent_RB) {
            tipAmount = 0.20 * input;
        }
        String formattedTipDouble = String.format("%.2f", tipAmount);
        totalTipAmount.setText("$"+formattedTipDouble);

        totalAmountWithTip = Double.valueOf(tipAmount + input);
        String formattedTotalAmountDouble = String.format("%.2f", totalAmountWithTip);
        totalWithTip.setText("$" + formattedTotalAmountDouble);
        Log.d("MyApp", "Tip Calculated");
       }

    //Tapping the GO button will calculate the Total per Person value.
    public void goBtn(View v) {
        String bill = totalBillWithTax.getText().toString();
        String Amount= totalWithTip.getText().toString();
        String people= totalNumberPeople.getText().toString();
        if (bill.isEmpty() || bill.equals("0")) {
            Toast.makeText(this, "Incorrect Bill!!", Toast.LENGTH_SHORT).show();
            RGroup.clearCheck();
            totalTipAmount.setText("");
            totalWithTip.setText("");
            totalNumberPeople.setText("");
            totalPerPerson.setText("");
            totalBillWithTax.setText("");
            Log.d("MyApp", "GoBtn Worked!!");
            return;
        }

        if(people.isEmpty() || people.equals("0")) {
            Toast.makeText(this, "Incorrect Number of People!!", Toast.LENGTH_SHORT).show();
            RGroup.clearCheck();
            totalTipAmount.setText("");
            totalWithTip.setText("");
            totalNumberPeople.setText("");
            totalPerPerson.setText("");
            totalBillWithTax.setText("");
            Log.d("MyApp", "Clear Btn Worked!!");
            return;
        }
        Amount = Amount.replace("$", "");
        double AmountDouble = Double.parseDouble(Amount);
        double PeopleDouble = Double.parseDouble(people);
        double result = AmountDouble / PeopleDouble;
        double roundedDouble = Math.ceil(result * 100) / 100;
        String formattedDouble = String.format("%.2f", roundedDouble);
        totalPerPerson.setText("$"+ formattedDouble);
    }

    //Tapping the CLEAR button will clear all text fields and radio buttons
    public void clearAllFields(View v) {
        totalBillWithTax.setText("");
        RGroup.clearCheck();
        totalTipAmount.setText("");
        totalWithTip.setText("");
        totalNumberPeople.setText("");
        totalPerPerson.setText("");
    }
}