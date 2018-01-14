package com.tn.tnparty.activities.member_list;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tn.tnparty.R;
import com.tn.tnparty.activities.AddUserActivity;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.model.MemberListResult;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.AppContext;
import com.tn.tnparty.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberSearchResultActivity extends AppCompatActivity {

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

    private ApiInterface retrofitInterface;
    private ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_search_result);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.select_user));
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
        }

        loadMemberLit();
    }

    private void loadMemberListView() {
        searchResultsView = (RecyclerView) findViewById(R.id.membersListView);
        memberSearchAdapter = new MemberSearchResultAdapter(this, membersList, new MemberSearchResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MemberList selectedItem) {
                AppContext.getInstance().add(Constants.CONTEXT_SELECTED_MEMBER, selectedItem);
                navigateToMemberEdit();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        searchResultsView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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

        retrofitInterface.gethMemberList(currentUser, selectedDistrict, selectedAssembly, selectedUnion, selectedPanchayat,selectedVillage).enqueue(new Callback<MemberListResult>() {
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

    public void navigateToMemberEdit(){

        Intent i = new Intent(this, AddUserActivity.class);
        i.putExtra(Constants.EDIT_MEMBER, true);
        i.putExtra(Constants.CURRENT_USER, currentUserName);
        i.putExtra(Constants.CURRENT_USER_ID, currentUser);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        startActivity(i);

    }
}