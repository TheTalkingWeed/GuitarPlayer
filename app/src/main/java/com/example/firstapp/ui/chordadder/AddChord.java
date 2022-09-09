package com.example.firstapp.ui.chordadder;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.R;


public class AddChord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_chord);

        Spinner chords=findViewById(R.id.spinner_chords);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.chords, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        chords.setAdapter(adapter);
    }



}
