package com.osos.bottomsheetrecycler;

import android.content.Context;

public class SharedPreferenceService {
    public static String getLastUsedImage(Context context){
        return context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE).getString("imageUrl","");
    }
    public static void setLastUsedImage(Context context,String url){
        context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE).edit().putString("imageUrl",url).apply();
    }
    public static String getLastUsedText(Context context){
        return context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE).getString("text","");
    }
    public static void setLastUsedText(Context context,String t){
        context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE).edit().putString("text",t).apply();
    }
}
