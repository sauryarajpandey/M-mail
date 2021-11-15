package com.saurya.controller;

import com.saurya.Emailmanager;
import com.saurya.controller.services.Loginservice;
import com.saurya.model.Emailaccount;
import com.saurya.view.Viewfactor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

//import javafx.scene.control.Button;

//import javafx.stage.Stage;


public class Logindisplaywindow  extends Basecontroller implements Initializable {
    @FXML
    private Label errorLabel;

    @FXML
    private TextField emailaddressField;


    @FXML
    private PasswordField passwordField;




    public Logindisplaywindow(Emailmanager emailmanager, Viewfactor viewfactor, String fxmlName) {
        super(emailmanager, viewfactor, fxmlName);

//        this.emailaddressField = emailaddressField;
    }

    @FXML
    void loginButtonAction() {
        System.out.println("loginButtonAction!!");
        if (fieldsarevalid()) {
            Emailaccount emailaccount = new Emailaccount(emailaddressField.getText(), passwordField.getText());
            Loginservice loginservice = new Loginservice(emailaccount, emailmanager);
            loginservice.start();
            loginservice.setOnSucceeded(event -> {
                Emailloginresult emailLoginResult = loginservice.getValue();
                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("login succesfull!!!" + emailaccount);
                        if(!viewfactor.isMainviewInitialized()){
                            viewfactor.showDisplaywindow();
                        }
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewfactor.closeStage(stage);
                        return;
                    case EMAIL_OR_PASSWORD_IS_NOT_CORRECT:
                        System.out.println("Invalid Validation");
                        return;
                    case UNEXPECTED_ERROR:
                        System.out.println("Unexpected Error Occured");
                        return;
                    case NETWORK_FAIL:
                        System.out.println(" Please check your Internet Connection");
                        return;
                    default:
                        return;
                }

            });


        }
    }

    private boolean fieldsarevalid () {
        if (emailaddressField.getText().isEmpty()) {
            errorLabel.setText("****Please fill email");
            return false;
        }
        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("****Please fill password");
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailaddressField.setText("saurya.pandey21@gmail.com");
        passwordField.setText("Saurya123.");
    }
}
