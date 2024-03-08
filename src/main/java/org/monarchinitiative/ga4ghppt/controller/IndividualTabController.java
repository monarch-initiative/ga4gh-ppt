package org.monarchinitiative.ga4ghppt.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.HTMLEditor;
import org.monarchinitiative.ga4ghppt.view.DemographicVBox;
import org.monarchinitiative.ga4ghppt.view.PmidVBox;

import java.net.URL;
import java.util.ResourceBundle;

public class IndividualTabController implements Initializable {


    @FXML
    private HTMLEditor htmlEd;
    @FXML
    private PmidVBox pmidVbox;

    @FXML
    private DemographicVBox demographicVBox;


    public IndividualTabController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("INITI");
    }
}
