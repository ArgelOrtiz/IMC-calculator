package com.tec.aoaimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title);

        initComponents();
    }

    protected void initComponents(){
        Button calculateButton                      = findViewById(R.id.calculateMainButton);
        final TextView iMCTextView                        = findViewById(R.id.iMCMainTextView);
        final TextInputEditText nameTextInputEditText     = findViewById(R.id.nameMainTextInputEditTExt);
        final TextInputEditText weightTextInputEditText   = findViewById(R.id.weightMainTextInputEditText);
        final TextInputEditText heightTextInputEditText   = findViewById(R.id.heightMainTextInputEditText);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iMCTextView.setVisibility(View.GONE);

                if (Objects.requireNonNull(nameTextInputEditText.getText()).toString().isEmpty()){
                    Snackbar.make(v,getString(R.string.err_name),Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (Objects.requireNonNull(weightTextInputEditText.getText()).toString().isEmpty()){
                    Snackbar.make(v,getString(R.string.err_weight),Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (Objects.requireNonNull(heightTextInputEditText.getText()).toString().isEmpty()){
                    Snackbar.make(v,getString(R.string.err_height),Snackbar.LENGTH_LONG).show();
                    return;
                }

                String iMC      = calculateIMC(weightTextInputEditText.getText().toString(), heightTextInputEditText.getText().toString());

                if (iMC.isEmpty()){
                    Snackbar.make(v,getString(R.string.err_imc),Snackbar.LENGTH_LONG).show();
                    return;
                }

                String label    = nameTextInputEditText.getText().toString() + getString(R.string.label) + iMC;
                iMCTextView.setText(label);
                iMCTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    protected String calculateIMC(String weightString, String heightString){

        try{
            final float WEIGHT  = Float.parseFloat(weightString);
            final float HEIGHT  = Float.parseFloat(heightString);
            final float IMC     = (WEIGHT/(HEIGHT*HEIGHT))*10000;

            if (IMC < 18)
                return IMC + " 'Peso bajo. Necesario valorar signos de desnutrición.'";
            else if (IMC >= 18 && IMC <=24.99)
                return IMC + " 'Normal.'";
            else if (IMC >= 25 && IMC <= 26.99)
                return IMC + " 'Sobrepeso.'";
            else if (IMC >= 27 && IMC <= 29.99)
                return IMC + " 'Obesidad grado I.'";
            else if (IMC >= 30 && IMC <= 39.99)
                return IMC + " 'Obesidad grado II.'";
            else if (IMC >= 40)
                return IMC + " 'Obesidad grado III.'";
            else
                return "";

        }catch (NumberFormatException e){
            Log.e("MainActivity",e.getMessage());
            return "";
        }


    }
}
