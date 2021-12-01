package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

public class ForResult extends AppCompatActivity {

    TextView txt1;
    TextView txt2;
    TextView txt3;
    Button btn;
    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_result);

        getSupportActionBar().setTitle("Результат");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        txt1 = findViewById(R.id.textView1);
        txt2 = findViewById(R.id.textView2);
        txt3 = findViewById(R.id.textView3);

        btn = findViewById(R.id.btnNext);

        Intent intent = getIntent();
        txt1.setText("Ваш результат: \n" + intent.getStringExtra("result"));
        txt2.setText("Описание: \n" + intent.getStringExtra("message_result"));
        txt3.setText(intent.getStringExtra("message_desc"));


        btn.setOnClickListener(view->{
            Intent intent1 = new Intent();
            if(myPreferences.getBoolean("turner", false)){
                switch (intent.getIntExtra("number_next_intent", -1)){
                    case -1:
                        intent1 = new Intent(this, MainActivity.class);
                        break;
                    case 0:
                        intent1 = new Intent(this, calc_imt.class);
                        break;
                    case 1:
                        intent1 = new Intent(this, LevelDA.class);
                        break;
                    case 2:
                        intent1 = new Intent(this, KoefWin.class);
                        break;
                    case 3:
                        intent1 = new Intent(this, Serdce.class);
                        break;
                    case 4:
                        intent1 = new Intent(this, LifeIndex.class);
                        break;
                    case 5:
                        intent1 = new Intent(this, SKI.class);
                        break;
                    case 6:
                        intent1 = new Intent(this, kerdo.class);
                        break;
                    case 7:
                        intent1 = new Intent(this, IFUNIZ.class);
                        break;
                    case 8:
                        intent1 = new Intent(this, MainActivity.class);
                        break;
                }
            }
            else{
                intent1 = new Intent(this, MainActivity.class);
            }
            startActivity(intent1);
        });

    }
}