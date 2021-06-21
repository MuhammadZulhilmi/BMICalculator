package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etH;
    EditText etW;
    Button btnCalc;
    TextView tvOutput;
    TextView tvOutputCat;
    TextView tvOutputBMI;
    TextView tvOutputHealth;

    SharedPreferences sharedPref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#0000FF");
        window.setStatusBarColor(colorCodeDark);

        etW = (EditText) findViewById(R.id.ETWeight) ;
        etH = (EditText) findViewById(R.id.ETHeight) ;
        btnCalc = (Button) findViewById(R.id.btnCalc);
        tvOutput = (TextView) findViewById(R.id.tvOutput);
        tvOutputCat = (TextView) findViewById(R.id.tvOutputCat);
        tvOutputBMI = (TextView) findViewById(R.id.tvOutputBMI);
        tvOutputHealth = (TextView) findViewById(R.id.tvOutputHealth);

        btnCalc.setOnClickListener(this);

        sharedPref = this.getSharedPreferences("bmi", Context.MODE_PRIVATE);

        float height = sharedPref.getFloat("height", R.id.ETHeight);
        etH.setText(""+ height);
        float weight = sharedPref.getFloat("weight", R.id.ETWeight);
        etW.setText(""+ weight);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnCalc:

                if (etW.getText().toString().equals("") || etH.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please input your weight and height.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else {

                    float weight = Float.parseFloat(etW.getText().toString());
                    float height = Float.parseFloat(etH.getText().toString());
                    String healthRisk = "";
                    String bmiCat = "";


                    float bmi = weight / ((height / 100) * (height / 100));

                    if (bmi <= 18.4) {
                        tvOutputHealth.setBackgroundColor(Color.parseColor("#FFFF00"));
                        bmiCat = "Underweight";
                        healthRisk = "Malnutrition risk";
                    } else if (bmi <= 24.9) {
                        tvOutputHealth.setBackgroundColor(Color.parseColor("#00FF00"));
                        bmiCat = "Normal weight";
                        healthRisk = "Low risk";
                    } else if (bmi <= 29.9) {
                        tvOutputHealth.setBackgroundColor(Color.parseColor("#FFA500"));
                        bmiCat = "Overweight";
                        healthRisk = "Enchanced risk";
                    } else if (bmi <= 34.9) {
                        tvOutputHealth.setBackgroundColor(Color.parseColor("#C34A2C"));
                        bmiCat = "Moderately obese";
                        healthRisk = "Medium risk";
                    } else if (bmi <= 39.9) {
                        tvOutputHealth.setBackgroundColor(Color.parseColor("#FF0000"));
                        bmiCat = "Severely obese";
                        healthRisk = "High risk";
                    } else {
                        tvOutputHealth.setBackgroundColor(Color.parseColor("#800000"));
                        bmiCat = "Very severely obese";
                        healthRisk = "Very high risk";
                    }

                    tvOutputCat.setText(" " + bmiCat);
                    tvOutputBMI.setText(String.format("%.2f", bmi) + " kg/m²");
                    tvOutputHealth.setText(healthRisk);

                    //tvOutput.setText("\nBMI Category : " + bmiCat + "\n\n" + "BMI Value : " + String.format("%.2f", bmi) + " kg/m²" + "\n\n" + "Health Risk : " + healthRisk);

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putFloat("height", height);
                    editor.putFloat("weight", weight);
                    editor.apply();
                }


            break;

        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:

                Toast.makeText(this, "Home Page",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.about:

                Toast.makeText(this, "About Page",Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}