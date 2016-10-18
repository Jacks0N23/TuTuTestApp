package com.jassdev.jackson.tututestapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jassdev.jackson.tututestapp.NawDrawScreens.AboutFragment;
import com.jassdev.jackson.tututestapp.NawDrawScreens.ScheduleFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = new ScheduleFragment();
    Bundle modelBundle = new Bundle();
    private final String DOWNLOADED_JSON = "DOWNLOADED_JSON";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Расписание");
        getFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();

    }

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            fragment = new ScheduleFragment();
            fragment.setArguments(modelBundle);
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Расписание");

        } else if (id == R.id.nav_about) {
            if (fragment != null)
                modelBundle.putParcelable(DOWNLOADED_JSON, ScheduleFragment.model);
            fragment = new AboutFragment();
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("О приложении");
        }

        getFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
