package me.jessyan.armscomponent.commonsdk.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import me.jessyan.armscomponent.commonsdk.R;
import me.jessyan.armscomponent.commonsdk.app.MyApplication;


/**
 * Created by hasee on 2016/12/19.
 */

public class ToastUtil {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    public static int TPYE_SUCCESS = 1;
    public static int TPYE_FAILURE = 0;



    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }

        oneTime = twoTime;
    }

    public static void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oldMsg = s;
        oneTime = twoTime;
    }

    public static void showToast(int type, String s) {
        if (toast == null) {
            toast = new Toast(MyApplication.getInstance());
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 12, 20);
      View toastLayout=  LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.common_toast, null);
        TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
        Drawable titleDrawable;
        if(type==TPYE_SUCCESS){
            titleDrawable = MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_toast_success);
        }else {
            titleDrawable = MyApplication.getInstance().getResources().getDrawable(R.drawable.ic_toast_failure);
        }
        titleDrawable.setBounds(0, 0, titleDrawable.getMinimumWidth(), titleDrawable.getMinimumHeight());
        txtToast.setCompoundDrawables(null, titleDrawable, null, null);
        txtToast.setCompoundDrawablePadding(10);
        txtToast.setText(s);
        toast.setView(toastLayout);
        toast.show();

    }

}
