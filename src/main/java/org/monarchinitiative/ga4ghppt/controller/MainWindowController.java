package org.monarchinitiative.ga4ghppt.controller;

import javafx.application.HostServices;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import org.monarchinitiative.ga4ghppt.model.Model;
import org.monarchinitiative.ga4ghppt.view.IndividualTab;
import org.monarchinitiative.ga4ghppt.view.ViewFactory;
import org.monarchinitiative.ga4ghppt.widgets.PopUps;
import org.monarchinitiative.phenol.io.OntologyLoader;
import org.monarchinitiative.phenol.ontology.data.MinimalOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {
    private final Logger LOGGER = LoggerFactory.getLogger(MainWindowController.class);

    private final ObjectProperty<MinimalOntology> hpOntology = new SimpleObjectProperty<>();
    @FXML
    public MenuItem newMenuItem;
    @FXML
    public MenuItem exitMenuItem;
    @FXML
    public MenuItem optionsMenuItem;
    @FXML
    public Menu templatesMenu;
    @FXML
    public WebView currentRobotView;
    public MenuItem showVersionsMenuItem;
    public TabPane tabPane;


    @FXML
    private VBox statusBar;
    @FXML
    public Label statusBarLabel;
    private StringProperty statusBarTextProperty;
    private Optional<HostServices> hostServicesOpt;

    private final Model model;

    /** This gets set to true once the Ontology tree has finished initiatializing. Before that
     * we can check to make sure the user does not try to open a disease before the Ontology is
     * done loading.
     */
    private final BooleanProperty ontologyLoadedProperty = new SimpleBooleanProperty(false);

    private final BooleanProperty robotIssueIsReadyProperty = new SimpleBooleanProperty(false);


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        model = new Model();
    }

    @FXML
    void optionsAction() {
        this.viewFactory.showOptionsWindow();
    }



    /**
     * This method should be called after we have validated that the three
     * files needed in the Options are present and valid. This method then
     * loads the HPO Ontology object and uses it to set up the Ontology Tree
     * browser on the left of the GUI.

    private void loadHpoAndSetupOntologyTree() {
        ontologyTree.addHookProperty().set(this::addPhenotypeTerm);
        ontologyTree.ontologyProperty().bind(hpOntology);


        // Setup event handlers to update HPO in case the user changes path to another one
        viewFactory.getOptions().hpJsonFileProperty().addListener((obs, old, hpJsonFilePath) -> loadHpo(hpJsonFilePath));
        // Do the actual loading..
        loadHpo(viewFactory.getOptions().getHpJsonFile());
        this.model.setOptions(viewFactory.getOptions());
    }
     */
    private void loadHpo(File hpJsonFilePath) {
        if (hpJsonFilePath != null && hpJsonFilePath.isFile()) {
            Task<MinimalOntology> hpoLoadTask = new Task<>() {
                @Override
                protected MinimalOntology call() {
                    MinimalOntology hpoOntology = OntologyLoader.loadOntology(hpJsonFilePath);
                    LOGGER.info("Loaded HPO, version {}", hpoOntology.version().orElse("n/a"));
                    return hpoOntology;
                }
            };
            hpoLoadTask.setOnSucceeded(e -> hpOntology.set(hpoLoadTask.getValue()));
            hpoLoadTask.setOnFailed(e -> {
                LOGGER.warn("Could not load HPO from {}", hpJsonFilePath.getAbsolutePath());
                hpOntology.set(null);
            });
            Thread thread = new Thread(hpoLoadTask);
            thread.start();
        } else {
            hpOntology.set(null);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOGGER.trace("Initializing MainWindowController");
        this.hostServicesOpt = this.viewFactory.getHostervicesOpt();
       // this.gitHubIssueBox.setHostServices(this.hostServicesOpt);
        //termLabelValidator.setFieldLabel("New Term Label");
        setUpStatusBar();
        setUpKeyAccelerators();
        setupStatusBarOptions();
        setUpTabPane();
        //loadHpoAndSetupOntologyTree();
       // setUpTableView();

    }

    private void setUpTabPane() {
        Tab individualTab = new IndividualTab();
        individualTab.setId("Individual");
        individualTab.setText("Individual");
        tabPane.getTabs().add(individualTab);
    }


    private void clearFields() {
     /*   this.termLabelValidator.clearFields();
        this.parentTermAdder.clearFields();
        this.definitionPane.clearFields();
        this.pmidXrefAdderBox.clearFields();
        this.addNewHpoTermBox.clearFields();

      */
    }



    private void setupStatusBarOptions() {
        viewFactory.getOptions().isReadyProperty().addListener((obs, old, novel) -> {
            if (novel) {
                statusBarTextProperty.set("input data: ready");
                statusBarLabel.setTextFill(Color.BLACK);
                statusBarLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
                if (! ontologyLoadedProperty.get()) {
                    statusBarTextProperty.set("hp.json not loaded.");
                    statusBarLabel.setTextFill(Color.RED);
                    statusBarLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
                }
            } else {
                statusBarTextProperty.set(viewFactory.getOptions().getErrorMessage());
                statusBarLabel.setTextFill(Color.RED);
                statusBarLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
            }
        });
    }

    private void setUpStatusBar() {
        statusBarTextProperty = new SimpleStringProperty("Starting");
        statusBar.setStyle("-fx-background-color: gainsboro");
        statusBar.setMinHeight(30);
        statusBar.setPadding(new Insets(10, 50, 10, 50));
        statusBar.setSpacing(10);
        statusBarLabel.textProperty().bind(statusBarTextProperty);
        statusBarLabel.setTextFill(Color.BLACK);
        statusBarLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
    }


    private void setUpKeyAccelerators() {
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.META_DOWN));
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.META_DOWN));
        optionsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.META_DOWN));
    }




    /**
     * Write the settings from the current session to file and exit.
     */
    @FXML
    private void exitGui() {
        javafx.application.Platform.exit();
    }






    public void showVersionsAction(ActionEvent actionEvent) {
        String hpo_json_version = "n/a";
        if (hpOntology != null) {
            Optional<String> opt = hpOntology.get().version();
            hpo_json_version = opt.orElse("could not retrieve version");
        }
        PopUps.alertDialog("hp.json version", String.format("hp.json: %s", hpo_json_version));
    }
}
