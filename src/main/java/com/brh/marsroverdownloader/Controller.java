package com.brh.marsroverdownloader;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void searchTargetFolder( Event ev ) {
        System.out.println( ev.getEventType() );
    }


}