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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.firstapp.ui.apiconnection.ApiConn;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
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

    private SensorManager sensorManager;
    private Sensor accmeter;
    private Vibrator vib;

    private Button[] buttons = new Button[8];
    private Button button;
    private Switch modeSwitch;

    private String chordToPlay = "";

    private int buttonId;
    private int ids = 0;

    private float cX, cY, cZ, lX, lY, lZ, xDiff, yDiff, zDiff;
    private float shakeTrashHold = 5f;

    private boolean notFirstTime = false;
    private boolean pressed = false;
    private boolean isAccSenAvailable;
    private boolean playmode;


    private OkHttpClient client = new OkHttpClient();

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
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accmeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccSenAvailable = true;
            System.out.println(" available");
        } else {
            System.out.println(" not available");
            isAccSenAvailable = false;

        }
        modeSwitch = (Switch) findViewById(R.id.switch_mode);
        //most vagyok elsőször valstat előadáson
        Spinner chordName=findViewById(R.id.spinner_chordname);
        Spinner chordType=findViewById(R.id.spinner_chordtype);
        Spinner chordSounding=findViewById(R.id.spinner_chordsouding);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.chords, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        chordName.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this, R.array.chordtype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        chordType.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this, R.array.chordsouinding, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        chordSounding.setAdapter(adapter2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

//    ((xDiff > shakeTrashHold && yDiff > shakeTrashHold)
//            || (xDiff > shakeTrashHold && zDiff > shakeTrashHold)
//            || (yDiff > shakeTrashHold && zDiff > shakeTrashHold))
        playmode = modeSwitch.isChecked();
        cX = sensorEvent.values[0];
        cY = sensorEvent.values[1];
        cZ = sensorEvent.values[2];
        if (notFirstTime) {
            xDiff = Math.abs(lX - cX);
            yDiff = Math.abs(lY - cY);
            zDiff = Math.abs(lZ - cZ);
            if ( (yDiff > shakeTrashHold || xDiff > shakeTrashHold ) && playmode && pressed && !(chordToPlay.equals("Add chord"))) {
                System.out.println(chordToPlay);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vib.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void playmodeToggle(View v) {
        modeSwitch = (Switch) findViewById(R.id.switch_mode);
        playmode = modeSwitch.isChecked();
        pressed = false;
    }

    public void handleAddClick(View v){
        playmode = modeSwitch.isChecked();
        if (!playmode) {
            Intent intent = new Intent(this, AddChord.class);
            buttonId = v.getId();
            chordNameResult.launch(intent);
        }else{
            chordToPlay = ((Button) findViewById(v.getId())).getText().toString();
            pressed = true;
        }



    }

    public void getChordClick(View v) throws IOException{
        String url = "https://api.uberchord.com/v1/chords/";

        System.out.println(run(url));

    }


    private String data = "";
    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    data = response.body().string();
                }
            }
        });

        return data;
    }
}