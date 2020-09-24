package me.jessyan.armscomponent.commonsdk.data.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.core.Constants;

/**
 * @author flymegoc
 * @date 2018/3/4
 */

@Singleton
public class PreferenceHelperImpl implements PreferenceHelper {

    private static final String TAG = PreferenceHelperImpl.class.getSimpleName();

    private Gson gson;

    private  SharedPreferences mPreferences;

    @Inject
    public PreferenceHelperImpl() {
        mPreferences = MyApplication.getInstance().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }




    @Override
    public boolean getNightModeState() {
        return mPreferences.getBoolean(Constants.NIGHT_MODE_STATE,false);
    }

    @Override
    public void putBoolean(String key, boolean value) {
         mPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    @Override
    public void putString(String key, String defValue) {
        mPreferences.edit().putString(key,defValue).apply();
    }

    @Override
    public String getString(String key) {
        return    mPreferences.getString(key,"");
    }

    @Override
    public void setNightModeState(boolean b) {
        mPreferences.edit().putBoolean(Constants.NIGHT_MODE_STATE, b).apply();
    }








}
