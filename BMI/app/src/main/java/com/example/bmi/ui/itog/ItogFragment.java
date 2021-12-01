package com.example.bmi.ui.itog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bmi.R;
import com.example.bmi.databinding.FragmentHomeBinding;
import com.example.bmi.databinding.FragmentItogBinding;

import java.util.ArrayList;
import java.util.Date;

public class ItogFragment extends Fragment {

    private ItogViewModel itogViewModel;
    private FragmentItogBinding binding;
    public SharedPreferences myPreferences;
    public SharedPreferences.Editor myEditor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itogViewModel =
                new ViewModelProvider(this).get(ItogViewModel.class);

        binding = FragmentItogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        myEditor = myPreferences.edit();

        ListView lw = (ListView) binding.listitog;

        String defaultValue = "значение не обнаружено;";

        ArrayList<String> desc = new ArrayList<String>();
        desc.add("Индекс массы тела (кг/м2)");
        desc.add("Уровень двигательной активности(число шагов в сутки)");
        desc.add("Коэффициент выносливости(усл. ед.)");
        desc.add("Уровень регуляции сердечно-сосудистой системы");
        desc.add("Жизненный индекс (мл/кг)");
        desc.add("Циркулярно-респираторный коэффициент Скибински(усл.ед)");
        desc.add("Вегетативный индекс Кердо (усл. ед)");
        desc.add("Индекс функциональных изменений");

        ArrayList<String> norm = new ArrayList<String>();
        norm.add("Норма: 18,5 -25;");
        norm.add("Норма: 10-12 тыс. шагов;");
        norm.add("Норма: 16 усл.ед.;");
        norm.add("81-90 средний уровень, 91-100 – ниже среднего;");
        norm.add("Норма: 50-61");
        norm.add("30-60 – хорошо, 10-30  - удовлетворительно;");
        norm.add("В норме равен 0 усл. ед.;");
        norm.add("Менее 2,6 — функциональные возможности системы кровообращения хорошие, 2,6—3,09 — удовлетворительные возможности;");

        ArrayList<String> results = new ArrayList<String>();
        results.add(myPreferences.getString("itog1", defaultValue));
        results.add(myPreferences.getString("itog2", defaultValue));
        results.add(myPreferences.getString("itog3", defaultValue));
        results.add(myPreferences.getString("itog4", defaultValue));
        results.add(myPreferences.getString("itog5", defaultValue));
        results.add(myPreferences.getString("itog6", defaultValue));
        results.add(myPreferences.getString("itog7", defaultValue));
        results.add(myPreferences.getString("itog8", defaultValue));


        itogViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ArrayList<String> itogs = new ArrayList<String>();
                for(int i = 0; i < 8; i++){
                    itogs.add(desc.get(i) + "\n" + norm.get(i) + "\n" + "Результат: " + results.get(i) + " GMT+3");
                }

                ArrayAdapter adapterCalculators = new ArrayAdapter(getActivity().getApplicationContext(),R.layout.textview1,  itogs);

                lw.setAdapter(adapterCalculators);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}