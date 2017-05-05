package org.tll.falciniz;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by abdullahtellioglu on 21/04/17.
 */
public class SharedPrefUtils {
    private static final String PREF_NAME = "FALCINIZ";
    public static void saveString(String str,String key,Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,str);
        editor.commit();
    }
    public static void saveInt(int i,String key,Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(key, i);
        editor.commit();
    }
    public static void saveBoolean(boolean b,String key,Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, b);
        editor.commit();
    }
    public static int getInt(String key,Context context,int defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return preferences.getInt(key,defaultValue);
    }
    public static String getString(String key,Context context,String defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }
    public static boolean getBoolean(String key,Context context,boolean defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }
}
