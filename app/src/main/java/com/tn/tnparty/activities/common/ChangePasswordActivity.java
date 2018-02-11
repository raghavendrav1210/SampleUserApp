package com.tn.tnparty.activities.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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

                String newPasswordText = String.valueOf(newPassword.getText());
                String oldPassword = String.valueOf(currentPassword.getText());
                String newPasswordConfirmText = String.valueOf(newPasswordConfirm.getText());

                if (TextUtils.isEmpty(newPasswordText) || newPasswordText.trim().length() <= 0 ||
                        TextUtils.isEmpty(oldPassword) || oldPassword.trim().length() <= 0 ||
                        TextUtils.isEmpty(newPasswordConfirmText) || newPasswordConfirmText.trim().length() <= 0) {

                    Toast.makeText(ChangePasswordActivity.this, "Please enter proper values for all the fields and try again", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!newPasswordText.trim().equals(newPasswordConfirmText.trim())) {
                    Toast.makeText(ChangePasswordActivity.this, "New password and confirm password are not matching", Toast.LENGTH_LONG).show();
                    return;
                }

                changePassword(oldPassword.trim(), newPasswordText.trim());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }


    private void changePassword(String oldPassword, String newPassword) {
        showProgresDialog();
        retrofitInterface.changePassword(userId, oldPassword, newPassword).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                hideProgresDialog();
                if (response.isSuccessful()) {
                    showMessage("Password successfully changed");
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Something went wrong, try again later", Toast.LENGTH_LONG).show();
                }
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

    private void showMessage(String message) {
        new MaterialDialog.Builder(this)
                .title(R.string.okayTitle)
                .content(message)
                .positiveText(R.string.okayButtonText)
                .onPositive((@NonNull MaterialDialog dialog, @NonNull DialogAction which) -> {
                    finish();
                }).show();
    }
}
