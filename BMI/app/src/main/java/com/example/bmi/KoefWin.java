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

public class KoefWin extends AppCompatActivity {

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    Button btn;
    EditText SAD;
    EditText DAD;
    EditText PD;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koef_win);
        getSupportActionBar().setTitle("Коэффициент выносливости (усл. ед.)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        SAD = findViewById(R.id.textSAD);
        DAD = findViewById(R.id.textDAD);
        PD = findViewById(R.id.textPD);
        btn = findViewById(R.id.buttonContinue);
        btn.setOnClickListener(view->{
            if(SAD.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"САД\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(DAD.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"ДАД\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(PD.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"ЧСС\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                float SAD_F = Float.parseFloat(SAD.getText().toString().trim());
                float DAD_F = Float.parseFloat(DAD.getText().toString().trim());
                float CSS_F = Float.parseFloat(PD.getText().toString().trim());
                float itog = (CSS_F*10)/(SAD_F-DAD_F);
                String date = String.valueOf(LocalDateTime.now());
                myEditor.putString("itog3", String.valueOf(itog) + ";\n" + date);
                myEditor.commit();
                Intent intent = new Intent(this, ForResult.class);
                String message_result = "";

                if(itog > 17) message_result = "увеличен";
                if(itog <= 16) message_result = "в норме";
                if(itog <= 14) message_result = "уменьшен";

                intent.putExtra("result", String.valueOf(itog));
                intent.putExtra("message_result", message_result);
                intent.putExtra("message_desc", "В норме коэффициент выносливости – 16 усл. ед., \n" +
                        "- уменьшение – на усиление кардиореспираторной системы. \n" +
                        "- увеличение указывает на ослабление деятельности сердечно-сосудистой системы или детренированности обследуемого\n" +
                        "- при занятиях спортом коэффициент выносливости может быть ниже 16 усл.ед., из-за укрепления сердечно-сосудистой системы, коэффициент выносливости коррелирует с уровнем физической работоспособности организма\n");

                intent.putExtra("number_next_intent", 3);
                startActivity(intent);
            }
            catch (Exception e){
                Toast.makeText(this, "Поля были неправильно заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
}