package com.saurya.view;

public enum Theme {
    LIGHT,
    DEFAULT,
    DARK;
    public static String getCsspath(Theme theme){
        switch (theme){
            case LIGHT :
                return "css/themelight.css";
            case DEFAULT:
                return "css/themeDefault.css";
            case DARK:
                return "css/themeDark.css";
            default:
                return null;
        }
    }
}
