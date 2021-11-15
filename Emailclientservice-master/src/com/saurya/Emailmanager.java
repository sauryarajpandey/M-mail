package com.saurya;

import com.saurya.controller.services.Fetchfolderservice;
import com.saurya.controller.services.folderuppdaterservice;
import com.saurya.model.Emailaccount;
import com.saurya.model.Emailmessage;
import com.saurya.model.Emailtreeitems;
import com.saurya.view.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.Flags;
import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class Emailmanager {


    private Emailmessage  selectedMessage;
    private Emailtreeitems<String> selectedFolder;
    private ObservableList<Emailaccount> emailaccounts= FXCollections.observableArrayList();
    private IconResolver iconResolver = new IconResolver();

    public ObservableList<Emailaccount> getEmailaccounts() {
        return emailaccounts;
    }

    public Emailmessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(Emailmessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public Emailtreeitems<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(Emailtreeitems<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    private  folderuppdaterservice folderuppdaterservice;
    // this  is particular file  that hanedles all the folder in the email
    private Emailtreeitems<String> foldersRoot = new Emailtreeitems<String>(" ");

    public Emailtreeitems<String> getFoldersRoot() {
        return foldersRoot;
    }
    private List<Folder> folderList = new ArrayList<Folder>();

    public List<Folder> getFolderList() {
        return this.folderList;
    }

    public Emailmanager(){
        folderuppdaterservice = new folderuppdaterservice((folderList ));
        folderuppdaterservice.start();

    }
    public  void  addEmailaccount(Emailaccount emailaccount){
        emailaccounts.add(emailaccount);
        Emailtreeitems<String> treeItem = new Emailtreeitems<String>(emailaccount.getAddress());
        treeItem.setGraphic(iconResolver.getIconForFolder(emailaccount.getAddress()));
        Fetchfolderservice fetchfolderservice = new Fetchfolderservice(emailaccount.getStore(),treeItem,folderList);//folderlist
        fetchfolderservice.start();
        foldersRoot.getChildren().add(treeItem);
    }


    public void setRead() {
        try{
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,true);
            selectedFolder.decrementmessagescount();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setUnRead() {
        try{
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,false);
            selectedFolder.incrementmessagescount();
        }
        catch (Exception e){
            e.printStackTrace(); // deleteSelectedmessage()
        }
    }
    public void deleteSelectedmessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailmessages().remove(selectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}