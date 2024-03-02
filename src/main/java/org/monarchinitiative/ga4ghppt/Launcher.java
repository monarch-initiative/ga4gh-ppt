package org.monarchinitiative.ga4ghppt;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

/**
 * A JavaFX App for creating ROBOT templates to add or modify terms
 * of the Human Phenotype Ontology (HPO)
 * @author Peter Robinson
 */
public class Launcher extends Application {

   // ViewFactory viewFactory = null;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
       // Options options = PersistenceAccess.loadFromPersistence();
        HostServices hostServices = getHostServices();
      //  viewFactory = new ViewFactory(options, hostServices);
       // viewFactory.showMainWindow();
      //  stage.setOnCloseRequest(e -> PersistenceAccess.saveToPersistence(viewFactory.getOptions()));
    }

    @Override
    public void stop() {
        /*if (viewFactory != null) {
            PersistenceAccess.saveToPersistence(viewFactory.getOptions());
        }*/
    }
}
