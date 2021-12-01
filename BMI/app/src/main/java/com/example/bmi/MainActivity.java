package com.example.bmi;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bmi.ui.index.IndexFragment;
import com.example.bmi.ui.index.IndexViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bmi.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public NavController navController;
    public SharedPreferences myPreferences;
    public SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_index, R.id.navigation_itog).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        myEditor.putBoolean("turner", false);

        if(myPreferences.getBoolean("FirstStart", false)){
            navController.navigate(R.id.navigation_home);
            myEditor.putBoolean("FirstStart", false);
            myEditor.commit();
        }
        else {
            navController.navigate(R.id.navigation_index);
        }

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(findViewById(R.id.navigation_home).isSelected()){

                navView.setVisibility(View.INVISIBLE);
                getSupportActionBar().hide();
            }
            else {
                navView.setVisibility(View.VISIBLE);
                getSupportActionBar().show();
            }
        });
    }

    public void GoIndex(View view){
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_index);
    }
}