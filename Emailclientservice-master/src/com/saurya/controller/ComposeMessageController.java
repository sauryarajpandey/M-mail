package com.saurya.controller;

import com.saurya.Emailmanager;
import com.saurya.controller.services.EmailSenderService;
import com.saurya.model.Emailaccount;
import com.saurya.view.Viewfactor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ComposeMessageController extends  Basecontroller implements Initializable {

    private List<File> attachments = new ArrayList<>();
    @FXML
    private TextField recieverTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<Emailaccount> emailAccountChoice;

    @FXML
    void attachButnAction() {
        FileChooser fileChooser = new FileChooser();
        File seletedFile = fileChooser.showOpenDialog(null);
        if(seletedFile !=null){
            attachments.add(seletedFile);
        }
    }

    @FXML
    void sendButtonAction() {
        EmailSenderService emailSenderService = new EmailSenderService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recieverTextField.getText(),
                htmlEditor.getHtmlText(),
                attachments
        );
        emailSenderService.start();
        emailSenderService.setOnSucceeded(event -> {
            EmailSendingResult emailSendingResult = emailSenderService.getValue();
            switch (emailSendingResult){
                case SUCCESS :
                    Stage stage = (Stage) recieverTextField.getScene().getWindow();
                    viewfactor.closeStage(stage);
                    break;
                case UNEXPECTED_ERROR:
                    errorLabel.setText("Incorrect Information !!!");
                    break;
                case NETWORK_FAIL:
                    errorLabel.setText("Network Failure !!!");
                    break;
            }
        });

    }
    public ComposeMessageController(Emailmanager emailmanager, Viewfactor viewfactor, String fxmlName) {
        super(emailmanager, viewfactor, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAccountChoice.setItems(emailmanager.getEmailaccounts());
        emailAccountChoice.setValue(emailmanager.getEmailaccounts().get(0));
    }
}
