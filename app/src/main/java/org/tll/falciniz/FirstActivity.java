package org.tll.falciniz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.Random;


/**
 * Created by burhanboz on 17/05/2017.
 */
public class FirstActivity extends Activity {
    private static final int REQUEST1 = 1;
    private Button btnMagicActivity;
    private Button btnOldMagicActivity;
    private String[] randomContext;
    private static final Random rgenerator = new Random();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Resources res = getResources();

        randomContext = res.getStringArray(R.array.randomText);

        String q = randomContext[rgenerator.nextInt(randomContext.length)];

        TextView tv = (TextView) findViewById(R.id.activity_text);
        tv.setText(q);

        btnMagicActivity = (Button) findViewById(R.id.activity_first_btn_send_magic);
        btnOldMagicActivity = (Button) findViewById(R.id.activity_first_btn_old_magic);

        btnOldMagicActivity.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i2 = new Intent(FirstActivity.this, OldMagicsActivity.class);
                startActivity(i2);
            }
        });


        btnMagicActivity.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(FirstActivity.this, MainActivity.class);
                i.putExtra("name","burhan");
                startActivityForResult(i,REQUEST1);
            }
        });
    }
}
