package org.monarchinitiative.ga4ghppt.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.monarchinitiative.ga4ghppt.io.PubmedXmlParser;
import org.monarchinitiative.ga4ghppt.model.PubmedEntry;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.SECONDS;

public class PmidVBoxController implements Initializable {

    private static final String PUBMED_REGEX = "^PMID:\\d+";

    private static final Pattern PUBMED_PATTERN = Pattern.compile(PUBMED_REGEX);
    
    
    private final Map<String, PubmedEntry> pubmedMap;


    @FXML
    private Button retrievePmid;

    @FXML
    private TextField pmidTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ChoiceBox<String> pmidCB;
    @FXML
    private VBox pmidVBox;

    private final BooleanProperty isValidProperty = new SimpleBooleanProperty();
    
    public PmidVBoxController() {
        pubmedMap = new TreeMap<>();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // remove stray white space
        pmidTextField.textProperty().addListener( // ChangeListener
                (observable, oldValue, newValue) -> {
                    String txt = pmidTextField.getText();
                    txt = txt.replaceAll("\\s", "");
                    pmidTextField.setText(txt);
                    Matcher matcher = PUBMED_PATTERN.matcher(newValue);
                    isValidProperty.set(matcher.matches());
                    if (! isValidProperty.get()) {
                        setInvalid("invalid PMID");
                    } else {
                        setValid();
                    }
                });

        retrievePmid.setOnAction(e -> {
            if (! isValidProperty.get()) {
                setInvalid("Cannot retrieve invalid PMID");
                return;
            }
            String pmid = pmidTextField.getText();
            retrievePmid(pmid);
        });
    }

    private void retrievePmid(String pmid)  {
        String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=" + pmid + "&retmode=xml";


        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();
            var client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            PubmedXmlParser parser = new PubmedXmlParser(responseBody);
            Optional<PubmedEntry> opt = parser.getPubmedEntry();
            if (opt.isPresent()) {
                PubmedEntry entry = opt.get();
                pubmedMap.putIfAbsent(entry.pmid(), entry);
                updatePulldownMenu(entry.pmid());
            } else {
                setInvalid(String.format("Could not retrieve PubMed data for %s", pmid));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePulldownMenu(String pmid) {
        pmidCB.getItems().clear();
        pmidCB.getItems().addAll(pubmedMap.keySet());
        System.out.println("UPDATE" + pmid);
        //pmidCB.set
    }

    private void setInvalid(String message) {
        isValidProperty.set(false);
        errorLabel.setText(message);
        pmidTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red ;");
    }

    private void setValid() {
        isValidProperty.set(true);
        errorLabel.setText("");
        pmidTextField.setStyle("-fx-text-box-border: green; -fx-focus-color: green ;");
    }

    public List<PubmedEntry> getPubmedEntries() {
        return new ArrayList<>(pubmedMap.values());
    }
}
