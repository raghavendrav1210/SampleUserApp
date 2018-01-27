package com.tn.tnparty.activities.member_access;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tn.tnparty.R;
import com.tn.tnparty.model.MemberAccessResponse;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.model.Role;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.AppContext;
import com.tn.tnparty.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberAccessList extends AppCompatActivity {

    private MemberAccessListAdapter memberAccessListAdapter;
    private List<MemberList> memberList;
    private RecyclerView recyclerView;
    private ApiInterface retrofitInterface;
    private long userId;
    private Role selectedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_access_list);

        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);

        initViews();

        init();
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

        recyclerView = findViewById(R.id.membersList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void init() {
        memberList = (List) AppContext.getInstance().get(Constants.MEMBER_ACCESS_LIST);
        memberAccessListAdapter = new MemberAccessListAdapter(memberList, new MemberAccessListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showRoleChangeDialog(memberList.get(position));
            }
        });
        recyclerView.setAdapter(memberAccessListAdapter);
    }

    private void showRoleChangeDialog(MemberList memberList) {
        if (null != memberList) {
            retrofitInterface.getMemberDetails(memberList.getMemberId(), userId).enqueue(new Callback<MemberAccessResponse>() {
                @Override
                public void onResponse(Call<MemberAccessResponse> call, Response<MemberAccessResponse> response) {

                    if (response.isSuccessful()) {
                        createAndShowRoleChangeDialog(response.body());
                    }
                }

                @Override
                public void onFailure(Call<MemberAccessResponse> call, Throwable t) {

                    Toast.makeText(MemberAccessList.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void createAndShowRoleChangeDialog(MemberAccessResponse memberAccessResponse) {
        selectedRole = null;
        boolean wrapInScrollView = true;
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        List<Role> roleList = memberAccessResponse.getMemberAccessItem().getRoleList();
        final View dialogCustomView = factory.inflate(R.layout.access_form_dropdown, null);
        Spinner spinner = (Spinner) dialogCustomView.findViewById(R.id.roles);
        EditText memberCode = (EditText) dialogCustomView.findViewById(R.id.memberCode);
        memberCode.setEnabled(false);
        EditText memberName = (EditText) dialogCustomView.findViewById(R.id.name);
        memberName.setEnabled(false);
        EditText phoneNumber = (EditText) dialogCustomView.findViewById(R.id.phoneNumber);
        phoneNumber.setEnabled(false);

        //set member code
        memberCode.setText(String.valueOf(memberAccessResponse.getMemberAccessItem().getMemberDetail().getMemberCode()));
        memberName.setText(String.valueOf(memberAccessResponse.getMemberAccessItem().getMemberDetail().getName()));
        phoneNumber.setText(String.valueOf(memberAccessResponse.getMemberAccessItem().getMemberDetail().getPhoneNumber()));

        //set member name
        final ArrayAdapter<Role> roleArrayAdapter = new ArrayAdapter<Role>(this, R.layout.spinner_item, roleList);
        spinner.setAdapter(roleArrayAdapter);

        builder.title(R.string.edit_member_details)
                .customView(dialogCustomView, wrapInScrollView)
                .positiveText(R.string.savelbl).show();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRole = roleArrayAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                saveMemberRole();
            }
        });
    }

    private void saveMemberRole() {

    }
}
