package com.spotify.sdk.Audiobreeze;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.android.appremote.demo.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.spotify.sdk.Audiobreeze.GetAndPost.MyGETRequest;

public class User extends AppCompatActivity {
    MainActivity main= new MainActivity();
    String name=null;
    String date=null;
    String country=null;
    String follow=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user);
        ImageView cover=(ImageView) findViewById(R.id.user_photo);

        TextView username=(TextView) findViewById(R.id.user_name);
        TextView followers=(TextView) findViewById(R.id.followers);
        TextView birthdate= (TextView) findViewById(R.id.date);
        TextView count= (TextView) findViewById(R.id.country);
        BottomNavigationView nav=(BottomNavigationView) findViewById(R.id.nav);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Toast.makeText(User.this, "Recents", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(),GridActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_profile:
                        Toast.makeText(User.this, "Favorites", Toast.LENGTH_SHORT).show();
                        Intent intentProfile = new Intent(getBaseContext(),User.class);
                        startActivity(intentProfile);
                        break;
                }
                return true;
            }
        });

        JSONObject user = getUser();
        System.out.println(user.toString());
        try {
            name= user.getString("display_name");
            System.out.println(name);
            username.append(name);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        try {
            date= user.getString("product");
            System.out.println(date);
            birthdate.append(date);
        }
        catch (JSONException e)
        {
            e.printStackTrace();

        }

        try {
            country=user.getString("country");
            System.out.println(country);
            count.setText(country);
        }
        catch (JSONException e)
        {
            e.printStackTrace();

        }

        try {

            JSONObject fol= user.getJSONObject("followers");
            follow=fol.getString("total");
            System.out.println(follow);
            followers.append(follow);
        }
        catch (JSONException e)
        {
            e.printStackTrace();

        }










        try {

            JSONArray images= user.getJSONArray("images");

            System.out.println(images.toString());
            JSONObject image= images.getJSONObject(0);
            System.out.println(image.toString());
            String imageUrl= image.getString("url");

            System.out.println(imageUrl);





            Picasso.get().load(imageUrl).fit().into(cover);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject getUser() {

        String response = null;
        JSONObject data = null;

        try {

            response = MyGETRequest("https://api.spotify.com/v1/me", main.getToken());

            data = new JSONObject(response);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
