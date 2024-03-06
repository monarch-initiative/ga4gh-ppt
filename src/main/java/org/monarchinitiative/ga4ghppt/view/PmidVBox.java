package org.monarchinitiative.ga4ghppt.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.monarchinitiative.ga4ghppt.controller.PmidVBoxController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PmidVBox extends VBox {
    private final static Logger LOGGER = LoggerFactory.getLogger(PmidVBox.class);

    private PmidVBoxController controller;

    public PmidVBox() {
     super();
        try {
        FXMLLoader loader = new FXMLLoader(IndividualTab.class.getResource("PmidVBox.fxml"));
        controller = new PmidVBoxController();
        loader.setController(controller);
        Node node = loader.load();
        this.getChildren().add(node);

    } catch (Exception e) {
        LOGGER.error("Error loading IndividualTabController: {}", e.getMessage());
    }
    }
}
