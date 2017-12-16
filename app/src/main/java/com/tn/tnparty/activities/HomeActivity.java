package com.tn.tnparty.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.tn.tnparty.R;
import com.tn.tnparty.utils.Constants;

public class HomeActivity extends AppCompatActivity implements OnClickListener {

    private FloatingActionButton addUser;
    private int userId;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addUser = (FloatingActionButton) findViewById(R.id.addUser);
        addUser.setOnClickListener(this);

        userName = getIntent().getStringExtra(Constants.CURRENT_USER);
        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(userName != null ? userName : "User Details");
        }
    }

    private void navigateToAddUser(){
        Intent i = new Intent(this, AddUserActivity.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        if(view == addUser){
            navigateToAddUser();
        }
    }
}
