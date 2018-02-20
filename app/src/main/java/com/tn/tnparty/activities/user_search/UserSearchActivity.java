package com.tn.tnparty.activities.user_search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tn.tnparty.R;
import com.tn.tnparty.activities.member_access.MemberAccessList;
import com.tn.tnparty.model.MemberDetail;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.model.MemberListResult;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.AppContext;
import com.tn.tnparty.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText phoneNumber;
    private Button searchMember;
    private long userIdVal;
    private String userName;
    private int userRole;
    private ApiInterface retrofitInterface;

    public static List<MemberDetail> membersList = null;
    private ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        userIdVal = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
        userName = getIntent().getStringExtra(Constants.CURRENT_USER);
        userRole = getIntent().getIntExtra(Constants.CURRENT_USER_ROLEID, 0);
        retrofitInterface = ApiUtils.getAPIService();

        initViews();
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        searchMember = (Button) findViewById(R.id.searchMember);
        searchMember.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {

        if (view == searchMember) {
            if (phoneNumber.getText() == null || phoneNumber.getText().toString().trim().equals("")) {
                phoneNumber.setError(null);
                phoneNumber.setError("Please provide member name to search");
            } else {
                searchMember(Long.parseLong(phoneNumber.getText().toString()));
            }
        }
    }

    private void searchMember(long phoneNumber) {
        showProgresDialog();

        retrofitInterface.searchMembersByPhone(phoneNumber, userIdVal).enqueue(new Callback<MemberListResult>() {
            @Override
            public void onResponse(Call<MemberListResult> call, Response<MemberListResult> response) {
                hideProgresDialog();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<MemberList> membersList = response.body().getMemberList();
                        if (null != membersList && !membersList.isEmpty()) {
                            AppContext.getInstance().add(Constants.MEMBER_ACCESS_LIST, membersList);
                            navigateToMemberAccessList();
                        } else
                            Toast.makeText(UserSearchActivity.this, "No user list available at this movement, please try after sometime.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberListResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(UserSearchActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void navigateToUserSearchResults() {
        hideProgresDialog();
        Intent intent = new Intent(UserSearchActivity.this, UserSearchResults.class);
        startActivity(intent);

    }

    private void showProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }

        pDialog = new ProgressDialog(UserSearchActivity.this);
        pDialog.setMessage("Searching. Please wait...");
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

    private void navigateToMemberAccessList() {
        Intent i = new Intent(this, MemberAccessList.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, (int) userIdVal);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        i.putExtra(Constants.NAVIGATED_FRM_USER_SEARCH, Constants.NAVIGATED_FRM_USER_SEARCH);
        startActivity(i);
    }

}
