module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires  javafx.web;
    requires  activation;
    requires java.mail;
    requires java.desktop;
    requires java.compiler;
    opens com.saurya;
    opens com.saurya.controller;
    opens com.saurya.view;
    opens com.saurya.model;
}