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

public class kerdo extends AppCompatActivity {

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    Button btn;
    EditText CSS;
    EditText DAD;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerdo);

        getSupportActionBar().setTitle("Вегетативный индекс Кердо (усл. ед),");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();


        CSS = findViewById(R.id.textCSS);
        DAD = findViewById(R.id.textDAD);
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
            try{
                float CSS_F = Float.parseFloat(CSS.getText().toString().trim());
                float DAD_F = Float.parseFloat(DAD.getText().toString().trim());
                float itog = 100 - (1 - (DAD_F/CSS_F));
                String date = String.valueOf(LocalDateTime.now());
                myEditor.putString("itog7", String.valueOf(itog) + ";\n" + date);
                myEditor.commit();
                Intent intent = new Intent(this, ForResult.class);
                String message_result = "";

                if(itog > 31) message_result = "выраженная симпатикотония";
                if(itog <= 30) message_result = "симпатикотония";
                if(itog <= 15) message_result = "уравновешенность симпатических и парасимпатических влияний";
                if(itog <= -15) message_result = "парасимпатикотония.";
                if(itog <= 30) message_result = "выраженная парасимпатикотония.";

                intent.putExtra("result", String.valueOf(itog));
                intent.putExtra("message_result", message_result);
                intent.putExtra("message_desc", "Индекс Кердо в норме равен 0 усл. ед., что демонстрирует оптимальный уровень вегетативной регуляции сердечно-сосудистой системы, \n" +
                        "- при преобладании симпатического тонуса отмечается увеличение (табл. 1), - при преобладании парасимпатического тонуса отмечается снижение индекса.\n");

                intent.putExtra("number_next_intent", 7);
                startActivity(intent);
            }
            catch (Exception e){
                Toast.makeText(this, "Поля были неправильно заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
}