package me.matata.matamenu.general.utils;

import java.util.HashMap;

/**
 * @author matata
 * @date 2020/3/13 22:25
 */
public class LangUtil {

    private static HashMap<String, String> messages = new HashMap<>();

    public LangUtil(){

    }

    public static String getMessage(String matcher){
        return translateMatcher(matcher);
    }

    public static String getMessage(String matcher, String[] args){
        return translateArgs(translateMatcher(matcher), args);
    }

    public static String translateMatcher(String matcher){
        return "";
    }

    public static String translateArgs(String matcher, String[] args){
        return "";
    }


}
