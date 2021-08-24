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

public class SearchResutsActivity extends AppCompatActivity {
    MainActivity main= new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String search=null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        search=getIntent().getStringExtra("search");
        GridView myGrid=(GridView)findViewById(R.id.gv);
        EditText searchInput=(EditText) findViewById(R.id.searchInput);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> uriList = new ArrayList<>();
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = getIntent();
                String search=searchInput.getText().toString();
                intent.putExtra("search",search);
                finish();
                startActivity(intent);


            }
        });
        BottomNavigationView nav=(BottomNavigationView) findViewById(R.id.nav);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Toast.makeText(SearchResutsActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(),GridActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_profile:
                        Toast.makeText(SearchResutsActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        Intent intentProfile = new Intent(getBaseContext(),User.class);
                        startActivity(intentProfile);
                        break;
                }
                return true;
            }
        });

        JSONArray myGridArray= searchTracks(search);
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



        myGrid.setAdapter(new ArrayAdapter<>(this,R.layout.cell,names));
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                String uriID=uriList.get(position);
                Intent intent = new Intent(getApplicationContext(), RemotePlayerActivity.class);
                intent.putExtra("uri", uriID);
                startActivity(intent);
            }

        });

    }

    public JSONArray searchTracks(String input){
        String response=null;
        input.replaceAll("\\s+","%20");
        JSONArray entries = null;
        try {

            response=MyGETRequest("https://api.spotify.com/v1/search?q="+input+"&type=track%2Cartist&market=US&limit=10&offset=5",main.getToken());
            System.out.println("hey asshole");
            System.out.println(response);
            JSONObject data = new JSONObject(response);
            JSONObject data2 = data.getJSONObject("artists");
            entries = data2.getJSONArray("items");
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
        return entries;

    }

}
