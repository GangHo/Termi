package org.androidtown.termi02;

import android.content.Context;
import android.content.SharedPreferences;

/*
 * Created by Gangho on 2016-11-27.
 *
 *
 */

public class UserToken {

    public static void setPreferences(Context context,String key,String token){
        SharedPreferences pref = context.getSharedPreferences("pref",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,token);
        editor.commit();
    }

    public static String getPreferences(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences("pref",context.MODE_PRIVATE);
        return pref.getString(key,"empty");
    }

    public static void removePreferences(Context context,String key){
        SharedPreferences pref = context.getSharedPreferences("pref",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
    }
}
