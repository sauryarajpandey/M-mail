package com.saurya.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import java.util.List;

public class folderuppdaterservice extends Service {
    private List<Folder> folderList;


    public folderuppdaterservice(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @Override
    protected Task createTask() {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                for(;;){
                    try {
                        Thread.sleep(4000);
                        for(Folder folder : folderList){
                            if(folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()){
                                folder.getMessageCount();
                            }
                        }
                    }
                    catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
        };
    }
}
