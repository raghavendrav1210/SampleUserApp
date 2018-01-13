package com.tn.tnparty.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.tn.tnparty.R;
import com.tn.tnparty.activities.member_access.MemberAccessForm;
import com.tn.tnparty.activities.member_list.MemberListSearchForm;
import com.tn.tnparty.adapter.GridButtonsAdapter;
import com.tn.tnparty.utils.Constants;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String userName;
    private int userId;
    private int userRole;
    private TextView userNameTextView;
    private TextView userIdTextView;
    private TextView memberList;
    private TextView memberAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userName = getIntent().getStringExtra(Constants.CURRENT_USER);
        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
        userRole = getIntent().getIntExtra(Constants.CURRENT_USER_ROLEID, 0);
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

        /*GridView gridview = (GridView) findViewById(R.id.gridview);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView button = (TextView) view;

                //"Add User"
                if (getString(R.string.fa_user_plus).equals(button.getText())) {
                    navigateToAddUser();
                    return;
                } else if (getString(R.string.fa_search_plus).equals(button.getText())) {
                    navigateToSearchUser();
                    return;
                }
            }
        };
        gridview.setAdapter(new GridButtonsAdapter(this, onClickListener));*/

        FloatingActionButton addUser = (FloatingActionButton) findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddUser();
            }
        });

        FloatingActionButton searchUser = (FloatingActionButton) findViewById(R.id.searchUser);
        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSearchUser();
            }
        });
    }

    private void setInitialValues() {
        userNameTextView.setText(userName);
        String roleDesc = "";
        switch(userRole) {
            case 1:
            case 2:
                roleDesc = "Admin";
                break;
            case 3:
                roleDesc = "District Head";
                break;
            case 4:
                roleDesc = "Assembly Head";
                break;
            case 5:
                roleDesc = "Union Head";
                break;
            case 6:
                roleDesc = "Panchyath Head";
                break;
            case 7:
                roleDesc = "Village Head";
                break;
            case 8:
                roleDesc = "Member";
                break;
        }
        userIdTextView.setText(roleDesc);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            // Handle the camera action
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.memberList) {
            Intent intent = new Intent(this, MemberListSearchForm.class);
            startActivity(intent);
        } else if (id == R.id.accessForm) {
            Intent intent = new Intent(this, MemberAccessForm.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToAddUser() {
        Intent i = new Intent(this, AddUserActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        startActivity(i);
    }

    private void navigateToSearchUser() {
        Intent i = new Intent(this, UserSearchActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        startActivity(i);
    }
}
