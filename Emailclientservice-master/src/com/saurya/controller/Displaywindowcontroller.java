package com.saurya.controller;

import com.saurya.Emailmanager;
import com.saurya.model.Emailmessage;
import com.saurya.model.Emailtreeitems;
import com.saurya.model.sizeinteger;
import com.saurya.view.Viewfactor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;
//import com.saurya.controller.services.folderupdaterservice;
import  com.saurya.controller.services.messagerenderservice;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class Displaywindowcontroller extends Basecontroller implements Initializable {
    private MenuItem markUnreadMenuitem= new MenuItem("Mark as Unread");
    private MenuItem deletedMenuitem = new MenuItem("Delete");
    private MenuItem showMessageDetailsMenuItem = new MenuItem("View Details");
    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private WebView emailsWebView;


    @FXML
    private  TableView<Emailmessage> emailsTableView;

    @FXML
    private TableColumn<Emailmessage, String> sendercol;

    @FXML
    private TableColumn<Emailmessage, Date> datecol;

    @FXML
    private TableColumn<Emailmessage, String> recievercol;

    @FXML
    private TableColumn<Emailmessage, sizeinteger> sizecol;

    @FXML
    private TableColumn<Emailmessage, String> subjectcol;

    private  messagerenderservice messagerenderservice;

    public Displaywindowcontroller(Emailmanager emailmanager, Viewfactor viewfactor, String fxmlName) {
        super(emailmanager, viewfactor, fxmlName);
//        this.emailsTableView = emailsTableView;
    }

    @FXML
    void optionsAction() {
        viewfactor.showmoreWindows();
    }


    @FXML
    void addAccountAction(){
        viewfactor.showlogindisplay();
    }
    @FXML
    void composeMessageAction() {
        viewfactor.showComposeMessageWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setUpemailsTreeView();
        setUpemailsTableView();
        setUpfolderSelection();
        setUpBoldrows();
        setUpmessagerenderservice();
        setUpmessageselection();
        setUpContextMenu();

    }
    private void setUpContextMenu(){
        markUnreadMenuitem.setOnAction(event -> {
            emailmanager.setUnRead();
        });

        deletedMenuitem.setOnAction(event -> {
            emailmanager.deleteSelectedmessage();
            emailsWebView.getEngine().loadContent("");
        });

        showMessageDetailsMenuItem.setOnAction(event -> {
            viewfactor.showEmailDetailsWindow();
        });
    }

    private void setUpmessageselection() {
        emailsTableView.setOnMouseClicked(e -> {
            Emailmessage emailmessage = emailsTableView.getSelectionModel().getSelectedItem();
            if (emailmessage != null) {
                emailmanager.setSelectedMessage(emailmessage);
                if(!emailmessage.isRead()){
                    emailmanager.setRead();
                }
                emailmanager.setSelectedMessage(emailmessage);
                messagerenderservice.setEmailmessage(emailmessage);
                messagerenderservice.restart();
            }


        });
    }

    private void setUpmessagerenderservice() {
        messagerenderservice = new messagerenderservice(emailsWebView.getEngine());
    }

    private void setUpBoldrows() {
        emailsTableView.setRowFactory(new Callback<TableView<Emailmessage>, TableRow<Emailmessage>>() {
            @Override
            public TableRow<Emailmessage> call(TableView<Emailmessage> param) {
                return new TableRow<Emailmessage>(){
                    @Override
                    protected  void  updateItem (Emailmessage item,boolean empty){
                        super.updateItem(item, empty);
                        if(item !=null){
                            if(item.isRead()){
                                setStyle(" ");
                            }else{
                                setStyle("-fx-font-weight: bold");

                            }
                        }

                    }
                };

            }
        });
    }

    public void setUpfolderSelection() {
        emailsTreeView.setOnMouseClicked(e->{
            Emailtreeitems<String> item = (Emailtreeitems<String>)emailsTreeView.getSelectionModel().getSelectedItem();
            if (item != null) {
                emailmanager.setSelectedFolder(item);
                emailsTableView.setItems(item.getEmailmessages() );
            }
        });
    }

    private void setUpemailsTableView() { //  sender date reciver size subject
        sendercol.setCellValueFactory((new PropertyValueFactory<Emailmessage, String>("Sender")));
        datecol.setCellValueFactory((new PropertyValueFactory<Emailmessage, Date>("Date")));
        recievercol.setCellValueFactory((new PropertyValueFactory<Emailmessage, String>("Reciever")));
        sizecol.setCellValueFactory((new PropertyValueFactory<Emailmessage, sizeinteger>("Size")));
        subjectcol.setCellValueFactory((new PropertyValueFactory<Emailmessage, String>("Subject")));

        emailsTableView.setContextMenu(new ContextMenu(markUnreadMenuitem,deletedMenuitem,showMessageDetailsMenuItem));
    }

    private void setUpemailsTreeView() {
        emailsTreeView.setRoot(emailmanager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }


}
