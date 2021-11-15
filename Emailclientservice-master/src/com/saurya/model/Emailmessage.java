package com.saurya.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Emailmessage{

    private SimpleStringProperty Sender;
    private SimpleObjectProperty<Date> Date;
    private SimpleStringProperty Reciever;
    private SimpleObjectProperty<sizeinteger> Size;
    private  SimpleStringProperty Subject;
    private boolean isRead;
    private Message message;
    private List<MimeBodyPart> attachmentList = new ArrayList<MimeBodyPart>();
    private boolean hasAttachments = false;

    public Emailmessage(String Sender, Date Date, String Receiver, int Size, String Subject,
                        boolean isRead , Message message) {
        this.Sender=new SimpleStringProperty(Sender);
        this.Date=new SimpleObjectProperty<Date>(Date);
        this.Reciever=new SimpleStringProperty(Receiver);
        this.Size=new SimpleObjectProperty<sizeinteger>(new sizeinteger(Size));
        this.Subject=new SimpleStringProperty(Subject);
        this.isRead=isRead;
        this.message=message;

    }
    public boolean hasAttachments(){
        return hasAttachments;
    }

    public String getSender() {
        return this.Sender.get();
    }
    public Date getDate() {
        return this.Date.get();
    }
    public String getReciever() {
        return this.Reciever.get();
    }
    public  sizeinteger getSize() {
        return this.Size.get();
    }
    public String getSubject() {
        return this.Subject.get();
    }
    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read){
        isRead=read;
    }
    public Message getMessage() {
        return this.message;
    }

    public List<MimeBodyPart> getAttachmentList(){
        return  attachmentList;
    }

    public void addAttachment(MimeBodyPart mbp) {
        hasAttachments=true;
        attachmentList.add(mbp);
        try{
            System.out.println("Added attachment : " + mbp.getFileName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
