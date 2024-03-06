package org.monarchinitiative.ga4ghppt;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import org.monarchinitiative.ga4ghppt.controller.persistence.PersistenceAccess;
import org.monarchinitiative.ga4ghppt.model.Options;
import org.monarchinitiative.ga4ghppt.view.ViewFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A JavaFX App for creating ROBOT templates to add or modify terms
 * of the Human Phenotype Ontology (HPO)
 * @author Peter Robinson
 */
public class Launcher extends Application {
    final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

   ViewFactory viewFactory = null;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        LOGGER.info("Starting app");
        Options options = PersistenceAccess.loadFromPersistence();
        HostServices hostServices = getHostServices();
        viewFactory = new ViewFactory(options, hostServices);
        viewFactory.showMainWindow();
        stage.setOnCloseRequest(e -> PersistenceAccess.saveToPersistence(viewFactory.getOptions()));
    }

    @Override
    public void stop() {
        /*if (viewFactory != null) {
            PersistenceAccess.saveToPersistence(viewFactory.getOptions());
        }*/
    }
}
