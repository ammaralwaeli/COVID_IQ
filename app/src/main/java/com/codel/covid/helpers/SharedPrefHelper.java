package com.codel.covid.helpers;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefHelper {
    private static SharedPrefHelper instance;

    private static String PREF_NAME = "mySettingsPref";
    private static String TOTAL = "TOTAL";
    private static String DEATHS = "DEATHS";
    private static String RECOVERED = "RECOVERED";

    public static void init(Context context) {
        instance= new SharedPrefHelper(context);
    }


    public static SharedPrefHelper getInstance(){
        if(instance == null) throw new NullPointerException();
        return instance;
    }

    private SharedPreferences mSharedPreferences;

    private SharedPrefHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public String getTOTAL() {
        return mSharedPreferences.getString(TOTAL,"000,000");
    }

    public void setTOTAL(String total) {
        mSharedPreferences.edit().putString(TOTAL, total).apply();
    }

    public String getDEATHS() {
        return mSharedPreferences.getString(DEATHS,"00,000");
    }

    public void setDEATHS(String deaths) {
        mSharedPreferences.edit().putString(DEATHS, deaths).apply();
    }

    public String getRECOVERED() {
        return mSharedPreferences.getString(RECOVERED,"000,000");
    }

    public void setRECOVERED(String recovered) {
        mSharedPreferences.edit().putString(RECOVERED, recovered).apply();
    }

}
