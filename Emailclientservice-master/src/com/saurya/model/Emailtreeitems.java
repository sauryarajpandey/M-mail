package com.saurya.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class Emailtreeitems<String> extends TreeItem<String> {
    //  private static ObservableList<Emailmessage> emailmessages;
    private  String name;
    private ObservableList<Emailmessage> emailmessages;
    private int unreadmessagescount;

    public Emailtreeitems(String name) {
        super(name);
        this.name = name;
        this.emailmessages= FXCollections.observableArrayList();
    }

    public  ObservableList<Emailmessage> getEmailmessages() {
        return emailmessages;
    }
    public void  addEmail(Message message) throws MessagingException {
        Emailmessage emailmessage = extracted(message);
        emailmessages.add(emailmessage);
    }
    public void addEmailtotop(Message message)  throws MessagingException {
        Emailmessage emailmessage = extracted(message);
        emailmessages.add(0,emailmessage );
    }

    private Emailmessage extracted(Message message) throws MessagingException {
        boolean messageIsRead= message.getFlags().contains(Flags.Flag.SEEN);
        Emailmessage emailmessage= new Emailmessage( // sender date receiver size subject
                message.getFrom()[0].toString(),
                message.getSentDate(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSubject(),
                messageIsRead,
                message

        );
//        emailmessages.add(emailmessage);
        if(!messageIsRead){
            incrementmessagescount();
        }
        return emailmessage;
    }

    public  void incrementmessagescount(){
        unreadmessagescount++;
        updateName();
    }
    public  void decrementmessagescount(){
        unreadmessagescount--;
        updateName();
    }
    private  void updateName(){
        if(unreadmessagescount > 0){
            this.setValue((String) (name + "( " + unreadmessagescount + " )" ));
        }
        else {
            this.setValue(name);
        }
    }


}
