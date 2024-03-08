package org.monarchinitiative.ga4ghppt.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DemographicVBoxController implements Initializable  {
    @FXML
    private Button lastEncounterButton;

    @FXML
    private Button onsetButton;

    @FXML
    private TextField probandIdTextField;

    @FXML
    private RadioButton sexF;

    @FXML
    private RadioButton sexM;

    @FXML
    private RadioButton sexO;

    @FXML
    private RadioButton sexU;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
