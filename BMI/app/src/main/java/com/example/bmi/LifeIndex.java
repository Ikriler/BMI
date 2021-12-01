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

public class LifeIndex extends AppCompatActivity {

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    Button btn;
    EditText Massa;
    EditText JELM;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_index);

        getSupportActionBar().setTitle("Жизненный индекс (мл/кг)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        Massa = findViewById(R.id.textMassa);
        JELM = findViewById(R.id.textJELM);
        btn = findViewById(R.id.buttonContinue);
        btn.setOnClickListener(view->{
            if(JELM.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"ёмкость лёгких\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Massa.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"масса\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                float JELM_F = Float.parseFloat(JELM.getText().toString().trim());
                float Massa_F = Float.parseFloat(Massa.getText().toString().trim());
                float itog = JELM_F/Massa_F;
                String date = String.valueOf(LocalDateTime.now());
                myEditor.putString("itog5", String.valueOf(itog) + ";\n" + date);
                myEditor.commit();
                Intent intent = new Intent(this, ForResult.class);
                String message_result = "";

                if(itog > 61) message_result = "выше нормы";
                if(itog <= 61) message_result = "нормы";
                if(itog <= 50) message_result = "ниже нормы";

                intent.putExtra("result", String.valueOf(itog));
                intent.putExtra("message_result", message_result);
                intent.putExtra("message_desc", "- норма 50-61 мл/кг; если показатель меньше, то это может свидетельствовать о недостаточности кислород обеспечения организма, недостаточной жизненной емкости легких, либо избыточной массе тела \n" +
                        "- для обследуемых, имеющих жизненный индекс ниже нормы рекомендуются физические упражнения средней интенсивности, наряду с этим допускается чередование высокоинтенсивных (молодой и средний возраст) и малоинтенсивных тренировочных нагрузок.\n" +
                        "- на увеличение жизненной емкости легких влияют регулярные занятия физической культурой и спортом, особенно занятия аэробной направленности (бег, велопрогулки, плавание, аэробика, работа на кардиотренажерах, спортивные игры и другие).\n");

                intent.putExtra("number_next_intent", 5);
                startActivity(intent);
            }
            catch (Exception e){
                Toast.makeText(this, "Поля были неправильно заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
}