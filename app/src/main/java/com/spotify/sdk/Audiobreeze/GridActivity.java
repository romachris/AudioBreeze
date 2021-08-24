package com.spotify.sdk.Audiobreeze;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.spotify.android.appremote.demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.spotify.sdk.Audiobreeze.GetAndPost.MyGETRequest;

public class GridActivity extends AppCompatActivity {

    MainActivity main= new MainActivity();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        EditText searchInput=(EditText) findViewById(R.id.searchInput);
        Button submitButton=(Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String search=searchInput.getText().toString();
                if(search!=null) {
                    Intent myIntent = new Intent(v.getContext(), SearchResutsActivity.class);
                    myIntent.putExtra("search", search);
                    startActivity(myIntent);
                }

            }
        });
        BottomNavigationView nav=(BottomNavigationView) findViewById(R.id.nav);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Toast.makeText(GridActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(),GridActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_profile:
                        Toast.makeText(GridActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        Intent intentProfile = new Intent(getBaseContext(),User.class);
                        startActivity(intentProfile);
                        break;
                }
                return true;
            }
        });
        //new releases
        GridView myGrid=(GridView)findViewById(R.id.gridView1);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> uriList = new ArrayList<>();
        ArrayList<Item> gridArray= new ArrayList<>();

        JSONArray myGridArray= newReleases();

        for (int i = 0; i < myGridArray.length(); i++) {
            JSONObject entry = null;
            try {
                entry = myGridArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String name = null;
            try {
                name = entry.getString("name");

                names.add(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String uris = null;
            try {
                uris = entry.getString("uri");
                uriList.add(uris);
            } catch (JSONException e) {
                e.printStackTrace();

            }

            System.out.println(uris+"\n"+name);

        }
        //reccomended
        GridView recom=(GridView)findViewById(R.id.gridView2);
        ArrayList<Item> recomArray= new ArrayList<>();
        ArrayList<String> recNames = new ArrayList<>();
        ArrayList<String> recUriList = new ArrayList<>();
        JSONArray myRecomArray= getReccomended();

        for (int i = 0; i < myRecomArray.length(); i++) {
            JSONObject entry = null;
            try {
                entry = myRecomArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String name = null;
            try {
                name = entry.getString("name");

                recNames.add(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String uris = null;
            try {
                uris = entry.getString("uri");
                recUriList.add(uris);
            } catch (JSONException e) {
                e.printStackTrace();

            }

            System.out.println(uris+"\n"+name);

        }


       myGrid.setAdapter(new ArrayAdapter<>(this,R.layout.cell,names));
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                String uriID=uriList.get(position);
                Intent intent = new Intent(getApplicationContext(), RemotePlayerActivity.class);
                intent.putExtra("uri", uriID);
                startActivity(intent);
            }

        });
        recom.setAdapter(new ArrayAdapter<>(this,R.layout.cell,recNames));
        recom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                String uriID=recUriList.get(position);
                Intent intent = new Intent(getApplicationContext(), RemotePlayerActivity.class);
                intent.putExtra("uri", uriID);
                startActivity(intent);
            }

        });

    }
    public JSONArray newReleases(){
        String response=null;
        JSONArray entries=null;
        try {

            response=MyGETRequest(" https://api.spotify.com/v1/browse/new-releases",main.getToken());
            System.out.println("hey asshole");
            System.out.println(response);
            JSONObject data = new JSONObject(response);
            JSONObject data2 = data.getJSONObject("albums");
            entries = data2.getJSONArray("items");
            System.out.println(entries.length());


        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return entries;

    }
    public JSONArray getReccomended(){
        String response=null;
        JSONArray entries=null;
        try {

            response=MyGETRequest("https://api.spotify.com/v1/browse/featured-playlists",main.getToken());
            System.out.println("hey asshole");
            System.out.println(response);
            JSONObject data = new JSONObject(response);
            JSONObject data2 = data.getJSONObject("playlists");
            entries = data2.getJSONArray("items");
            System.out.println(entries.length());


        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return entries;

    }






}
