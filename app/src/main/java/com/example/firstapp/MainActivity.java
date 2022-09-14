package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.firstapp.ui.chordadder.AddChord;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;




    private Button button;
    private Switch modeSwitch;
    private Boolean playmode;
    public static int buttonId;
    private Button[] buttons = new Button[8];
    private int ids = 0;
    private SensorManager sensorManager;
    private Sensor accmeter;
    private boolean notFirstTime = false;
    private boolean isAccSenAvailable;
    private float cX, cY, cZ, lX, lY, lZ, xDiff, yDiff, zDiff;
    private float shakeTrashHold = 5f;
    private Vibrator vib;
    private boolean holding = false;
    private ActivityResultLauncher<Intent> chordNameResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == 200) {

                        button = (Button) findViewById(buttonId);

                        Intent intent = result.getData();
                        String data = "";

                        if (intent != null) {
                            data = intent.getStringExtra("result");
                        }


                        button.setText(data);
                        button.setBackgroundColor(getResources().getColor(R.color.added_chord));

                    }
                }
            }
    );




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.tools);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_chords, R.id.nav_strum, R.id.nav_diagrams)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setItemTextColor(getResources().getColorStateList(R.color.white));



        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        for (int i = 0; i < 8; i++) {
            ids = getResources().getIdentifier("button" + i, "id", getPackageName());
            buttons[i] = (Button) findViewById(ids);
        }


        for (int i = 0; i < 8; i++) {
            buttons[i].setOnTouchListener((view, event) -> {
                System.out.println("hozzadava");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("ittvan");
                        return true;
                    case MotionEvent.ACTION_UP:
                        System.out.println("most itt");
                        Intent intent = new Intent(this, AddChord.class);
                        buttonId = view.getId();
                        chordNameResult.launch(intent);
                        return true;
                }
                return false;
            });

        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accmeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccSenAvailable = true;
            System.out.println(" available");
        } else {
            System.out.println(" not available");
            isAccSenAvailable = false;

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("itt vagyok hello"+ item.getItemId());
        switch (item.getItemId()) {
            case R.id.nav_chords:
                for (int i = 0; i < 8; i++) {
                    ids = getResources().getIdentifier("button" + i, "id", getPackageName());
                    buttons[i] = (Button) findViewById(ids);
                }
                return true;
            case R.id.nav_strum:
                System.out.println("strum");
                return true;
            case R.id.nav_diagrams:
                System.out.println("diag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();


    }



    public void playmodeToggle(View v) {



        modeSwitch = (Switch) findViewById(R.id.switch_mode);

        System.out.println("valami");
        playmode = modeSwitch.isChecked();
        System.out.println(playmode);

        if (playmode) {
            System.out.println("ide belép");
            for (int i = 0; i < 8; i++) {

                    buttons[i].setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            button = (Button) findViewById(view.getId());
                            switch (motionEvent.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    System.out.println("lent tart");
                                    button.setBackgroundColor(getResources().getColor(R.color.add_chord_background));
                                    holding = true;

                                    return true;
                                case MotionEvent.ACTION_UP:
                                    System.out.println("kipufog");
                                    button.setBackgroundColor(getResources().getColor(R.color.added_chord));

                                    holding = false;
                                    return true;
                            }
                            return false;
                        }
                    });

            }
        } else {
            System.out.println("most ide lép be");
            for (int i = 0; i < 8; i++) {

                System.out.println("a ciklus megy");
                buttons[i].setOnTouchListener((view, event) -> {
                    System.out.println("hozzadava");
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            System.out.println("ittvan");
                            return true;
                        case MotionEvent.ACTION_UP:
                            System.out.println("most itt");
                            Intent intent = new Intent(this, AddChord.class);
                            buttonId = view.getId();
                            chordNameResult.launch(intent);
                            return true;
                    }
                    return false;
                });
            }
        }


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        cX = sensorEvent.values[0];
        cY = sensorEvent.values[1];
        cZ = sensorEvent.values[2];
        if (notFirstTime) {
            xDiff = Math.abs(lX - cX);
            yDiff = Math.abs(lY - cY);
            zDiff = Math.abs(lZ - cZ);
            if (((xDiff > shakeTrashHold && yDiff > shakeTrashHold)
                    || (xDiff > shakeTrashHold && zDiff > shakeTrashHold)
                    || (yDiff > shakeTrashHold && zDiff > shakeTrashHold)) && holding) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vib.vibrate(VibrationEffect.createOneShot(100,VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vib.vibrate(500);
                }
            }

            lX = cX;
            lY = cY;
            lZ = cZ;

        }
        notFirstTime = true;


    }

    @Override
    public void onAccuracyChanged (Sensor sensor,int i){

    }
    @Override
    protected void onResume () {
        super.onResume();

        if (isAccSenAvailable)
            sensorManager.registerListener(this, accmeter, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause () {
        super.onPause();

        if (isAccSenAvailable)
            sensorManager.unregisterListener(this);

    }
}