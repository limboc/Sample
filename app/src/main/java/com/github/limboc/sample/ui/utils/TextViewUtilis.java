package com.github.limboc.sample.ui.utils;

/**
 * Created by Chen on 2016/4/5.
 */
public class TextViewUtilis {
    public static String setMaxLength(String text, int length){
        if(text.length() > length){
            text = text.substring(0, length) + "...";
        }
        return text;
    }
}
