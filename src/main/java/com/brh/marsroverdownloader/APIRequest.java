package com.brh.marsroverdownloader;

import java.time.LocalDate;
import java.util.Date;
import java.util.function.Consumer;

public class APIRequest {

       private Consumer<APIResponse> onSuccessCallback;
       private final String apiKey = "FDhYDjlm3UIpxzK5oH2XNcZui09r46v7DRn1spIY";

       public void getRoverImageByDate(LocalDate date, Consumer<APIResponse> onSuccessCallback ){
           this.onSuccessCallback = onSuccessCallback;

       }

       private void sendRequest( LocalDate date ){
           String urlString ="https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date="+ date.toString()+"&api_key="+apiKey;

       }





}
