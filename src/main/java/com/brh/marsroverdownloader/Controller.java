package com.brh.marsroverdownloader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Controller {
    @FXML
    private Label targetFolder;

    @FXML
    private DatePicker datePicker;

    //Table------------------------------------
    @FXML
    private TableView<MarsRoverImage> tableView;
    @FXML
    private TableColumn<MarsRoverImage, LocalDate> dateCol;
    @FXML
    private TableColumn<MarsRoverImage, String> cameraCol;
    @FXML
    private TableColumn<MarsRoverImage, String> urlCol;
    @FXML
    private TableColumn<MarsRoverImage, Integer> progressCol;

    private ObservableList<MarsRoverImage> imageList  = FXCollections.observableArrayList();

    @FXML
    private ImageView imageView;

   @FXML
    public void initialize(){
        dateCol.setCellValueFactory( new PropertyValueFactory<>("date"));
        urlCol.setCellValueFactory( new PropertyValueFactory<>("imgURL"));
        cameraCol.setCellValueFactory( new PropertyValueFactory<>("cameraName"));
        progressCol.setCellValueFactory( new PropertyValueFactory<>("downloadProgress"));
    }


    /**
     * Holt über einen Dialog den Pfad der als Ziel des Downloads dient
     * @param ev
     */
    @FXML
    protected void searchTargetFolder( Event ev ) {
        System.out.println( ev.getTarget() );

        //Stage wird geholt - weil das Elternfenster angegeben werden muss
        //um es zu blockieren
        Stage stage = (Stage) targetFolder.getScene().getWindow();

        //Dialog-Objekt
        DirectoryChooser directoryChooser = new DirectoryChooser();
        //Dialog wird angezeigt
        File folder = directoryChooser.showDialog( stage );
        //Pfad der ausgesucht wurde, wird dem Label zugewiesen
        targetFolder.setText( folder.getAbsolutePath() );
    }

    /**
     * Anfrage bei der NASA API auslösen, wird von Button ausgelöst
     */
    public void getImageFromAPI(){
        APIRequest request = new APIRequest();
        //Datum und Callback-Methode werden übergeben an APIRequest
        request.getRoverImageByDate( datePicker.getValue(), this::handleRoverImages );
    }

    public void handleRoverImages(ArrayList<MarsRoverImage> response ){
        imageList.addAll(response);
        tableView.setItems( imageList );
        tableView.refresh();
    }

    /**
     * Tabelle wurde angeklickt
     * @param event Event
     */
    public void tableClicked( Event event ){
        System.out.println(event.getTarget().toString());
       Image image = new Image( getFocusedUrl() );
       imageView.setImage(image);
    }


    @FXML
    protected void downloadSingleImage(){
        int tableIndex = getActiveIndex();
        download( tableIndex, targetFolder.getText(), this::updateBytes);
    }

    @FXML
    protected void downloadAllImages(){
        for(int i = 0; i < imageList.size(); i++){
            download( i, targetFolder.getText(), this::updateBytes);
        }
    }

    private void download( int tableIndex, String target, Action<Integer,Integer> progressCallback){
        String url = getUrlByTableIndex(tableIndex);
        new Thread( new Download( url, target, tableIndex, progressCallback ) ).start();
    }

    private String getFocusedUrl(){
        int tableRow = tableView.getFocusModel().getFocusedIndex();
        return imageList.get(tableRow).getImgURL().replace("http://", "https://");
    }

    private int getActiveIndex(){
        return tableView.getSelectionModel().getFocusedIndex();
    }

    private String getUrlByTableIndex(int index){
        return imageList.get(index).getImgURL().replace("http://", "https://");
    }

    private void updateBytes( int index, int bytes) {
        var imageItem = imageList.get(index);
        imageItem.setDownloadProgress( bytes);
        tableView.setItems( imageList );
        tableView.refresh();
    }
}