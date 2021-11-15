package com.saurya.controller.services;
import com.saurya.Emailmanager;
import com.saurya.controller.Emailloginresult;
import com.saurya.model.Emailaccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;
public class Loginservice extends Service<Emailloginresult> {
    Emailaccount emailaccount;
    Emailmanager emailmanager;

    public Loginservice(Emailaccount emailaccount, Emailmanager emailmanager) {
        this.emailaccount = emailaccount;
        this.emailmanager = emailmanager;
    }

    private Emailloginresult login(){
        Authenticator authenticator= new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailaccount.getAddress(),emailaccount.getPassword());
            }
        };
        try {
            Thread.sleep(5000);
            Session session = Session.getInstance(emailaccount.getProperties(),authenticator);
            emailaccount.setSession(session);
            Store store = session.getStore("imaps");
            store.connect(emailaccount.getProperties().getProperty("IncomingHost"),
                    emailaccount.getAddress(),
                    emailaccount.getPassword());
            emailaccount.setStore(store);
            emailmanager.addEmailaccount(emailaccount);
        }
        catch (NoSuchProviderException e) {
            e.printStackTrace();
            return Emailloginresult.NETWORK_FAIL;
        } catch (AuthenticationFailedException e){
            e.printStackTrace();
            return Emailloginresult.EMAIL_OR_PASSWORD_IS_NOT_CORRECT;
        } catch (MessagingException e) {
            e.printStackTrace();
            return Emailloginresult.UNEXPECTED_ERROR;
        }catch (Exception e){
            e.printStackTrace();
            return  Emailloginresult.UNEXPECTED_ERROR;
        }

        return Emailloginresult.SUCCESS;
    }

    @Override
    protected Task<Emailloginresult> createTask() {
        return new Task<Emailloginresult>() {
            @Override
            protected Emailloginresult call() throws Exception {
                return login();
            }
        };
    }
}