package org.tll.falciniz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burhanboz on 20/05/2017.
 */
public class OldMagicsActivity extends Activity{

    final List<EskiFallar> fallar=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_magics);
        final ListView listemiz = (ListView) findViewById(R.id.activity_list_old_magics);

        fallar.add(new EskiFallar("ajdkajsdkaksdlkaj"));
        fallar.add(new EskiFallar("132412341234"));
        fallar.add(new EskiFallar("ajdkajdfasfasfsdkaksdlkaj"));
        fallar.add(new EskiFallar("ajdkajsdkaasaksdlkaj"));


        OldMagicAdapter adaptorumuz=new OldMagicAdapter(this, fallar);
        listemiz.setAdapter(adaptorumuz);

    }
}
