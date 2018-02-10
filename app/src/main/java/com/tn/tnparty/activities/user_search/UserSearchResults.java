package com.tn.tnparty.activities.user_search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tn.tnparty.R;
import com.tn.tnparty.adapter.MemberSearchAdapter;
import com.tn.tnparty.model.MemberDetail;

import java.util.List;

public class UserSearchResults extends AppCompatActivity {

    private RecyclerView searchResultsView;
    private MemberSearchAdapter memberSearchAdapter;
    private List<MemberDetail> membersList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_results);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.select_user));
        }

        initViews();
    }

    private void initViews() {

        membersList = UserSearchActivity.membersList;
        searchResultsView = (RecyclerView) findViewById(R.id.membersListView);
        memberSearchAdapter = new MemberSearchAdapter(membersList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        searchResultsView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        searchResultsView.setLayoutManager(mLayoutManager);
        searchResultsView.setItemAnimator(new DefaultItemAnimator());
        searchResultsView.setAdapter(memberSearchAdapter);
    }
}
