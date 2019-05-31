package com.example.safi.muslimissues;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.safi.muslimissues.activity.SalatSettings;
import com.example.safi.muslimissues.activity.WomenSectionActivity;
import com.example.safi.muslimissues.adapter.DbAdapter;
import com.example.safi.muslimissues.fragments.CalendarFragment;
import com.example.safi.muslimissues.fragments.DuwaFragments;
import com.example.safi.muslimissues.fragments.MainFragment;
import com.example.safi.muslimissues.fragments.SalatFragment;
import com.example.safi.muslimissues.fragments.StoryFragment;
import com.example.safi.muslimissues.model.Quran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMenu();


      if (savedInstanceState==null){
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_render,new MainFragment()).commit();
          navigationView.setCheckedItem(R.id.nav_camera);
      }

    }

    public void showAppTitle(String title){
       getSupportActionBar().setTitle(title);
    }


    private void createMenu(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody=getResources().getString(R.string.shareapp);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Read latest muslim news");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share App"));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    public void showFloatingActionButton() {
        fab.show();
    };

    public void hideFloatingActionButton() {
        fab.hide();
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_render,new MainFragment())
                    .addToBackStack(null).commit();
        } else if (id == R.id.nav_slideshow) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_render,new SalatFragment()).addToBackStack(null).commit();
        }else if(id==R.id.nav_duwa){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_render,new DuwaFragments()).addToBackStack(null).commit();
        }else if (id == R.id.nav_manage) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_render,new StoryFragment()).addToBackStack(null).commit();

        }else if(id==R.id.nav_calendar){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_render,new CalendarFragment()).addToBackStack(null).commit();
        }else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SalatSettings.class));
        } else if (id == R.id.nav_send) {

        }else if (id == R.id.women_section) {
            startActivity(new Intent(this, WomenSectionActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
