package com.saurya.view;

import com.saurya.Emailmanager;
import com.saurya.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Viewfactor {
    private Emailmanager emailmanager;
    private ArrayList<Stage> activeStages;
    private  boolean mainViewInitialized=false;

    public Viewfactor(Emailmanager emailmanager) {

        this.emailmanager = emailmanager;
        activeStages = new ArrayList<Stage>();
    }
    public boolean isMainviewInitialized(){
        return mainViewInitialized;
    }

    //For the editting the setttings
    private  Theme theme= Theme.DEFAULT  ;
    private FontSize fontSize= FontSize.MEDIUM;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public void showlogindisplay() {
        System.out.println(" Logged In ");
        Basecontroller controller = new Logindisplaywindow(emailmanager, this, "logindisplay.fxml");
        intializeStage(controller);

    }

    public void showDisplaywindow() {
        //  System.out.println(" logged for the display ");
        Basecontroller controller = new Displaywindowcontroller(emailmanager, this, "Displaywindow.fxml");
        intializeStage(controller);
        mainViewInitialized=true;

    }
    public void  showComposeMessageWindow(){
        // System.out.println("Message Window called");
        Basecontroller controller= new ComposeMessageController(emailmanager,this,"ComposeMessageWindow.fxml");
        intializeStage(controller);
    }
    public void  showmoreWindows(){
        //  System.out.println("Settings called");
        Basecontroller controller= new moreWindowscontroller(emailmanager,this,"moreWindows.fxml");
        intializeStage(controller);
    }


    public  void showEmailDetailsWindow(){
        Basecontroller controller = new EmailDetailsController(emailmanager,this,"EmailDetailsWindow.fxml");
        intializeStage(controller);
    }


    private  void intializeStage(Basecontroller basecontroller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(basecontroller.getFxmlName()));
        fxmlLoader.setController(basecontroller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
    }
    public  void closeStage(Stage stageToClose){
        stageToClose.close();
        activeStages.remove(stageToClose);
    }


    public void updatestyles() {
        for  (Stage stage: activeStages){
            Scene scene= stage.getScene();
            // for the css
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(Theme.getCsspath(theme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCsspath(fontSize)).toExternalForm());
        }
    }

}

