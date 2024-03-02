package org.monarchinitiative.ga4ghppt.controller;


import org.monarchinitiative.ga4ghppt.view.ViewFactory;

public abstract class BaseController {

    protected final ViewFactory viewFactory;

    private final String fxmlName;

    public BaseController(ViewFactory viewFactory, String fxmlName) {
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    /**
     * We store the FXML files in the resources/org/robinsonpn/view directory
     * @return name of the FXML file to be used to create the widget
     */
    public String getFxmlName() {
        return fxmlName;
    }




}
