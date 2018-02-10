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
import com.tn.tnparty.model.MemberDetail;
import com.tn.tnparty.model.MemberDetailResult;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText memberName;
    private Button searchMember;
    private long userId;
    private String userName;
    private ApiInterface retrofitInterface;

    public static List<MemberDetail> membersList = null;
    private ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
        userName = getIntent().getStringExtra(Constants.CURRENT_USER);
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
        memberName = (EditText) findViewById(R.id.memberName);
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
            if (memberName.getText() != null && !memberName.getText().toString().trim().equals("")) {
                memberName.setError(null);
                searchMember();
            } else
                memberName.setError("Please provide member name to search");
        }

    }

    private void searchMember() {
        showProgresDialog();

        retrofitInterface.searchMembers(memberName.getText().toString(), userId).enqueue(new Callback<MemberDetailResult>() {
            @Override
            public void onResponse(Call<MemberDetailResult> call, Response<MemberDetailResult> response) {

                if (response.isSuccessful())
                    membersList = response.body().getMembersList();

                hideProgresDialog();

                if (membersList != null && !membersList.isEmpty()) {
                    navigateToUserSearchResults();
                } else {
                    Toast.makeText(UserSearchActivity.this, "No members found for search criteria", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<MemberDetailResult> call, Throwable t) {
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
}
