package org.tll.falciniz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

/**
 * Created by abdullahtellioglu on 21/04/17.
 */
public class OnMagicResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String[] userNames = context.getResources().getStringArray(R.array.userNames);
        String selected;
        if(userNames!=null){
            selected = userNames[new Random().nextInt(userNames.length)];
        }else{
            selected = "Leyla";
        }
        Log.e("id OnMagicResultReceive",intent.getExtras().getString("id"));
        DatabaseHelper helper = new DatabaseHelper(context);
        if(!helper.contains(intent.getExtras().getString("id"))){
            helper.insert("Generated at "+System.currentTimeMillis(),intent.getExtras().getString("id"),selected);
            Actions.getInstance().sendNotification(context, intent.getExtras().getString("id"), selected);
        }

    }
}
