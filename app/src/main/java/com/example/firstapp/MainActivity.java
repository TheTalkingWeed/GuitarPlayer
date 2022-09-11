package com.example.firstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Switch;

import com.example.firstapp.ui.chordadder.AddChord;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    Button button;
    private int buttonId;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> chordNameResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 200){
                        switch (buttonId){
                            case R.id.button1 :
                                button = (Button) findViewById(R.id.button1);
                                break;
                            case R.id.button2 :
                                button = (Button) findViewById(R.id.button2);
                                break;
                            case R.id.button3 :
                                button = (Button) findViewById(R.id.button3);
                                break;
                            case R.id.button4 :
                                button = (Button) findViewById(R.id.button4);
                                break;
                            case R.id.button5 :
                                button = (Button) findViewById(R.id.button5);
                                break;
                            case R.id.button6 :
                                button = (Button) findViewById(R.id.button6);
                                break;
                            case R.id.button7 :
                                button = (Button) findViewById(R.id.button7);
                                break;
                            case R.id.button8 :
                                button = (Button) findViewById(R.id.button8);
                                break;

                        }

                        Intent intent = result.getData();
                        String data = "";

                         if (intent != null){
                             data = intent.getStringExtra("result");
                         }

                         button.setText(data);

                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_chords, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void handleClick(View v){
        Switch modeSwitch = (Switch) findViewById(R.id.switch_mode);

        Boolean play = modeSwitch.isChecked();

        if(!play){
            Intent intent = new Intent(MainActivity.this, AddChord.class);
            buttonId = v.getId();
            chordNameResult.launch(intent);
        }else{
            Button b = (Button)v;
            String buttonText = b.getText().toString();

            System.out.println(buttonText);
        }



    }










}



