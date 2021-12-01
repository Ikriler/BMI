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
import java.util.Date;

public class LevelDA extends AppCompatActivity {

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    Button btn;
    EditText shagi;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_da);
        getSupportActionBar().setTitle("Уровень двигательный активности (число шагов в сутки)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        shagi = findViewById(R.id.textEditshagi);
        btn = findViewById(R.id.buttonContinue);
        btn.setOnClickListener(view->{
            if(shagi.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"количество шагов\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                float shagi_flaot = Float.parseFloat(shagi.getText().toString().trim());
                float itog = shagi_flaot/365;
                String date = String.valueOf(LocalDateTime.now());
                myEditor.putString("itog2", String.valueOf(itog) + ";\n" + date);
                myEditor.commit();
                Intent intent = new Intent(this, ForResult.class);
                String message_result = "";

                if(itog > 12500) message_result = "очень активный образ жизни";
                if(itog <= 12000) message_result = "активный образ жизни";
                if(itog <= 9999) message_result = "несколько активная работа";
                if(itog <= 5000) message_result = "сидячая работа";

                intent.putExtra("result", String.valueOf(itog));
                intent.putExtra("message_result", message_result);
                intent.putExtra("message_desc", "Методические рекомендации\n" +
                        "<5000 шагов в день – «сидячая работа»; \n" +
                        "7500-9999 шагов в день – «несколько активная работа»; \n" +
                        "10-12 тыс. шагов – «активный образ жизни»; \n" +
                        "свыше 12,5 тыс. шагов – «очень активный образ жизни»\n");
                intent.putExtra("number_next_intent", 2);
                startActivity(intent);
            }
            catch (Exception e){
                Toast.makeText(this, "Поля были неправильно заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
}