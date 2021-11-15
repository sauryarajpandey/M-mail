package com.saurya.controller;

import com.saurya.Emailmanager;
import com.saurya.view.FontSize;
import com.saurya.view.Theme;
import com.saurya.view.Viewfactor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class moreWindowscontroller extends Basecontroller implements Initializable {


    public moreWindowscontroller(Emailmanager emailmanager, Viewfactor viewfactor, String fxmlName) {
        super(emailmanager, viewfactor, fxmlName);
    }
    @FXML
    private Slider fontSizepicker;

    @FXML
    private ChoiceBox<Theme> themePicker;


    @FXML
    void applyButtonAction() {
        viewfactor.setTheme(themePicker.getValue());
        viewfactor.setFontSize(FontSize.values()[(int)(fontSizepicker.getValue())]);
        System.out.println(viewfactor.getTheme());
        System.out.println(viewfactor.getFontSize());
        viewfactor.updatestyles();



    }

    @FXML
    void cancelButtonAction() {
        Stage stage = (Stage)fontSizepicker.getScene().getWindow();
        viewfactor.closeStage(stage);


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpThemePicker();
        setUpSizePicker();

    }

    private  void setUpSizePicker() {
        fontSizepicker.setMin(0);
        fontSizepicker.setMax(FontSize.values().length - 1 );
        fontSizepicker.setValue(viewfactor.getFontSize().ordinal());
        fontSizepicker.setMajorTickUnit(1);
        fontSizepicker.setMinorTickCount(0);
        fontSizepicker.setBlockIncrement(1);
        fontSizepicker.setSnapToTicks(true);
        fontSizepicker.setShowTickMarks(true);
        fontSizepicker.setShowTickLabels(true);
        fontSizepicker.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                int i = object.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        fontSizepicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            fontSizepicker.setValue(newVal.intValue());
        });
    }

    private  void setUpThemePicker(){
        themePicker.setItems(FXCollections.observableArrayList(Theme.values()));
        themePicker.setValue(viewfactor.getTheme());
    }
}
