package com.example.bmi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDateTime;

public class IFUNIZ extends AppCompatActivity {

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    Button btn;
    EditText CSS;
    EditText DAD;
    EditText SAD;
    EditText MT;
    EditText ROST;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ifuniz);

        getSupportActionBar().setTitle("Индекс функциональных изменений");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();


        CSS = findViewById(R.id.textCSS);
        DAD = findViewById(R.id.textDAD);
        SAD = findViewById(R.id.textSAD);
        MT = findViewById(R.id.textMT);
        ROST = findViewById(R.id.textRost);
        btn = findViewById(R.id.buttonContinue);
        btn.setOnClickListener(view->{
            if(CSS.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"ЧСС\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(DAD.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"ДАД\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(SAD.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"САД\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(MT.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"Масса тела\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(ROST.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"Рост\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                float CSS_F = Float.parseFloat(CSS.getText().toString().trim());
                float DAD_F = Float.parseFloat(DAD.getText().toString().trim());
                float SAD_F = Float.parseFloat(DAD.getText().toString().trim());
                float MT_F = Float.parseFloat(DAD.getText().toString().trim());
                float ROST_F = Float.parseFloat(DAD.getText().toString().trim());
                float VOZ_F = Float.parseFloat(myPreferences.getString("Age","1"));
                float itog = (float) (0.011*CSS_F + 0.014* SAD_F + 0.008*DAD_F + 0.014*VOZ_F + 0.009*MT_F - 0.009 * ROST_F - 0.27);
                String date = String.valueOf(LocalDateTime.now());
                myEditor.putString("itog8", String.valueOf(itog) + ";\n" + date);
                myEditor.commit();
                Intent intent = new Intent(this, ForResult.class);
                String message_result = "";

                if(itog > 3.09) message_result = "сниженные";
                if(itog <= 3.09) message_result = "удовлетворительные";
                if(itog <= 2.6) message_result = "хорошие";


                intent.putExtra("result", String.valueOf(itog));
                intent.putExtra("message_result", message_result);
                intent.putExtra("message_desc", "Оценку индекса функциональных изменений (ИФИ) осуществляют по следующей шкале:\n" +
                        "ИФИ менее 2,6 — функциональные возможности системы кровообращения хорошие.\nИФИ, равный 2,6—3,09 — удовлетворительные функциональные возможности системы кровообращения с умеренным напряжением механизмов регуляции.\nИФИ более 3,09 — сниженные, недостаточные возможности системы кровообращения, наличие выраженных нарушений процессов адаптации.");

                intent.putExtra("number_next_intent", 8);
                startActivity(intent);
            }
            catch (Exception e){
                Toast.makeText(this, "Поля были неправильно заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
}