package com.spotify.sdk.Audiobreeze;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.demo.R;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.spotify.sdk.Audiobreeze.GetAndPost.MyGETRequest;
import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;


public class MainActivity extends AppCompatActivity {

    GridView simpleGrid;
    private static String AUTH_TOKEN=null;
    public String getToken(){
        return AUTH_TOKEN;
    }

    private static final String CLIENT_ID = "ac056bb14099478899616accdfb44216";
    private static final String REDIRECT_URI = "https://sites.google.com/view/audiobreezecitycollege/home";
    private SpotifyAppRemote mSpotifyAppRemote;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);


        Button next1 = (Button) findViewById(R.id.sendURI1);

        next1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent1 = new Intent(view.getContext(), GridActivity.class);

                startActivity(myIntent1);
            }

        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    AUTH_TOKEN = response.getAccessToken();
                    System.out.println(AUTH_TOKEN);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote


                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }


    public void searchArtists(String input){
        String response=null;
        input.replaceAll("\\s+","%20");
        try {

            response=MyGETRequest("https://api.spotify.com/v1/search?q="+input+"&type=artist",AUTH_TOKEN);
            System.out.println("hey asshole");
            System.out.println(response);
            JSONObject data = new JSONObject(response);
            JSONArray entries = data.getJSONArray("artists");
            System.out.println(entries.length());
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                String name = entry.getString("name");
                String uris = entry.getString("uri");
                System.out.println(uris+"\n"+name);
            }

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }
    public void searchPlaylists(String input){
        String response=null;
        input.replaceAll("\\s+","%20");
        try {

            response=MyGETRequest("https://api.spotify.com/v1/search?q="+input+"&type=track%2Cartist&market=US&limit=10&offset=5",AUTH_TOKEN);
            System.out.println("hey asshole");
            System.out.println(response);
            JSONObject data = new JSONObject(response);
            JSONArray entries = data.getJSONArray("playlists");
            System.out.println(entries.length());
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                String name = entry.getString("name");
                String uris = entry.getString("uri");
                System.out.println(uris+"\n"+name);
            }

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }
    public void searchTracks(String input){
        String response=null;
        input.replaceAll("\\s+","%20");
        try {

            response=MyGETRequest("https://api.spotify.com/v1/search?q="+input+"&type=track%2Cartist&market=US&limit=10&offset=5",AUTH_TOKEN);
            System.out.println("hey asshole");
            System.out.println(response);
            JSONObject data = new JSONObject(response);
            JSONArray entries = data.getJSONArray("tracks");
            System.out.println(entries.length());
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                String name = entry.getString("name");
                String uris = entry.getString("uri");
                System.out.println(uris+"\n"+name);
            }

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }






    }

