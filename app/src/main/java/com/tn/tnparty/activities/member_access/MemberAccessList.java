package com.tn.tnparty.activities.member_access;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import com.tn.tnparty.model.MemberAccessRoleUpdate;
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
    private ProgressDialog pDialog = null;
    private int userRole;
//    private TextView homeToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_access_list);

        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
        userRole = getIntent().getIntExtra(Constants.CURRENT_USER_ROLEID, 0);
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

        /*homeToolbarTitle = (TextView) findViewById(R.id.homeToolbarTitle);

        homeToolbarTitle.setText(getResources().getString(R.string.membersList) + " - Logged in as " + AppUtils.getRoleDesc(userRole));*/

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
                showProgresDialog();
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
                    } else
                        hideProgresDialog();
                }

                @Override
                public void onFailure(Call<MemberAccessResponse> call, Throwable t) {
                    hideProgresDialog();
                    Toast.makeText(MemberAccessList.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }

        pDialog = new ProgressDialog(MemberAccessList.this);
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

    public static void selectSpinnerItemByValue(Spinner spnr, int value) {
        ArrayAdapter adapter = (ArrayAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItem(position) != null) {
                Role role = (Role) adapter.getItem(position);
                String roleId = role.getRoleId();
                if(roleId !=null && Integer.parseInt(roleId) == value) {
                    spnr.setSelection(position);
                    return;
                }
            }
        }
    }

    private void createAndShowRoleChangeDialog(final MemberAccessResponse memberAccessResponse) {

        hideProgresDialog();
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

        int currentRoleId = memberAccessResponse.getMemberAccessItem().getMemberDetail().getRoleId();
        selectSpinnerItemByValue(spinner, currentRoleId);

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
                saveMemberRole(memberAccessResponse);
            }
        });
    }

    private void saveMemberRole(MemberAccessResponse memberAccessResponse) {
        new CreateMemberAsyntask(memberAccessResponse).execute();
    }

    private class CreateMemberAsyntask extends AsyncTask<Void, Boolean, Boolean> {

        private boolean success = false;
        private MemberAccessResponse memberAccessResponse;

        public CreateMemberAsyntask(MemberAccessResponse memberAccessResponse){
            this.memberAccessResponse = memberAccessResponse;
        }


        @Override
        protected Boolean doInBackground(Void... params) {

            MemberAccessRoleUpdate m = new MemberAccessRoleUpdate();
            m.setIsActive(memberAccessResponse.getMemberAccessItem().getMemberDetail().getIsActive());
            m.setMemberId(memberAccessResponse.getMemberAccessItem().getMemberDetail().getMemberId());
            m.setStatus(Integer.parseInt(selectedRole.getRoleId()));
            m.setUserName(memberAccessResponse.getMemberAccessItem().getMemberDetail().getPhoneNumber().intValue());
            m.setUserId(userId);
            m.setPassword(memberAccessResponse.getMemberAccessItem().getMemberDetail().getPhoneNumber().intValue());


            retrofitInterface.updateMemberRole(m).enqueue(new Callback<MemberAccessRoleUpdate>() {
                @Override
                public void onResponse(Call<MemberAccessRoleUpdate> call, Response<MemberAccessRoleUpdate> response) {
                    String msg = response.message();
                    if (response.isSuccessful()) {
                        success = true;
                        showResponse(success, "Member Role updated");
                    }
                }

                @Override
                public void onFailure(Call<MemberAccessRoleUpdate> call, Throwable t) {
                    success = false;
                    showResponse(false, "Unable to update Member Role" + t.getMessage());
                    Toast.makeText(MemberAccessList.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            return success;
        }


        @Override
        protected void onPostExecute(Boolean success) {
        }


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MemberAccessList.this);
            pDialog.setTitle("Please wait...");
            pDialog.setMessage("Updating Member Role...");
            pDialog.setCancelable(false);
            if (!isFinishing())
                pDialog.show();
        }
    }

    private void showResponse(boolean success, String msg) {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
        showDialog(msg, success);
    }

    private void showDialog(String msg, final boolean success) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (success)
            dialogBuilder.setTitle("Success");
        else
            dialogBuilder.setTitle("Error");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (success)
                    finish();
            }
        });

        if (!isFinishing())
            dialogBuilder.show();
    }
}
