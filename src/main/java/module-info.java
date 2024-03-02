module org.monarchinitiative.ga4ghppt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.monarchinitiative.phenol.core;
    requires org.monarchinitiative.phenol.io;
    requires org.controlsfx.controls;
    requires org.slf4j;
    requires json.simple;
    requires java.net.http;

    opens org.monarchinitiative.ga4ghppt.view to javafx.fxml, javafx.web;
    opens org.monarchinitiative.ga4ghppt.controller to javafx.fxml, javafx.web;
    opens org.monarchinitiative.ga4ghppt.model to javafx.base;

    exports org.monarchinitiative.ga4ghppt;
   // opens org.monarchinitiative.ga4ghppt.controller.runner to javafx.base;
}