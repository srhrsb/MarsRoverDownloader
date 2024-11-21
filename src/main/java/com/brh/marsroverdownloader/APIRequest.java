package com.brh.marsroverdownloader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class APIRequest {

       private Consumer< ArrayList<MarsRoverImage> > onSuccessCallback;
       private final String apiKey = "FDhYDjlm3UIpxzK5oH2XNcZui09r46v7DRn1spIY";

       public void getRoverImageByDate(LocalDate date, Consumer< ArrayList<MarsRoverImage> > onSuccessCallback ){
           this.onSuccessCallback = onSuccessCallback;
           sendRequest(date);

       }

    private void sendRequest( LocalDate date ){
        String urlString ="https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";
        urlString+=date.toString()+"&api_key="+apiKey;

        try{
            CompletableFuture<HttpResponse<String>> future;

            try( HttpClient client = HttpClient.newHttpClient() ){
                HttpRequest request = HttpRequest.newBuilder(URI.create(urlString)).build();
                future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            }

            future.thenAccept(this::handleApiResponse );
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

       private void handleApiResponse( HttpResponse<String> response ){
           if(response.statusCode()==200) {
               System.out.println(response.body());

           }
           else{
               System.err.println("API response failed");
           }
       }





}
