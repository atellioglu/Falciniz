package org.tll.falciniz;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by abdullahtellioglu on 28/04/17.
 */
public class FMService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("Message","Geldi");
        Log.e("MESSAGE",remoteMessage.toString());
    }

}
