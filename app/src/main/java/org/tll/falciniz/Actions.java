package org.tll.falciniz;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by abdullahtellioglu on 21/04/17.
 */
public class Actions {
    private static Actions mInstance = null;
    public static Actions getInstance(){
        if(mInstance==null){
            mInstance = new Actions();
        }
        return mInstance;
    }

    public void sendMagic(Bitmap[] images,User user,Context context) throws ResourceException{
        String uniqueID = UUID.randomUUID().toString();
        sendToBackend(context, images, user,uniqueID);
        setAlarm(context, uniqueID);
    }
    public void sendNotification(Context context,String id,String selected){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.cup_coffee)
                        .setContentTitle("Falın geldiii!!!")
                        .setAutoCancel(true)
                        .setContentText(selected + " abla falına baktı. Sonucu görmek için tıkla!");
        Intent resultIntent = new Intent(context, ResultActivity.class);
        resultIntent.putExtra("id",id);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.cancelAll();
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
    private void sendToBackend(Context context,final Bitmap[] images,final User user,final String uniqueId) throws ResourceException{
        StringRequest sr = new StringRequest(Request.Method.POST,"http://www.ab2software.com/Falciniz/upload.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("VOLLEY RESPONSE",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", user.getName());
                params.put("age", String.valueOf(user.getAge()));
                params.put("relation", user.isRelation() ? "var" : "yok");
                params.put("gender", !user.isGender() ? "Kadin" : "Erkek");
                params.put("token", FirebaseInstanceId.getInstance().getToken());
                params.put("clientId",uniqueId);
                if (images != null) {
                    for (int i = 0; i < images.length; i++) {
                        if (images[i] == null) {
                            params.put("image" + i + "", "null");
                        } else {

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            images[i].compress(Bitmap.CompressFormat.JPEG, 90, stream); //compress to which format you want.
                            byte[] byte_arr = stream.toByteArray();
                            String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                            params.put("image" + i + "", image_str);
                        }
                    }
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;

            }
        };
        Vol.getInstance(context).addToRequestQueue(sr);
    }
    private void setAlarm(Context context,String id){
        Intent intent = new Intent(context,OnMagicResultReceiver.class);
        intent.putExtra("id",id);
        Log.e("generated id actions",id);
        PendingIntent sender = PendingIntent.getBroadcast(context,0,intent,0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5 + new Random().nextInt(10));
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }
    public void register(Context context,User user){
        SharedPrefUtils.saveString(user.getName(),"username",context);
        SharedPrefUtils.saveInt(user.getAge(), "age", context);
        SharedPrefUtils.saveBoolean(user.isRelation(), "relation", context);
        SharedPrefUtils.saveBoolean(user.isGender(), "gender", context);
    }
    public User getUser(Context context){
        User user = new User();
        user.setName(SharedPrefUtils.getString("username",context,""));
        user.setAge(SharedPrefUtils.getInt("age", context, 0));
        user.setRelation(SharedPrefUtils.getBoolean("relation", context, false));
        user.setGender(SharedPrefUtils.getBoolean("gender",context,false));
        return user;
    }
    public class User{
        private String name;
        private int age;
        private boolean relation;
        private boolean gender;//0 woman

        public boolean isGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        public String getName() {

            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isRelation() {
            return relation;
        }

        public void setRelation(boolean relation) {
            this.relation = relation;
        }
    }
}
