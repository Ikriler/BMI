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

public class Serdce extends AppCompatActivity {

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    Button btn;
    EditText SAD;
    EditText PD;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serdce);

        getSupportActionBar().setTitle("Уровень регуляции сердечно-сосудистой системы (усл. ед.)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        SAD = findViewById(R.id.textSAD);
        PD = findViewById(R.id.textPD);
        btn = findViewById(R.id.buttonContinue);
        btn.setOnClickListener(view->{
            if(SAD.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"САД\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(PD.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"ЧСС\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                float SAD_F = Float.parseFloat(SAD.getText().toString().trim());
                float CSS_F = Float.parseFloat(PD.getText().toString().trim());
                float itog = (SAD_F*CSS_F)/100;
                String date = String.valueOf(LocalDateTime.now());
                myEditor.putString("itog4", String.valueOf(itog) + ";\n" + date);
                myEditor.commit();
                Intent intent = new Intent(this, ForResult.class);
                String message_result = "";

                if(itog > 101) message_result = "низкое значение регуляции";
                if(itog <= 100) message_result = "ниже среднего";
                if(itog <= 90) message_result = "средний";
                if(itog <= 80) message_result = "выше среднего";
                if(itog <= 74) message_result = "высокий уровень регуляции сердечно-сосудистой системы";

                intent.putExtra("result", String.valueOf(itog));
                intent.putExtra("message_result", message_result);
                intent.putExtra("message_desc", "Методические рекомендации\n" +
                        "до 74 усл. ед. – высокий уровень регуляции сердечно-сосудистой системы; \n" +
                        "75-80 – выше среднего; \n" +
                        "81-90 – средний; \n" +
                        "91-100 – ниже среднего; \n" +
                        "101 и выше – низкое значение регуляции. \n" +
                        "- показатели регуляции сердечно-сосудистой системы у спортсменов ниже, чем у нетренированных лиц, так как сердце спортсмена в условиях покоя работает в более экономичном режиме, при меньшем потреблении кислорода.\n" +
                        "индекс используется для косвенного определения степени обеспеченности миокарда кислородом\n");

                intent.putExtra("number_next_intent", 4);
                startActivity(intent);
            }
            catch (Exception e){
                Toast.makeText(this, "Поля были неправильно заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
}