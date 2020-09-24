package com.idealbank.ib_secretassetcontrol.Netty;

import android.os.Environment;

/**
 * Created by shixianj on 2018/9/5.
 */

public class AppConstants {

    public static String ROOT_PATH = "/AssertCheck";
    public static String SD_PATH = "/AssertCheck";

    public static final String TASK_FILE_PATH = "/task";

    public static final String RESULT_FILE_PATH = "/result";

    public static final String HISTORY_FILE_PATH = getSDPath()+ROOT_PATH+"/history";

    public static String getSDPath(){
        return Environment.getExternalStorageDirectory().getPath();
    }
    public static String getPath(){
        return getSDPath()+HISTORY_FILE_PATH;
    }



}
