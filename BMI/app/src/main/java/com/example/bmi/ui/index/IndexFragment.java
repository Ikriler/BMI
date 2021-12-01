package com.example.bmi.ui.index;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bmi.IFUNIZ;
import com.example.bmi.KoefWin;
import com.example.bmi.LevelDA;
import com.example.bmi.LifeIndex;
import com.example.bmi.R;
import com.example.bmi.SKI;
import com.example.bmi.Serdce;
import com.example.bmi.calc_imt;
import com.example.bmi.databinding.FragmentIndexBinding;
import com.example.bmi.kerdo;

import java.util.ArrayList;

public class IndexFragment extends Fragment {

    private IndexViewModel itogViewModel;
    private FragmentIndexBinding binding;
    public AppCompatEditText textEdit;
    public SharedPreferences myPreferences;
    public SharedPreferences.Editor myEditor;
    public Spinner spinner;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        itogViewModel = new ViewModelProvider(this).get(IndexViewModel.class);

        binding = FragmentIndexBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_index, container, false);

        ListView lw = (ListView) binding.listCalculators;
        spinner = (Spinner) binding.spinner;
        textEdit = (AppCompatEditText)binding.editTextNumberDecimal;

        ArrayList<String> calculators = new ArrayList<String>();
        calculators.add("Индекс массы тела (кг/м2)");
        calculators.add("Уровень двигательной активности(число шагов в сутки)");
        calculators.add("Коэффициент выносливости(усл. ед.)");
        calculators.add("Уровень регуляции сердечно-сосудистой системы");
        calculators.add("Жизненный индекс (мл/кг)");
        calculators.add("Циркулярно-респираторный коэффициент Скибински(усл.ед)");
        calculators.add("Вегетативный индекс Кердо (усл. ед)");
        calculators.add("Индекс функциональных изменений");

        ArrayAdapter adapterCalculators = new ArrayAdapter(getActivity().getApplicationContext(),R.layout.textview1,  calculators);
        ArrayList<String> gender = new ArrayList<String>();
        gender.add("Мужской");
        gender.add("Женский");
        ArrayAdapter adapterGender = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spin_items_1,  gender);

        lw.setAdapter(adapterCalculators);
        spinner.setAdapter(adapterGender);

        lw.setAdapter(adapterCalculators);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoCalc(id);
            }
        });

        myPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        myEditor = myPreferences.edit();

        textEdit.setText(myPreferences.getString("Age",""));
        spinner.setSelection((int)myPreferences.getLong("Gender",0));

        Button btn = binding.btnStart;
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myEditor.putBoolean("turner", true);
                myEditor.commit();
                Intent intent = new Intent(getActivity().getApplicationContext(), calc_imt.class);
                startActivity(intent);
            }
        });

        itogViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void GoCalc(long id){
        if(textEdit.getText().toString().trim().length() == 0){
            Toast.makeText(getActivity().getApplicationContext(), "Необходимо указать возраст.", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            myEditor.putLong("Gender", spinner.getSelectedItemId());
            myEditor.putString("Age", textEdit.getText().toString().trim());
            myEditor.commit();
        }
        myEditor.putBoolean("turner", false);
        myEditor.commit();
        Intent intent = new Intent();
        switch ((int)id){
            case 0:
                intent = new Intent(getActivity().getApplicationContext(), calc_imt.class);
                break;
            case 1:
                intent = new Intent(getActivity().getApplicationContext(), LevelDA.class);
                break;
            case 2:
                intent = new Intent(getActivity().getApplicationContext(), KoefWin.class);
                break;
            case 3:
                intent = new Intent(getActivity().getApplicationContext(), Serdce.class);
                break;
            case 4:
                intent = new Intent(getActivity().getApplicationContext(), LifeIndex.class);
                break;
            case 5:
                intent = new Intent(getActivity().getApplicationContext(), SKI.class);
                break;
            case 6:
                intent = new Intent(getActivity().getApplicationContext(), kerdo.class);
                break;
            case 7:
                intent = new Intent(getActivity().getApplicationContext(), IFUNIZ.class);
                break;
        }
        startActivity(intent);
    }
}