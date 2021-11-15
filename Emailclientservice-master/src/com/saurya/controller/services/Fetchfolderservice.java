package com.saurya.controller.services;
import com.saurya.model.Emailtreeitems;
import com.saurya.view.IconResolver;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import java.util.List;

public class Fetchfolderservice extends Service<Void> {
    private Store store;
    private Emailtreeitems<String> foldersRoot ;
    private List<Folder> folderList;
    private IconResolver iconResolver = new IconResolver();


    public Fetchfolderservice(Store store, Emailtreeitems<String> foldersRoot, List<Folder> folderList) {
        this.store = store;
        this.foldersRoot = foldersRoot;
        this.folderList=folderList;
    }


    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fetchfolders();
                return null;
            }
        };
    }

    private void fetchfolders() throws MessagingException {
        Folder[] folders=store.getDefaultFolder().list();
        handleFolders(folders,foldersRoot);
    }

    private void handleFolders(Folder[] folders, Emailtreeitems<String> foldersRoot) throws MessagingException {
        for(Folder folder:folders){
            folderList.add(folder);
            Emailtreeitems<String> emailtreeitems = new Emailtreeitems<String>(folder.getName());
            emailtreeitems.setGraphic(iconResolver.getIconForFolder(folder.getName()));
            foldersRoot.getChildren().add((emailtreeitems));
            foldersRoot.setExpanded(true);
            fetchmessageFolder(folder, emailtreeitems);
            addmessagelistenertoFolder(folder,emailtreeitems);// this listen the message
            if(folder.getType() == Folder.HOLDS_FOLDERS){
                Folder[] subFolders = folder.list();
                handleFolders(subFolders,emailtreeitems);
            }
        }
    }

    private void addmessagelistenertoFolder(Folder folder, Emailtreeitems<String> emailtreeitems) {
        folder.addMessageCountListener(new MessageCountListener() {
            @Override
            public void messagesAdded(MessageCountEvent e) {
                for (int i = 0; i < e.getMessages().length; i++) {

                    try {
                        Message message = folder.getMessage(folder.getMessageCount() - i);
                        emailtreeitems.addEmailtotop(message);
                    } catch (MessagingException messagingException) {
                        messagingException.printStackTrace();
                    }

                }
            }

            @Override
            public void messagesRemoved(MessageCountEvent e) {
                System.out.println("message removed " + e);
            }
        });
    }

    private void fetchmessageFolder(Folder folder, Emailtreeitems<String> emailtreeitems) {
        Service fetchmessageService=new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if(folder.getType() != Folder.HOLDS_FOLDERS){
                            folder.open(Folder.READ_WRITE);
                            int foldersize = folder.getMessageCount();
                            for(int i =foldersize;i>0;i--){
                                emailtreeitems.addEmail(folder.getMessage(i));
                            }
                        }
                        return null;
                    }
                };
            }
        };
        fetchmessageService.start();
    }
}
