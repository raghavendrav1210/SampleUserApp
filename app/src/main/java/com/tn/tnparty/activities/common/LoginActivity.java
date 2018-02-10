package com.tn.tnparty.activities.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tn.tnparty.R;
import com.tn.tnparty.model.Login;
import com.tn.tnparty.model.LoginResult;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ImageView mProgressView;
    private View mLoginFormView;

    private ApiInterface loginInterface;
    private Integer loginUserId = null;
    private Integer loginUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginInterface = ApiUtils.getAPIService();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.userName);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Glide.with(this).load(R.raw.index_circle4).into(mProgressView);
//        Glide.with(this).asGif()
//                .load("https://loading.io/spinner/balls/-circle-slack-loading-icon")
//                .into(mProgressView);
//        Glide.with(this).asGif().load(R.drawable.index_circle1).into(mProgressView);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (!AppUtils.checkNetworkConnectivity(this)) {
            showDialog("Unable to connect to internet. Please enable data connection.");
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) { //&& !isPasswordValid(password)
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            loginToSystem(email.trim(), password);

        }
    }

    private boolean isPasswordValid(String password) {

        return password != null && !password.trim().equals("") ? true : false;
    }

    private void loginToSystem(String userName, String pwd) {

//        navigateToHome();

        loginInterface.login(userName, pwd).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                showProgress(false);
                if (response.isSuccessful()) {
                    showResponse(response);
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(LoginActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showResponse(Response<Login> response) {

        if (response != null) {
            if (response.body() != null) {
                if (response.body().getLoginResult() != null) {
                    LoginResult result = response.body().getLoginResult().get(0);
                    loginUserId = result.getUserId();
                    loginUserRole = result.getRoleId();
//                    Toast.makeText(LoginActivity.this, "Success" + response.body(), Toast.LENGTH_SHORT).show();
                    navigateToHome();
                } else {
                    Toast.makeText(LoginActivity.this, "Authentication Failed - " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

/*        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void navigateToHome() {
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra(Constants.CURRENT_USER, mEmailView.getText().toString());
        i.putExtra(Constants.CURRENT_USER_ID, loginUserId);
        i.putExtra(Constants.CURRENT_USER_ROLEID, loginUserRole);
        startActivity(i);
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
}

