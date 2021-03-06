package com.tn.tnparty.activities.common;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tn.tnparty.R;
import com.tn.tnparty.activities.add_user.AddUserActivity;
import com.tn.tnparty.activities.member_access.MemberAccessForm;
import com.tn.tnparty.activities.member_list.MemberListSearchForm;
import com.tn.tnparty.activities.report.ReportActivity;
import com.tn.tnparty.activities.user_search.UserSearchActivity;
import com.tn.tnparty.model.Assembly;
import com.tn.tnparty.model.District;
import com.tn.tnparty.model.Panchayath;
import com.tn.tnparty.model.Union;
import com.tn.tnparty.model.UserDetails;
import com.tn.tnparty.model.Village;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView userNameTextView;
    private TextView userIdTextView;
    private TextView memberList;
    private TextView memberAccess;

    private int year, day, month;

    private ApiInterface retrofitInterface;
    private List<District> distrctResults = new ArrayList<>();
    private List<Assembly> assemblyResults = new ArrayList<>();
    private List<Union> unionResults = new ArrayList<>();
    private List<Panchayath> panchayatResults = new ArrayList<>();
    private List<Village> villageResults = new ArrayList<>();

    private int userId;
    private String userName;
    private int selectedDistrict;
    private int selectedAssembly;
    private int selectedUnion;
    private int selectedPanchayat;
    private int selectedVillage;

    private boolean districtSelected = false;
    private boolean assemblySelected = false;
    private boolean unionSelected = false;
    private boolean panchayathSelected = false;
    private boolean villageSelected = false;


    private ProgressDialog pDialog = null;
    private int userRole;

    private ImageView userImage;


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
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();

        MenuItem memberAccess = menu.findItem(R.id.memberAccess);
        memberAccess.setVisible(userRole < 7);//It should visible only who have level as union admin above

        userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.userName);
        userIdTextView = navigationView.getHeaderView(0).findViewById(R.id.userId);
        userImage = navigationView.getHeaderView(0).findViewById(R.id.userImage);

        FloatingActionButton addUser = (FloatingActionButton) findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddUser();
            }
        });

//        downloadUserDetails();
    }

    /*private void downloadUserDetails(){
        showProgresDialog();
        retrofitInterface.searchUserDetails(userId).enqueue(new Callback<UserDetailsResult>() {
            @Override
            public void onResponse(Call<UserDetailsResult> call, Response<UserDetailsResult> response) {
                List<UserDetails> userDetailsList = null;
                if (response.isSuccessful()) {
                    userDetailsList = response.body().getUserDetails();
                } else
                    hideProgresDialog();

                if (null != userDetailsList && !userDetailsList.isEmpty()) {
//                    userDetailsList.get(0).setRoleId(5);
                    initialData(userDetailsList.get(0));
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(HomeActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void initialData(UserDetails userDetails) {
        //show Image
//        Bitmap userImg = AppUtils.getImgFrmBase64(userDetails.get)
    }

    private void showProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }

        pDialog = new ProgressDialog(HomeActivity.this);
        pDialog.setMessage("Loading. Please wait...");
        pDialog.setCancelable(false);
        if (!isFinishing())
            pDialog.show();
    }

    private void hideProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void setInitialValues() {
        userNameTextView.setText(userName);
        String roleDesc = "";
        switch (userRole) {
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

        switch (id) {
            case R.id.memberLogout:
                doLogout();
                break;

            case R.id.memberAccess:
                navigateToMemberAccessForm();
                break;

            case R.id.memberCreate:
                navigateToAddUser();
                break;

            case R.id.memberList:
                navigateToMemberListFrom();
                break;

            case R.id.memberSearch:
                navigateToSearchUser();
                break;

            case R.id.report:
                navigateToReport();
                break;

            case R.id.changePassword:
                navigateToChangePassword();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void doLogout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void showDialog(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

            }
        });

        builder.create();

        if (!isFinishing())
            builder.show();
    }

    private void navigateToMemberAccessForm() {

        if (!AppUtils.checkNetworkConnectivity(this)) {
            showDialog("Unable to connect to internet. Please enable data connection.");
            return;
        }

        Intent i = new Intent(this, MemberAccessForm.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        startActivity(i);
    }

    private void navigateToMemberListFrom() {

        if (!AppUtils.checkNetworkConnectivity(this)) {
            showDialog("Unable to connect to internet. Please enable data connection.");
            return;
        }

        Intent i = new Intent(this, MemberListSearchForm.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        startActivity(i);
    }

    private void navigateToAddUser() {

        if (!AppUtils.checkNetworkConnectivity(this)) {
            showDialog("Unable to connect to internet. Please enable data connection.");
            return;
        }

        Intent i = new Intent(this, AddUserActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        startActivity(i);
    }

    private void navigateToSearchUser() {

        if (!AppUtils.checkNetworkConnectivity(this)) {
            showDialog("Unable to connect to internet. Please enable data connection.");
            return;
        }

        Intent i = new Intent(this, UserSearchActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        startActivity(i);
    }

    private void navigateToChangePassword() {
        Intent i = new Intent(this, ChangePasswordActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        startActivity(i);
    }

    private void navigateToReport() {
        Intent i = new Intent(this, ReportActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        startActivity(i);
    }
}
