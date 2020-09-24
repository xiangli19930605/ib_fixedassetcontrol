package me.jessyan.armscomponent.commonsdk.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import me.jessyan.armscomponent.commonsdk.R;
import me.jessyan.armscomponent.commonsdk.app.MyApplication;

/**
 * @author quchao
 * @date 2017/11/27
 */

public class CommonUtils {



    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red =random.nextInt(150);
        //0-190
        int green =random.nextInt(150);
        //0-190
        int blue =random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red,green, blue);
    }


    /**
     * 设置指定字体高亮
     *
     * @return CharSequence型字符串
     */
    public static CharSequence getHighLightText(Context context, String text, String keyword) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        if (!keyword.isEmpty()) {
            int base = 0;//基准index，表示每一次进行字符串截取之后，新字符字符串的开始index相对于text原始字符串的位置
            int start;
            do {
                Log.i("getHighLightText", "现在的text：" + text);
                start = text.indexOf(keyword);
                int end;
                if (start >= 0) {
                    end = start + keyword.length();
                    style.setSpan(
                            new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
                            base + start, base + end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text = text.substring(end);
                    base += end;
                }
            } while (start >= 0 && text.length() > 0);
        }
        return style;
    }



}
