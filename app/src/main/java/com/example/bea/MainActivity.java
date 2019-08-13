package com.example.bea;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(getApplicationContext());

        SearchView simpleSearchView=findViewById(R.id.simpleSearchView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add new Contact button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(getDrawable(R.drawable.ic_add));

        final Intent addIntent = new Intent(this, AddContact.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Start add "client contact" activity
                startActivity(addIntent);
            }
        });

        //Display Contact cards
        final ArrayList<Contact> contacts = dbHelper.getAllData();
        final ContactsAdapter ca = new ContactsAdapter(this, contacts);
        final ListView list = findViewById(R.id.contactsList);
        list.setAdapter(ca);

        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ca.filter(newText);
                return false;
            }
        });

        simpleSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ca.clearSearch();
                return false;
            }
        });
/*
        int clearID = simpleSearchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView clearSearch = findViewById(clearID);

        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ca.clearSearch();
            }
        }); */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
