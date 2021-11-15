package com.saurya;

import com.saurya.controller.persistance.PersistanceAccess;
import com.saurya.controller.persistance.ValidAccount;
import com.saurya.controller.services.Loginservice;
import com.saurya.model.Emailaccount;
import com.saurya.view.Viewfactor;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private PersistanceAccess persistanceAccess = new PersistanceAccess();
    private Emailmanager emailmanager = new Emailmanager();

    @Override
    public void start(Stage stage) {
        Viewfactor viewfactor = new Viewfactor(emailmanager);
        // viewfactor.showlogindisplay();;
        List<ValidAccount> validAccountList = persistanceAccess.loadFromPresistance();
        if (validAccountList.size() > 0) {
            viewfactor.showDisplaywindow();
            for (ValidAccount validAccount : validAccountList) {
                Emailaccount emailaccount = new Emailaccount(validAccount.getAddress(), validAccount.getPassword());
                Loginservice loginservice = new Loginservice(emailaccount, emailmanager);
                loginservice.start();
            }
        } else {
            viewfactor.showlogindisplay();
        }
        viewfactor.updatestyles();
    }




    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for(Emailaccount emailaccount : emailmanager.getEmailaccounts()){
            validAccountList.add(new ValidAccount(emailaccount.getAddress(), emailaccount.getPassword()));
        }
        persistanceAccess.saveToPersistence(validAccountList);
    }
}
