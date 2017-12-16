package com.tn.tnparty.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.tn.tnparty.R;
import com.tn.tnparty.model.Assembly;
import com.tn.tnparty.model.AssemblyMainResult;
import com.tn.tnparty.model.AssemblyResult;
import com.tn.tnparty.model.District;
import com.tn.tnparty.model.DistrictResult;
import com.tn.tnparty.model.Panchayath;
import com.tn.tnparty.model.PanchayathResult;
import com.tn.tnparty.model.Union;
import com.tn.tnparty.model.UnionResult;
import com.tn.tnparty.model.Village;
import com.tn.tnparty.model.VillageOtherResult;
import com.tn.tnparty.model.VillageResult;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {

    //    private EditText userName, fatherName, address, phone;
//    private TextView dob;
    private Spinner district, assembly, union, panchayat, village;
    private FloatingActionButton acceptDetails;
//    private ImageView userPhoto;

    private int year, day, month;

    private ApiInterface retrofitInterface;
    private List<District> distrct = null;
    private List<Assembly> assemblyResults = null;
    private List<Union> unionResults = null;
    private List<Panchayath> panchayatResults = null;
    private List<Village> villageResults = null;

    private int userId;
    private String userName;
    private int selectedDistrict;
    private int selectedAssembly;
    private int selectedUnion;
    private int selectedPanchayat;
    private int selectedVillage;

    private ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.add_user));
        }
        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
        userName = getIntent().getStringExtra(Constants.CURRENT_USER);
        retrofitInterface = ApiUtils.getAPIService();

        initViews();
    }

    private void initViews() {

        district = (Spinner) findViewById(R.id.district);
        assembly = (Spinner) findViewById(R.id.assembly);
        union = (Spinner) findViewById(R.id.union);
        panchayat = (Spinner) findViewById(R.id.panchayat);
        village = (Spinner) findViewById(R.id.village);

        acceptDetails = (FloatingActionButton) findViewById(R.id.next);
        acceptDetails.setOnClickListener(this);

        loadDistrict();
    }

    private void showProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }

        pDialog = new ProgressDialog(AddUserActivity.this);
        pDialog.setMessage("Loading. Please wait...");
        pDialog.setCancelable(false);
        if (!isFinishing())
            pDialog.show();
    }

    private void initDistrctSpinner() {

        final ArrayAdapter<District> districtArrayAdapter = new ArrayAdapter<District>(this, R.layout.spinner_item, distrct);
        district.setAdapter(districtArrayAdapter);

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, districtArrayAdapter.getItem(pos).getDistrictName() + "-" + districtArrayAdapter.getItem(pos).getDistrictId(), Toast.LENGTH_SHORT).show();
                selectedDistrict = districtArrayAdapter.getItem(pos).getDistrictId();

                showProgresDialog();
                
                loadAssembly();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadDistrict() {

        retrofitInterface.getDistrict(userId).enqueue(new Callback<DistrictResult>() {
            @Override
            public void onResponse(Call<DistrictResult> call, Response<DistrictResult> response) {

                if (response.isSuccessful()) {
                    distrct = new ArrayList<>();
                    distrct = response.body().getDistricts();
                    initDistrctSpinner();
                }
            }

            @Override
            public void onFailure(Call<DistrictResult> call, Throwable t) {
                Toast.makeText(AddUserActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadAssembly() {

        retrofitInterface.getAssembly(userId, selectedDistrict).enqueue(new Callback<AssemblyResult>() {
            @Override
            public void onResponse(Call<AssemblyResult> call, Response<AssemblyResult> response) {

                if (response.isSuccessful()) {
                    assemblyResults = new ArrayList<>();
                    AssemblyMainResult mainResp = response.body().getAssemblyMainResult();
                    if (response.body() != null && mainResp != null && mainResp.getAssemblyMaster() != null)
                        assemblyResults = mainResp.getAssemblyMaster();

                    initAsseblySpinner();
                }
            }

            @Override
            public void onFailure(Call<AssemblyResult> call, Throwable t) {
                Toast.makeText(AddUserActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAsseblySpinner() {

        final ArrayAdapter<Assembly> assemblyArrayAdapter = new ArrayAdapter<Assembly>(this, R.layout.spinner_item, assemblyResults);
        assembly.setAdapter(assemblyArrayAdapter);

        assembly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, assemblyArrayAdapter.getItem(pos).getAssemblyName() + "-" + assemblyArrayAdapter.getItem(pos).getAssemblyId(), Toast.LENGTH_SHORT).show();
                selectedAssembly = assemblyArrayAdapter.getItem(pos).getAssemblyId();

                loadUnion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void loadUnion() {

        retrofitInterface.getUnions(selectedDistrict, selectedAssembly).enqueue(new Callback<UnionResult>() {
            @Override
            public void onResponse(Call<UnionResult> call, Response<UnionResult> response) {

                if (response.isSuccessful()) {
                    unionResults = new ArrayList<>();
                    unionResults = response.body().getResult();
                    if (unionResults != null && !unionResults.isEmpty())
                        initUnionSpinner();
                }
            }

            @Override
            public void onFailure(Call<UnionResult> call, Throwable t) {
                Toast.makeText(AddUserActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUnionSpinner() {

        final ArrayAdapter<Union> unionArrayAdapter = new ArrayAdapter<Union>(this, R.layout.spinner_item, unionResults);
        union.setAdapter(unionArrayAdapter);

        union.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, unionArrayAdapter.getItem(pos).getUnionId() + "-" + unionArrayAdapter.getItem(pos).getUnionName(), Toast.LENGTH_SHORT).show();
                selectedUnion = unionArrayAdapter.getItem(pos).getAssemblyId();

                loadPanchayath();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void loadPanchayath() {

        retrofitInterface.getPanchayaths(selectedDistrict, selectedAssembly, selectedUnion).enqueue(new Callback<PanchayathResult>() {
            @Override
            public void onResponse(Call<PanchayathResult> call, Response<PanchayathResult> response) {

                if (response.isSuccessful()) {
                    panchayatResults = new ArrayList<>();
                    panchayatResults = response.body().getPanchayaths();
                    if (panchayatResults != null && !panchayatResults.isEmpty())
                        initPanchayathSpinner();
                }
            }

            @Override
            public void onFailure(Call<PanchayathResult> call, Throwable t) {
                Toast.makeText(AddUserActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPanchayathSpinner() {

        final ArrayAdapter<Panchayath> panchayathArrayAdapter = new ArrayAdapter<Panchayath>(this, R.layout.spinner_item, panchayatResults);
        panchayat.setAdapter(panchayathArrayAdapter);

        panchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, panchayathArrayAdapter.getItem(pos).getPanchayatId() + "-" + panchayathArrayAdapter.getItem(pos).getPanchayatName(), Toast.LENGTH_SHORT).show();
                selectedPanchayat = panchayathArrayAdapter.getItem(pos).getPanchayatId();
                loadVillages();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void loadVillages() {

        retrofitInterface.getVillage(userId, selectedDistrict, selectedAssembly, selectedUnion, selectedPanchayat).enqueue(new Callback<VillageResult>() {
            @Override
            public void onResponse(Call<VillageResult> call, Response<VillageResult> response) {

                if (response.isSuccessful()) {
                    villageResults = new ArrayList<>();
                    VillageOtherResult villageOtherResults = response.body().getVillageOtherResult();
                    if (villageOtherResults != null) {
                        villageResults = villageOtherResults.getVillages();
                    }
                    initVillageSpinner();
                }
            }

            @Override
            public void onFailure(Call<VillageResult> call, Throwable t) {
                Toast.makeText(AddUserActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initVillageSpinner() {

        final ArrayAdapter<Village> villageArrayAdapter = new ArrayAdapter<Village>(this, R.layout.spinner_item, villageResults);
        village.setAdapter(villageArrayAdapter);

        village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, villageArrayAdapter.getItem(pos).getPanchayatId() + "-" + villageArrayAdapter.getItem(pos).getVillageName(), Toast.LENGTH_SHORT).show();
                selectedVillage = villageArrayAdapter.getItem(pos).getVillageId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View view) {

        if (view == acceptDetails) {
            gotoDetails();
        }
        //        } else if (view == dob) {
//            showDatePicker();
//        } else
//            if (view == userPhoto) {
//            acceptPhotoAndSet();
//        }


    }

    private void gotoDetails() {
        Intent i = new Intent(this, AdduserDetails.class);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        i.putExtra(Constants.SELECTED_DISTRICT_ID, selectedDistrict);
        i.putExtra(Constants.SELECTED_ASSEMBLY_ID, selectedAssembly);
        i.putExtra(Constants.SELECTED_UNION_ID, selectedUnion);
        i.putExtra(Constants.SELECTED_PANCHAYATH_ID, selectedPanchayat);
        i.putExtra(Constants.SELECTED_VILLAGE_ID, selectedVillage);
        i.putExtra(Constants.CURRENT_USER, userName);
        startActivity(i);
    }

}