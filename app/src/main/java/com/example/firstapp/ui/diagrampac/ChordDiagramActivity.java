package com.example.firstapp.ui.diagrampac;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.MainActivity;
import com.example.firstapp.R;


public class ChordDiagramActivity extends AppCompatActivity  {

    private String name;
    private String strings;
    private String fingers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.chord_diagram_activity);
        setContentView(new MyView(this));



        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.EXTRA_MESSAGE0);
        strings = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        fingers = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);






    }

    public class MyView extends View{
        Paint paint = null;
        public MyView(Context context){
            super(context);
            paint = new Paint();

        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int stringGap = (x-(x/8)*2)/6;
            int ladGap = (y-(y/8)*2)/5;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#3A506B"));
            canvas.drawPaint(paint);
            paint.setColor(Color.BLACK);

            canvas.drawRect(x/10,y/8,x-x/10 + 20,y-y/8,paint);

            int ladLeft = x/10;
            int ladTop = y/8 + ladGap;
            int ladRight = x-x/10 + 20;
            int ladBottom = ladTop + 10;
            paint.setColor(Color.GRAY);
            for (int i = 0; i < 4; i++) {
                canvas.drawRect(ladLeft,ladTop,ladRight,ladBottom,paint);
                ladTop += ladGap;
                ladBottom += ladGap;

            }
            //ChordFromApi{name='A', strings='X02220', fingers='XX234X'}

            int stringLeft = x/8 + stringGap/2;
            int stringTop = y/8;
            int stringRight = x/8 + stringGap/2 + 20;
            int stringBottom = y-y/8;
            paint.setColor(Color.WHITE);
            for (int i = 0; i < 6; i++) {
                if (strings.charAt(i) == 'X') {
                    paint.setColor(Color.RED);
                    canvas.drawRect(stringLeft,stringTop,stringRight,stringBottom,paint);
                    paint.setColor(Color.WHITE);

                } else{
                    canvas.drawRect(stringLeft,stringTop,stringRight,stringBottom,paint);
                }

                stringLeft +=stringGap;
                stringRight +=stringGap;

            }
            paint.setColor(Color.BLACK);
            paint.setTextSize(46);
            int dotPos = y/8 + ladGap;
            for (int i = 0; i < 5; i++) {
                canvas.drawText(Integer.toString(i+1),x / 20,dotPos-ladGap / 2,paint);
                if (i == 2 || i == 4){
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(x/2+10,dotPos-ladGap / 2,30,paint);
                    paint.setColor(Color.BLACK);
                }
                dotPos += ladGap;
            }
            int fingerposX = x/8 + stringGap/2 + 10;
            int fingerposY ;
            for (int i = 0; i < strings.length(); i++) {
                paint.setColor(fingers.charAt(i) == '1' ? Color.parseColor("#FAB511") :
                                fingers.charAt(i) == '2' ? Color.parseColor("#A95A9F") :
                                fingers.charAt(i) == '3' ? Color.parseColor("#00B1FF") :
                                fingers.charAt(i) == '4' ? Color.parseColor("#EC680F") : Color.WHITE);

                if (strings.charAt(i) != 'X' && strings.charAt(i) != '0') {
                fingerposY = ladGap * Integer.parseInt(strings.charAt(i)+"") + ladGap / 3;
                    canvas.drawCircle(fingerposX, fingerposY, 30, paint);
                }

                fingerposX += stringGap;
                fingerposY = y/8 + ladGap;
            }
            
            System.out.println(name);
            System.out.println(strings);
            System.out.println(fingers);

            
            
        }
    }


}
