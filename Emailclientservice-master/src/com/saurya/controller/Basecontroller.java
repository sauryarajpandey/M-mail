package com.saurya.controller;

import com.saurya.Emailmanager;
import com.saurya.view.Viewfactor;

public class Basecontroller {
    protected Emailmanager emailmanager;
    protected Viewfactor viewfactor;
    private  String fxmlName;

    public Basecontroller(Emailmanager emailmanager, Viewfactor viewfactor, String fxmlName) {
        this.emailmanager = emailmanager;
        this.viewfactor = viewfactor;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
