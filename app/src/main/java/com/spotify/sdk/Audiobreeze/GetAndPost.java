package com.spotify.sdk.Audiobreeze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class GetAndPost {
       public static String MyGETRequest (String url,String authToken) throws IOException, ExecutionException, InterruptedException {

           URL urlForGetRequest = new URL(url);
           String result;
           String readLine = null;
           HttpGetRequest getRequest = new HttpGetRequest();
           result = getRequest.execute(url, authToken).get();
           return result;
       }

    public static void POSTRequest(String url) throws IOException {

        final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" +

                "    \"id\": 101,\r\n" +

                "    \"title\": \"Test Title\",\r\n" +

                "    \"body\": \"Test Body\"" + "\n}";

        System.out.println(POST_PARAMS);

        URL obj = new URL("https://jsonplaceholder.typicode.com/posts");

        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();

        postConnection.setRequestMethod("POST");

        postConnection.setRequestProperty("userId", "a1bcdefgh");

        postConnection.setRequestProperty("Content-Type", "application/json");

        postConnection.setDoOutput(true);

        OutputStream os = postConnection.getOutputStream();

        os.write(POST_PARAMS.getBytes());

        os.flush();

        os.close();

        int responseCode = postConnection.getResponseCode();

        System.out.println("POST Response Code :  " + responseCode);

        System.out.println("POST Response Message : " + postConnection.getResponseMessage());

        if (responseCode == HttpURLConnection.HTTP_CREATED) { //success

            BufferedReader in = new BufferedReader(new InputStreamReader(

                    postConnection.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {

                response.append(inputLine);

            } in .close();

            // print result

            System.out.println(response.toString());

        } else {

            System.out.println("POST NOT WORKED");

        }

    }

}
