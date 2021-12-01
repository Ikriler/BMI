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

public class SKI extends AppCompatActivity {

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    Button btn;
    EditText JELM;
    EditText PSH;
    EditText CSS;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ski);

        getSupportActionBar().setTitle("Циркулярно-респираторный коэффициент Скибински");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        JELM = findViewById(R.id.textJELM);
        PSH = findViewById(R.id.textPSH);
        CSS = findViewById(R.id.textCSS);
        btn = findViewById(R.id.buttonContinue);
        btn.setOnClickListener(view->{
            if(JELM.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"ёмкость лёгких\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(PSH.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"проба Штанге\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(CSS.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"частота сердечных сокращений\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                float JELM_F = Float.parseFloat(JELM.getText().toString().trim());
                float PSH_F = Float.parseFloat(PSH.getText().toString().trim());
                float CSS_F = Float.parseFloat(CSS.getText().toString().trim());
                float itog = (JELM_F/100)*PSH_F/CSS_F;
                String date = String.valueOf(LocalDateTime.now());
                myEditor.putString("itog6", String.valueOf(itog) + ";\n" + date);
                myEditor.commit();
                Intent intent = new Intent(this, ForResult.class);
                String message_result = "";

                if(itog > 60) message_result = "неудовлетворительно";
                if(itog <= 60) message_result = "удовлетворительно";
                if(itog <= 30) message_result = "хорошо";
                if(itog <= 10) message_result = "очень хорошо (высокий уровень выносливости).";
                if(itog <= 5) message_result = "очень плохо.";

                intent.putExtra("result", String.valueOf(itog));
                intent.putExtra("message_result", message_result);
                intent.putExtra("message_desc", "<5 усл. ед. – очень плохо (низкий уровень выносливость сердечно-сосудистой и дыхательной систем), \n" +
                        "5-10 – неудовлетворительно, \n" +
                        "10-30 – удовлетворительно, \n" +
                        "30-60 – хорошо,\n" +
                        " > 60 – очень хорошо (высокий уровень выносливости). \n" +
                        "- проба Штанге (с), определяющая гипоксическую устойчивость организма (время задержки дыхания на вдохе). \n" +
                        "- индекс Скибински характеризует состояние сердечно-сосудистой и дыхательной систем.\n");

                intent.putExtra("number_next_intent", 6);
                startActivity(intent);
            }
            catch (Exception e){
                Toast.makeText(this, "Поля были неправильно заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
}