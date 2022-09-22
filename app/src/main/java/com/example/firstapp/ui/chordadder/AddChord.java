package com.example.firstapp.ui.chordadder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.firstapp.R;


public class AddChord extends Activity {

    public static String buttonname;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_chord);

        Spinner chords=findViewById(R.id.spinner_chords);
        Spinner chordtype=findViewById(R.id.spinner_chordtype);
    }


    public void handleCancel(View v){
        this.finish();
    }

    public void handleAddChord(View v){
        Spinner chords=findViewById(R.id.spinner_chords);
        Spinner chordtype=findViewById(R.id.spinner_chordtype);

        String chord = chords.getSelectedItem().toString();
        String type = chordtype.getSelectedItem().toString();



        buttonname = chord + " " + type.toLowerCase();

        Intent intent = new Intent();
        intent.putExtra("result", buttonname);
        setResult(200,intent);

        finish();
    }

    public void handlePlayChord(View view){
        Spinner chords=findViewById(R.id.spinner_chords);
        Spinner chordtype=findViewById(R.id.spinner_chordtype);

        String chord = chords.getSelectedItem().toString();
        String type = chordtype.getSelectedItem().toString();







    }








}
