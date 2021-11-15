package com.saurya.controller;

import com.saurya.Emailmanager;
import com.saurya.controller.services.messagerenderservice;
import com.saurya.model.Emailmessage;
import com.saurya.view.Viewfactor;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends Basecontroller implements Initializable {
    private String Location_OF_Downloads = System.getProperty("Users.saury") ; //Downloads

    @FXML
    private Label attachmentLabel;

    @FXML
    private WebView webView;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private HBox hBoxDownloads;

    public EmailDetailsController(Emailmanager emailmanager, Viewfactor viewfactor, String fxmlName) {
        super(emailmanager, viewfactor, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Emailmessage emailmessage = emailmanager.getSelectedMessage();
        subjectLabel.setText(emailmessage.getSubject());
        senderLabel.setText(emailmessage.getSender());
        loadAttachments(emailmessage);
        messagerenderservice messagerenderservice = new messagerenderservice(webView.getEngine());
        messagerenderservice.setEmailmessage(emailmessage);
        messagerenderservice.restart();
    }

    private void loadAttachments(Emailmessage emailmessage) {
        hBoxDownloads.getChildren().clear();
        if (emailmessage.hasAttachments()) {
            for (MimeBodyPart mimeBodyPart : emailmessage.getAttachmentList()) {
                try {
                    AttachmentButton button = new AttachmentButton(mimeBodyPart);
                    hBoxDownloads.getChildren().add(button);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        } else {
            attachmentLabel.setText("");
        }
    }

    private class AttachmentButton extends Button {
        private MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public AttachmentButton(MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadedFilePath = Location_OF_Downloads + mimeBodyPart.getFileName();
            this.setOnAction(e -> downloadAttachment()); // this downloads the attachments
        }
        private  void downloadAttachment(){
            colorBlue();
            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };
            service.restart();
            service.setOnSucceeded(event -> {
                colorGreen();
                this.setOnAction(e2 -> {
                    File file = new File(downloadedFilePath);
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()){
                       try {
                           desktop.open(file);
                       }
                       catch (Exception exp){
                           exp.printStackTrace();
                       }
                    }

                });
            });
        }
        private  void colorBlue(){
            this.setStyle("-fx-background-color: Blue");
        }
        private  void colorGreen(){
            this.setStyle("-fx-background-color: Green");
        }
    }

}
