package com.tn.tnparty.activities.member_list;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tn.tnparty.R;
import com.tn.tnparty.activities.add_user.AddUserActivity;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.model.MemberListResult;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.AppContext;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberSearchResultActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView searchResultsView;
    private MemberSearchResultAdapter memberSearchAdapter;
    private List<MemberList> membersList = null;

    private int currentUser;
    private int userRole;
    private String currentUserName;
    private int selectedDistrict;
    private int selectedAssembly;
    private int selectedUnion;
    private int selectedPanchayat;
    private int selectedVillage;

    private String selectedDistrictName;
    private String selectedAssemblyName;
    private String selectedUnionName;
    private String selectedPanchayatName;
    private String selectedVillageName;

    private ApiInterface retrofitInterface;
    private ProgressDialog pDialog = null;
    private TextView homeToolbarTitle;
    private TextView memberListHeader, memberListHeader1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_search_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        retrofitInterface = ApiUtils.getAPIService();
        initViews();
    }

    private void initViews() {

        if (getIntent().getExtras() != null) {
            userRole = getIntent().getIntExtra(Constants.CURRENT_USER_ROLEID, 0);
            currentUser = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
            currentUserName = getIntent().getStringExtra(Constants.CURRENT_USER);
            selectedDistrict = getIntent().getIntExtra(Constants.SELECTED_DISTRICT_ID, 0);
            selectedAssembly = getIntent().getIntExtra(Constants.SELECTED_ASSEMBLY_ID, 0);
            selectedUnion = getIntent().getIntExtra(Constants.SELECTED_UNION_ID, 0);
            selectedPanchayat = getIntent().getIntExtra(Constants.SELECTED_PANCHAYATH_ID, 0);
            selectedVillage = getIntent().getIntExtra(Constants.SELECTED_VILLAGE_ID, 0);

            selectedDistrictName = getIntent().getStringExtra(Constants.SELECTED_DISTRICT_NAME);
            selectedAssemblyName = getIntent().getStringExtra(Constants.SELECTED_ASSEMBLY_NAME);
            selectedUnionName = getIntent().getStringExtra(Constants.SELECTED_UNION_NAME);
            selectedPanchayatName = getIntent().getStringExtra(Constants.SELECTED_PANCHAYATH_NAME);
            selectedVillageName = getIntent().getStringExtra(Constants.SELECTED_VILLAGE_NAME);

        }

        homeToolbarTitle = (TextView) findViewById(R.id.homeToolbarTitle);
        searchResultsView = (RecyclerView) findViewById(R.id.membersListView);
        memberListHeader = findViewById(R.id.memberListHeader);
        memberListHeader1 = findViewById(R.id.memberListHeader1);

        String input = selectedDistrictName + " | " + selectedAssemblyName + " |\n" + selectedUnionName + " | " + selectedPanchayatName + " |\n" + selectedVillageName;
        memberListHeader.setText(input);
        memberListHeader.setText(selectedUnionName);
        memberListHeader1.setText(selectedVillageName);
//        homeToolbarTitle.setText(getResources().getString(R.string.select_user) + " - Logged in as " + AppUtils.getRoleDesc(userRole));

        if (!AppUtils.checkNetworkConnectivity(this)) {
            showDialog("Unable to connect to internet. Please enable data connection.");
            return;
        }

        loadMemberLit();
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

    private void loadMemberListView() {
        int size = membersList != null ? membersList.size() : 0;
        homeToolbarTitle.setText(getResources().getString(R.string.select_user) + " (" + size + ")");//- Logged in as " + AppUtils.getRoleDesc(userRole)
        memberSearchAdapter = new MemberSearchResultAdapter(this, membersList, new MemberSearchResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MemberList selectedItem) {
               /*
               //Removed as per request
               AppContext.getInstance().add(Constants.CONTEXT_SELECTED_MEMBER, selectedItem);
                navigateToMemberEdit();*/
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        searchResultsView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        searchResultsView.setLayoutManager(mLayoutManager);
        searchResultsView.setItemAnimator(new DefaultItemAnimator());
        searchResultsView.setAdapter(memberSearchAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppContext.getInstance().remove(Constants.CONTEXT_SELECTED_MEMBER);
    }

    private void loadMemberLit() {
        showProgresDialog();

        retrofitInterface.gethMemberList(currentUser, selectedDistrict, selectedAssembly, selectedUnion, selectedPanchayat, selectedVillage).enqueue(new Callback<MemberListResult>() {
            @Override
            public void onResponse(Call<MemberListResult> call, Response<MemberListResult> response) {

                if (response.isSuccessful()) {
                    membersList = new ArrayList<>();
                    if (response.body() != null) {
                        membersList = response.body().getMemberList();
                        loadMemberListView();
                        hideProgresDialog();
                    } else
                        hideProgresDialog();
                } else
                    hideProgresDialog();
            }

            @Override
            public void onFailure(Call<MemberListResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MemberSearchResultActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }

        pDialog = new ProgressDialog(MemberSearchResultActivity.this);
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

    public void navigateToMemberEdit() {

        Intent i = new Intent(this, AddUserActivity.class);
        i.putExtra(Constants.EDIT_MEMBER, true);
        i.putExtra(Constants.CURRENT_USER, currentUserName);
        i.putExtra(Constants.CURRENT_USER_ID, currentUser);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        startActivity(i);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.member_list_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        memberSearchAdapter.setFilter(membersList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<MemberList> newMemberList = filter(membersList, newText);

        memberSearchAdapter.setFilter(newMemberList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<MemberList> filter(List<MemberList> models, String query) {
        query = query.toLowerCase();
        final List<MemberList> filteredModelList = new ArrayList<>();
        for (MemberList model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}