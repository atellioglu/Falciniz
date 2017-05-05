package org.tll.falciniz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity {
    private ImageView backButton,image;
    private TextView headerTextView,contextTextView;
    private DatabaseHelper.Magic magic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        String uniqueId = getIntent().getExtras().getString("id");
        magic = helper.get(uniqueId);
        if(magic ==null){
            Log.e("result activity","magic null");
            finish();
        }

        backButton = (ImageView)findViewById(R.id.header_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultActivity.this.onBackPressed();
            }
        });
        image = (ImageView)findViewById(R.id.falciImage);
        headerTextView = (TextView)findViewById(R.id.falciTextView);
        contextTextView = (TextView)findViewById(R.id.contextTextView);
        contextTextView.setText(magic.getContext().toString()+" Generated Id : "+uniqueId);
        headerTextView.setText(magic.getAuthor()+" abla diyor ki...");
        helper.update(magic.getContext(),magic.getUniqueId(),true,magic.getAuthor());
    }
}
