package org.monarchinitiative.ga4ghppt.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import org.monarchinitiative.ga4ghppt.controller.IndividualTabController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndividualTab extends Tab {

    private final Logger LOGGER = LoggerFactory.getLogger(IndividualTab.class);
    private IndividualTabController controller;




    public IndividualTab() {
        super();
        LOGGER.info("Loading individual tab");
        try {
            FXMLLoader loader = new FXMLLoader(IndividualTab.class.getResource("IndividualVBox.fxml"));
            controller = new IndividualTabController();
            LOGGER.info("Initialized controller: {}", controller);
            loader.setController(controller);
            Node node = loader.load();
            LOGGER.info("Loaded node: {}", node.toString());
            this.setContent(node);
            StringProperty parentTermLabelStringProperty = new SimpleStringProperty("");
            //parentTermLabelStringProperty.bindBidirectional(controller.parentTermProperty());
            //parentTermReadyProperty.bind(controller.parentTermsReady());
        } catch (Exception e) {
            LOGGER.error("Error loading IndividualTabController: {}", e.getMessage());
        }
    }

}
