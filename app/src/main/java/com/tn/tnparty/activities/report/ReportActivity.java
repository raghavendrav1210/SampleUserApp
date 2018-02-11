package com.tn.tnparty.activities.report;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tn.tnparty.R;
import com.tn.tnparty.model.ReportVillageMembers;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private ApiInterface retrofitInterface;
    private int userId;
    private ReportListAdapter reportListAdapter;
    private RecyclerView reportListRecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);

        initView();
        retrofitInterface = ApiUtils.getAPIService();

        initialSetup();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        reportListRecycleview = findViewById(R.id.reportListRecycleview);
        reportListRecycleview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reportListRecycleview.setItemAnimator(new DefaultItemAnimator());
    }

    public void initialSetup() {
        loadReportData(userId);
    }

    public void loadReportData(int userId) {
        retrofitInterface.reportVillageMembers(userId).enqueue(new Callback<ReportVillageMembers>() {
            @Override
            public void onResponse(Call<ReportVillageMembers> call, Response<ReportVillageMembers> response) {
                if (response.isSuccessful()) {
                    if (reportListAdapter == null) {
                        reportListAdapter = new ReportListAdapter(response.body().getResult(), ReportActivity.this);
                        reportListRecycleview.setAdapter(reportListAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportVillageMembers> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }
}
