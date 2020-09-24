package me.jessyan.armscomponent.commonsdk.data.prefs;


/**
 * @author flymegoc
 * @date 2018/3/4
 */

public interface PreferenceHelper {

    /**
     * Get night mode state
     *
     * @return if is night mode
     */
    boolean getNightModeState();
    void putBoolean(String key, boolean defValue);
    boolean getBoolean(String key, boolean defValue);
    void putString(String key, String defValue);
    String getString(String key);
    /**
     * Set night mode state
     *
     * @param b current night mode state
     */
    void setNightModeState(boolean b);



}
