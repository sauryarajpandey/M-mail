package com.saurya.model;

import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class Emailaccount {
    public static Object sendMail;
    private String address;
    private String password;
    private Properties properties = new Properties();
    private Store store;
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {

        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return address;
    }

    public Emailaccount(String address, String password) {
        try {
            this.address = "saurya.pandey21@gmail.com";
            this.password = "Saurya123.";
            properties.put("IncomingHost", "imap.gmail.com");// imap protocol is used for sending the protocols
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.transport.protocol", "smtps");//smtp protocol is used for recieving the protocols
            properties.put("mail.smtp.host", "587");
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starthis.enable", true);
            properties.put("outgoingHost", "smtp.gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


