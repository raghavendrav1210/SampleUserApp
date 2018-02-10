package com.tn.tnparty.activities.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tn.tnparty.R;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPassword, newPassword, newPasswordConfirm;
    private Button changePasswordButton;
    private ApiInterface retrofitInterface;
    private ProgressDialog pDialog = null;

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);

        initViews();
        retrofitInterface = ApiUtils.getAPIService();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        newPasswordConfirm = findViewById(R.id.newPasswordConfirm);

        changePasswordButton = findViewById(R.id.changePassword);
        changePasswordButton.setOnClickListener(v -> {
            if (v != null) {
                changePassword();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }


    private void changePassword() {
        showProgresDialog();
        retrofitInterface.changePassword(userId, currentPassword.getText().toString(), newPassword.getText().toString()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                hideProgresDialog();
                if (response.isSuccessful()) {
                    Toast.makeText(ChangePasswordActivity.this, "Password successfully changed", Toast.LENGTH_LONG).show();
                    finish();
                }
                Toast.makeText(ChangePasswordActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(ChangePasswordActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }

        pDialog = new ProgressDialog(ChangePasswordActivity.this);
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
}
