package com.saurya.controller.persistance;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistanceAccess {

    private String VALID_ACCOUNTS_LOCATION = System.getProperty("User.saury") + File.separator + "validAccounts.ser";

    private Encoder encoder = new Encoder();

    public List<ValidAccount> loadFromPresistance(){
        List<ValidAccount> resultList = new ArrayList<ValidAccount>();
        try {
            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNTS_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> persistedList = (List<ValidAccount>) objectInputStream.readObject();
            decodePasswords(persistedList);
            resultList.addAll(persistedList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }

    private void decodePasswords(List<ValidAccount> persistedList) {
        for(ValidAccount validAccount : persistedList){
            String originalpassword= validAccount.getPassword();
            validAccount.setPassword(encoder.decode(originalpassword));
        }
    }

    private void encodePasswords(List<ValidAccount> persistedList) {
        for(ValidAccount validAccount : persistedList){
            String originalpassword= validAccount.getPassword();
            validAccount.setPassword(encoder.encode(originalpassword));
        }
    }


    public void saveToPersistence(List<ValidAccount> validAccounts) {
        try {
            File file = new File(VALID_ACCOUNTS_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            encodePasswords(validAccounts);
            objectOutputStream.writeObject(validAccounts);
            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
}
