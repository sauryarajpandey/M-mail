package com.saurya.view;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;
    public  static  String getCsspath(FontSize fontSize){
        switch (fontSize){
            case MEDIUM :
                return "css/fontMedium.css";
            case BIG:
                return "css/fontBig.css";
            case SMALL:
                return "css/fontSmall.css";
            default:
                return null;
        }
    }
}
