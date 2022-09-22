package com.example.firstapp.ui.diagrampac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.firstapp.MainActivity;
import com.example.firstapp.R;


public class ChordDiagramActivity extends Activity {
    private TextView t0;
    private TextView t1;
    private TextView t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chord_diagram_activity);

        Intent intent = getIntent();

        String name = intent.getStringExtra(MainActivity.EXTRA_MESSAGE0);
        String strings = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        String fingers = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);

        t0 = findViewById(R.id.textView0);
        t1 = findViewById(R.id.textView1);
        t2 = findViewById(R.id.textView2);

        t0.setText(name);
        t1.setText(strings);
        t2.setText(fingers);

    }
}
