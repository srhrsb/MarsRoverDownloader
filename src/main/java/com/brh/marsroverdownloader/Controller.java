package com.brh.marsroverdownloader;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Controller {
    @FXML
    private Label targetFolder;

    @FXML
    private DatePicker datePicker;

    @FXML
    protected void searchTargetFolder( Event ev ) {
        System.out.println( ev.getTarget() );

        Stage stage = (Stage) targetFolder.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File folder = directoryChooser.showDialog( stage );

        targetFolder.setText( folder.getAbsolutePath() );
    }

    public void getImageFromAPI(){
        APIRequest request = new APIRequest();
        request.getRoverImageByDate( datePicker.getValue(), this::handleRoverImages );
    }

    public void handleRoverImages(ArrayList<MarsRoverImage> response ){

    }

}