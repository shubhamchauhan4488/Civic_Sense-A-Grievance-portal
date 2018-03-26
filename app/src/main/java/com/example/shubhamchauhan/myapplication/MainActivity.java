package com.example.shubhamchauhan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab;
    TextView txtProfileName;
    TextView txtProfileEmail;
    Bundle b = new Bundle();

    public static void startIntent(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void startIntent(Context context, Bundle bundle) {
        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionBar("Complaint Portal");

        b = getIntent().getExtras();
      //  Log.d("MainCheck",b.getString("name") );

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new CreateComplaintFragment());
        fragmentTransaction.commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        txtProfileName.setText(b.getString("name"));
        txtProfileEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
        txtProfileEmail.setText(b.getString("email"));

    }




    public void setActionBar(String heading) {
        // TODO Auto-generated method stub

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(heading);
        actionBar.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.

                        Intent parentIntent = NavUtils.getParentActivityIntent(this);
                        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(parentIntent);
                        finish();
                        return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    final FragmentManager fragmentManager = getSupportFragmentManager();
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_createComplaint) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            setActionBar("Complaint Portal");
            fragmentTransaction.replace(R.id.container, new CreateComplaintFragment());
            fab.show();
            fragmentTransaction.commit();


        } else if (id == R.id.nav_myComplaints) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fab.hide();
            setActionBar("Registered Complaints");
            fragmentTransaction.replace(R.id.container, new MyComplaintsFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_help) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fab.hide();
            setActionBar("Helplines");
            fragmentTransaction.replace(R.id.container, new HelpFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_profile) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fab.hide();
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setArguments(b);
            setActionBar("Profile");
            fragmentTransaction.replace(R.id.container, profileFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_share) {

        }        else if (id == R.id.nav_send) {

        }
        else if (id == R.id.nav_logout) {
            LoginActivity.startIntent(MainActivity.this);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
