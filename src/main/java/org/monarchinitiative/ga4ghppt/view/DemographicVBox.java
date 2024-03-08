package org.monarchinitiative.ga4ghppt.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.monarchinitiative.ga4ghppt.controller.DemographicVBoxController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemographicVBox extends VBox {
    private final static Logger LOGGER = LoggerFactory.getLogger(DemographicVBox.class);

    private DemographicVBoxController controller;

    public DemographicVBox() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(IndividualTab.class.getResource("DemographcVBox.fxml"));
            controller = new DemographicVBoxController();
            loader.setController(controller);
            Node node = loader.load();
            this.getChildren().add(node);
        } catch (Exception e) {
            LOGGER.error("Error loading DemographicVBoxController: {}", e.getMessage());
        }
    }

}
