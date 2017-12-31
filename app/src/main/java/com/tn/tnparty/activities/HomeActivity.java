package com.tn.tnparty.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.tn.tnparty.R;
import com.tn.tnparty.adapter.GridButtonsAdapter;
import com.tn.tnparty.utils.Constants;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String userName;
    private int userId;

    private TextView userNameTextView;
    private TextView userIdTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userName = getIntent().getStringExtra(Constants.CURRENT_USER);
        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);

        initializeViews();

        setInitialValues();
    }


    private void initializeViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.userName);
        userIdTextView = navigationView.getHeaderView(0).findViewById(R.id.userId);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;

                if ("Add User".equals(button.getText())) {
                    navigateToAddUser();
                    return;
                } else if ("Search User".equals(button.getText())) {
                    navigateToSearchUser();
                    return;
                }
            }
        };
        gridview.setAdapter(new GridButtonsAdapter(this, onClickListener));
    }

    private void setInitialValues() {
        userNameTextView.setText(userName);
        userIdTextView.setText(String.valueOf(userId));
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
        getMenuInflater().inflate(R.menu.home, menu);
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

        if (id == R.id.nav_logout) {
            // Handle the camera action
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToAddUser() {
        Intent i = new Intent(this, AddUserActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        startActivity(i);
    }

    private void navigateToSearchUser() {
        Intent i = new Intent(this, UserSearchActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        startActivity(i);
    }
}
