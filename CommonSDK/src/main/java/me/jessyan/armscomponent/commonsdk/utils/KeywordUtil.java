package me.jessyan.armscomponent.commonsdk.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.jessyan.autosize.utils.LogUtils;

/**
 * 文字变色工具类
 */
public class KeywordUtil {

    /**
     * 关键字高亮变色
     *
     * @param color
     *            变化的色值
     * @param text
     *            文字
     * @param keyword
     *            文字中的关键字
     * @return 结果SpannableString
     */
    public static SpannableString matcherSearchTitle(int color, String text, String keyword) {
        SpannableString s;
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0039e1"));
        if(text==null){
         s =null;
        }else {
            s = new SpannableString(text);
            keyword = escapeExprSpecialWord(keyword);
            text = escapeExprSpecialWord(text);
            if (text.contains(keyword) && !TextUtils.isEmpty(keyword)) {
                try {
                    Pattern p = Pattern.compile(keyword);
                    Matcher m = p.matcher(s);
                    while (m.find()) {
                        int start = m.start();
                        int end = m.end();
                        s.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                }
            }
        }
        return s;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return keyword
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }
}