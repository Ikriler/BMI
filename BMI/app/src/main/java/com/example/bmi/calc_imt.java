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

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class calc_imt extends AppCompatActivity {

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    Button btn;
    EditText mas;
    EditText rost;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_imt);
        getSupportActionBar().setTitle("Индекс массы тела (кг/м2)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        mas = findViewById(R.id.textEditMassa);
        rost = findViewById(R.id.textEditRost);
        btn = findViewById(R.id.buttonContinue);
        btn.setOnClickListener(view->{
            if(mas.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"вес\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            if(rost.getText().toString().trim().length() == 0){
                Toast.makeText(this, "Поле \"Рост\" не заполнено", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                float mas_prime = Float.parseFloat(mas.getText().toString().trim());
                float rost_prime = Float.parseFloat(rost.getText().toString().trim());
                float itog = mas_prime/(rost_prime*rost_prime)*10000;
                String date = String.valueOf(LocalDateTime.now());
                myEditor.putString("itog1", String.valueOf(itog) + ";\n" + date);
                myEditor.commit();
                Intent intent = new Intent(this, ForResult.class);
                String message_result = "";

                if(itog > 40) message_result = "третья степень ожирения";
                if(itog <= 40) message_result = "вторая степень ожирения";
                if(itog <= 35) message_result = "первая степень ожирения";
                if(itog <= 30) message_result = "избыточная масса тела";
                if(itog <= 25) message_result = "норма";
                if(itog <= 18) message_result = "наблюдается недостаток массы тела";

                intent.putExtra("result", String.valueOf(itog));
                intent.putExtra("message_result", message_result);
                intent.putExtra("message_desc", "- 16-18,5 кг/м2 и менее, то наблюдается недостаток массы тела, \n" +
                        "- 18,5-25 кг/м2 – норма, \n" +
                        "25-30 кг/м2 – избыточная масса тела, \n" +
                        "30-35 кг/м2 – первая степень ожирения, \n" +
                        "35-40 кг/м2 – вторая степень ожирения, \n" +
                        "свыше 40 кг/м2 – третья степень ожирения.\n" +
                        "- индекс не учитывает пол, возраст, распределение в организме жирового и мышечного компонента тела\n" +
                        "- средние значения индекса, полученные в РЭУ им. Г.В. Плеханова составили у девушек – 19-23 кг/м2, у юношей 20-23 кг/м2\n" +
                        "- у представителей молодого возраста (до 30 лет) индекс массы тела ниже по сравнению с представителями среднего возраста\n" +
                        "- для поддержания оптимальной массы тела необходимо ходить не менее 10 тысяч шагов в сутки\n" +
                        "- по индексу массы тела можно лишь косвенно судить о норме, недостатке или избыточной массе тела\n" +
                        "- при индексе свыше 25 кг/м2 необходимо подключить онлайн-калькулятор питания и контролировать полученные калории, увеличить прогулки на свежем воздухе, ограничить хлебобулочные изделия");
                intent.putExtra("number_next_intent", 1);
                startActivity(intent);
            }
            catch (Exception e){
                Toast.makeText(this, "Поля были неправильно заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
}