package com.spotify.sdk.Audiobreeze;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.spotify.android.appremote.demo.R;

public class SearchableActivity extends ListActivity {
    String search;
    EditText searchInput;
    Button submitButton;
    MainActivity main= new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // Get the intent, verify the action and get the query
        EditText searchInput=(EditText) findViewById(R.id.searchInput);
        Button submitButton=(Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String search=searchInput.getText().toString();
                Intent myIntent = new Intent(v.getContext(), SearchResutsActivity.class);
                myIntent.putExtra("search",search);
                startActivity(myIntent);

            }
        });


    }


}
