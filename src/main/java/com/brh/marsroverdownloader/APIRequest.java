package com.brh.marsroverdownloader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class APIRequest {

       private Consumer< ArrayList<MarsRoverImage> > onSuccessCallback;
       private final String apiKey = "FDhYDjlm3UIpxzK5oH2XNcZui09r46v7DRn1spIY";

    /**
     * Initiiert Anfrage um Bilder des Marsrovers an einem bestimmten Tag abzufragen
     * @param date
     * @param onSuccessCallback
     */
       public void getRoverImageByDate(LocalDate date, Consumer< ArrayList<MarsRoverImage> > onSuccessCallback ){
           //Callback zwischenspeichern für spätere Verwendung
           this.onSuccessCallback = onSuccessCallback;
           sendRequest(date);

       }

    /**
     * Abfrage bei der NASA API wird gesendet
      * @param date
     */
    private void sendRequest( LocalDate date ){
        String urlString ="https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";
        urlString+=date.toString()+"&api_key="+apiKey;

        try{
            //dient dazu die asynchron zukünftig empfangene Antwort der API weiterzuleiten
            CompletableFuture<HttpResponse<String>> future;

            //Objekt dass den Request ausführt
            try( HttpClient client = HttpClient.newHttpClient() ){

                //Erzeugung des Client war erfolgreich -> Request wird erzeugt
                //unter Verwendung der Abfrage-URL
                HttpRequest request = HttpRequest.newBuilder(URI.create(urlString)).build();

                //Request wird asynchron gesendet -> Response wird im Future zugewiesen
                future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            }

            //wenn die Daten (Response) empfangen wurden, gibt das Future-Callback
            //diese an die Methode "handleApiResponse" weiter
            future.thenAccept(this::handleApiResponse );
        }
        catch(Exception e){
            System.err.println( "Error: "+e.getMessage() );
        }
    }

       private void handleApiResponse( HttpResponse<String> response ){

           //ist der Response erfolgreich?
           if(response.statusCode()==200) {
               System.out.println(response.body());
               var imageList = parseJson( response.body() );

               //Consumer ausgelöst durch accept -> Daten sind da
               //und werden an die Callback-Methode geschickt
               onSuccessCallback.accept( imageList );
           }
           else{
               System.err.println("API response failed");
           }
       }

       private ArrayList<MarsRoverImage> parseJson( String json ){

           ArrayList<MarsRoverImage> imageList = new ArrayList<>();

           try{
               //Daten aus dem JSON parsen
               JSONParser parse = new JSONParser();
               JSONObject jsonObject = (JSONObject)parse.parse( json );

               //Objekt aus den JSON Daten erzeugen
               JSONArray photos = (JSONArray) jsonObject.get("photos");

               //durch das "photos"-Array itterieren
               for( var item : photos){
                   //Objekt Photo
                   JSONObject photo = (JSONObject) item;
                   //Objekt Camera
                   JSONObject camera = (JSONObject) photo.get("camera");

                   //Daten holen
                   String url = photo.get("img_src").toString();
                   String camName = camera.get("full_name").toString();
                   LocalDate date = LocalDate.parse(photo.get("earth_date").toString());

                   //MarsRoverImage aus den geparsten Daten erzeugen
                   MarsRoverImage roverImage = new MarsRoverImage( date, url, camName);

                   //MarsRoverImage der List zufügen
                   imageList.add(roverImage);
               }
           }
           catch (Exception e){
               throw new RuntimeException(e);
           }
            return imageList;
       }
}
