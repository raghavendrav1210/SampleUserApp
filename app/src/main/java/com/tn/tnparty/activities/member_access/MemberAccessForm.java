package com.tn.tnparty.activities.member_access;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.tn.tnparty.R;
import com.tn.tnparty.model.Assembly;
import com.tn.tnparty.model.AssemblyResult;
import com.tn.tnparty.model.District;
import com.tn.tnparty.model.DistrictResult;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.model.MemberListResult;
import com.tn.tnparty.model.Panchayath;
import com.tn.tnparty.model.PanchayathResult;
import com.tn.tnparty.model.Union;
import com.tn.tnparty.model.UnionResult;
import com.tn.tnparty.model.UserDetails;
import com.tn.tnparty.model.UserDetailsResult;
import com.tn.tnparty.model.Village;
import com.tn.tnparty.model.VillageResult;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.AppContext;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberAccessForm extends AppCompatActivity implements View.OnClickListener {

    private Spinner district, assembly, union, panchayat, village;

    private ApiInterface retrofitInterface;
    private List<District> distrctResults = new ArrayList<>();
    private List<Assembly> assemblyResults = new ArrayList<>();
    private List<Union> unionResults = new ArrayList<>();
    private List<Panchayath> panchayatResults = new ArrayList<>();
    private List<Village> villageResults = new ArrayList<>();

    private int userId;
    private String userName;
    private int selectedDistrict;
    private int selectedAssembly;
    private int selectedUnion;
    private int selectedPanchayat;
    private int selectedVillage;

    private boolean districtSelected = false;
    private boolean assemblySelected = false;
    private boolean unionSelected = false;
    private boolean panchayathSelected = false;
    private boolean villageSelected = false;


    private ProgressDialog pDialog = null;
    private int userRole;
    private FloatingActionButton floatingActionButtonNext;
//    private TextView homeToolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_access_form);

        initViews();

        initialLoad();
    }


    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setTitle("");

//        homeToolbarTitle = (TextView) findViewById(R.id.homeToolbarTitle);
        district = (Spinner) findViewById(R.id.district);
        assembly = (Spinner) findViewById(R.id.assembly);
        union = (Spinner) findViewById(R.id.union);
        panchayat = (Spinner) findViewById(R.id.panchayat);
        village = (Spinner) findViewById(R.id.village);

        setSpinnerHeight(district);
        setSpinnerHeight(assembly);
        setSpinnerHeight(union);
        setSpinnerHeight(panchayat);
        setSpinnerHeight(village);

        floatingActionButtonNext = findViewById(R.id.next);
        floatingActionButtonNext.setOnClickListener(this);
    }

    private void initialLoad() {

        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
        userName = getIntent().getStringExtra(Constants.CURRENT_USER);
        userRole = getIntent().getIntExtra(Constants.CURRENT_USER_ROLEID, 0);

//        homeToolbarTitle.setText(getResources().getString(R.string.memberAccessLbl) + " - Logged in as " + AppUtils.getRoleDesc(userRole));

        retrofitInterface = ApiUtils.getAPIService();

        retrofitInterface.searchUserDetails(userId).enqueue(new Callback<UserDetailsResult>() {
            @Override
            public void onResponse(Call<UserDetailsResult> call, Response<UserDetailsResult> response) {
                List<UserDetails> userDetailsList = null;
                if (response.isSuccessful()) {
                    userDetailsList = response.body().getUserDetails();
                } else
                    hideProgresDialog();

                if (null != userDetailsList && !userDetailsList.isEmpty()) {
//                    userDetailsList.get(0).setRoleId(5);
                    loadData(userDetailsList.get(0));
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MemberAccessForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }

        pDialog = new ProgressDialog(MemberAccessForm.this);
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

    private void loadData(UserDetails userDetails) {

        if (null != userDetails) {

            switch (userRole) {
                case 1:
                case 2:
                    loadDistrict(true);
                    break;

                case 3: {
                    //set District load others
                    selectedDistrict = userDetails.getDistrictId();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);
                    initDistrctSpinner(false);
                    loadAssembly(true);
                }
                break;
                case 4: {
                    //set istrict load others
                    selectedDistrict = userDetails.getDistrictId();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);

                    selectedAssembly = userDetails.getAssemblyId();
                    Assembly assembly = new Assembly();
                    assembly.setAssemblyId(userDetails.getAssemblyId());
                    assembly.setAssemblyName(userDetails.getAssemblyName());
                    assemblyResults.add(assembly);
                    initDistrctSpinner(false);
                    initAsseblySpinner(false);
                    loadUnion(true);
                }
                break;
                case 5: {
                    //set istrict load others
                    selectedDistrict = userDetails.getDistrictId();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);

                    selectedAssembly = userDetails.getAssemblyId();
                    Assembly assembly = new Assembly();
                    assembly.setAssemblyId(userDetails.getAssemblyId());
                    assembly.setAssemblyName(userDetails.getAssemblyName());
                    assemblyResults.add(assembly);

                    selectedUnion = userDetails.getUnionId();
                    Union union = new Union();
                    union.setUnionId(userDetails.getUnionId());
                    union.setUnionName(userDetails.getUnionName());
                    unionResults.add(union);

                    initDistrctSpinner(false);
                    initAsseblySpinner(false);
                    initUnionSpinner(false);
                    loadPanchayath(true);
                }
                break;
                case 6: {
                    //set istrict load others
                    selectedDistrict = userDetails.getDistrictId();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);

                    selectedAssembly = userDetails.getAssemblyId();
                    Assembly assembly = new Assembly();
                    assembly.setAssemblyId(userDetails.getAssemblyId());
                    assembly.setAssemblyName(userDetails.getAssemblyName());
                    assemblyResults.add(assembly);

                    selectedUnion = userDetails.getUnionId();
                    Union union = new Union();
                    union.setUnionId(userDetails.getUnionId());
                    union.setUnionName(userDetails.getUnionName());
                    unionResults.add(union);

                    selectedPanchayat = userDetails.getPanchayatId();
                    Panchayath panchayath = new Panchayath();
                    panchayath.setPanchayatId(userDetails.getPanchayatId());
                    panchayath.setPanchayatName(userDetails.getPanchayatName());
                    panchayatResults.add(panchayath);

                    initDistrctSpinner(false);
                    initAsseblySpinner(false);
                    initUnionSpinner(false);
                    initPanchayathSpinner(false);
                    loadVillages(true);
                }
                break;
                case 7: {
                    //set istrict load others
                    selectedDistrict = userDetails.getDistrictId();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);

                    selectedAssembly = userDetails.getAssemblyId();
                    Assembly assembly = new Assembly();
                    assembly.setAssemblyId(userDetails.getAssemblyId());
                    assembly.setAssemblyName(userDetails.getAssemblyName());
                    assemblyResults.add(assembly);

                    selectedUnion = userDetails.getUnionId();
                    Union union = new Union();
                    union.setUnionId(userDetails.getUnionId());
                    union.setUnionName(userDetails.getUnionName());
                    unionResults.add(union);

                    selectedPanchayat = userDetails.getPanchayatId();
                    Panchayath panchayath = new Panchayath();
                    panchayath.setPanchayatId(userDetails.getPanchayatId());
                    panchayath.setPanchayatName(userDetails.getPanchayatName());
                    panchayatResults.add(panchayath);

                    selectedVillage = userDetails.getVillageId();
                    Village village = new Village();
                    village.setVillageId(userDetails.getVillageId());
                    village.setVillageName(userDetails.getVillageName());
                    villageResults.add(village);

                    initDistrctSpinner(false);
                    initAsseblySpinner(false);
                    initUnionSpinner(false);
                    initPanchayathSpinner(false);
                    initVillageSpinner(false);
                }
                break;

                default:
                    loadDistrict(true);
                    break;

            }
        }

    }

    private void setSpinnerHeight(Spinner spinner) {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(800);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
    }

    private void loadDistrict(boolean autoLoad) {

        retrofitInterface.getDistrict().enqueue(new Callback<DistrictResult>() {
            @Override
            public void onResponse(Call<DistrictResult> call, Response<DistrictResult> response) {

                if (response.isSuccessful()) {
                    distrctResults.clear();
                    distrctResults = response.body().getDistricts();
                } else
                    hideProgresDialog();

                initDistrctSpinner(autoLoad);
            }

            @Override
            public void onFailure(Call<DistrictResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MemberAccessForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initDistrctSpinner(boolean autoLoad) {

        district.setEnabled(autoLoad);
        final ArrayAdapter<District> districtArrayAdapter = new ArrayAdapter<District>(this, R.layout.spinner_item, distrctResults);
        district.setAdapter(districtArrayAdapter);
        districtArrayAdapter.notifyDataSetChanged();

        if (null == distrctResults || distrctResults.isEmpty()) {
            assemblyResults.clear();
            unionResults.clear();
            panchayatResults.clear();
            villageResults.clear();

            initAsseblySpinner(autoLoad);
            initUnionSpinner(autoLoad);
            initPanchayathSpinner(autoLoad);
            initVillageSpinner(autoLoad);

        }

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, districtArrayAdapter.getItem(pos).getDistrictName() + "-" + districtArrayAdapter.getItem(pos).getDistrictId(), Toast.LENGTH_SHORT).show();
                selectedDistrict = districtArrayAdapter.getItem(pos).getDistrictId();
                districtSelected = true;

                if (autoLoad) {
                    loadAssembly(autoLoad);
                    showProgresDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                districtSelected = false;
            }
        });
    }

    public void loadAssembly(boolean autoLoad) {

        assemblyResults.clear();

        retrofitInterface.getAssembly(selectedDistrict).enqueue(new Callback<AssemblyResult>() {
            @Override
            public void onResponse(Call<AssemblyResult> call, Response<AssemblyResult> response) {

                if (response.isSuccessful()) {
                    assemblyResults = new ArrayList<>();
                    assemblyResults = response.body().getResult();
//                    if (assemblyResults != null && !assemblyResults.isEmpty()) {
                    hideProgresDialog();
                }

                selectedAssembly = 0;
                selectedUnion = 0;
                selectedPanchayat = 0;
                selectedVillage = 0;
                initAsseblySpinner(autoLoad);
            }

            @Override
            public void onFailure(Call<AssemblyResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MemberAccessForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAsseblySpinner(boolean autoLoad) {

        assembly.setEnabled(autoLoad);
        final ArrayAdapter<Assembly> assemblyArrayAdapter = new ArrayAdapter<Assembly>(this, R.layout.spinner_item, assemblyResults);
        assembly.setAdapter(assemblyArrayAdapter);
        assemblyArrayAdapter.notifyDataSetChanged();

        if (null == assemblyResults || assemblyResults.isEmpty()) {
            unionResults.clear();
            panchayatResults.clear();
            villageResults.clear();

            initUnionSpinner(autoLoad);
            initPanchayathSpinner(autoLoad);
            initVillageSpinner(autoLoad);

        }


        assembly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, assemblyArrayAdapter.getItem(pos).getAssemblyName() + "-" + assemblyArrayAdapter.getItem(pos).getAssemblyId(), Toast.LENGTH_SHORT).show();
                selectedAssembly = assemblyArrayAdapter.getItem(pos).getAssemblyId();
                assemblySelected = true;

                if (autoLoad) {
                    showProgresDialog();
                    loadUnion(autoLoad);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                assemblySelected = false;
            }
        });
    }

    public void loadUnion(boolean autoLoad) {

        unionResults.clear();

        retrofitInterface.getUnions(selectedDistrict, selectedAssembly).enqueue(new Callback<UnionResult>() {
            @Override
            public void onResponse(Call<UnionResult> call, Response<UnionResult> response) {

                if (response.isSuccessful()) {
                    unionResults = new ArrayList<>();
                    if (response.body() != null) {
                        unionResults = response.body().getUnion();
//                        if (unionResults != null && !unionResults.isEmpty())
//                        else
                        hideProgresDialog();
                    } else
                        hideProgresDialog();

                } else
                    hideProgresDialog();

                selectedUnion = 0;
                selectedPanchayat = 0;
                selectedVillage = 0;

                initUnionSpinner(autoLoad);
            }

            @Override
            public void onFailure(Call<UnionResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MemberAccessForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUnionSpinner(boolean autoLoad) {

        union.setEnabled(autoLoad);
        final ArrayAdapter<Union> unionArrayAdapter = new ArrayAdapter<Union>(this, R.layout.spinner_item, unionResults);
        union.setAdapter(unionArrayAdapter);
        unionArrayAdapter.notifyDataSetChanged();

        if (null == unionResults || unionResults.isEmpty()) {
            panchayatResults.clear();
            villageResults.clear();

            initPanchayathSpinner(autoLoad);
            initVillageSpinner(autoLoad);

        }

        union.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, unionArrayAdapter.getItem(pos).getUnionId() + "-" + unionArrayAdapter.getItem(pos).getUnionName(), Toast.LENGTH_SHORT).show();
                selectedUnion = unionArrayAdapter.getItem(pos).getUnionId();
                unionSelected = true;

                selectedPanchayat = 0;
                selectedVillage = 0;
                if (autoLoad) {
                    loadPanchayath(autoLoad);
                    showProgresDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                unionSelected = false;
            }
        });
    }

    public void loadPanchayath(boolean autoLoad) {
        panchayatResults.clear();
        ;
        retrofitInterface.getPanchayaths(selectedDistrict, selectedAssembly, selectedUnion).enqueue(new Callback<PanchayathResult>() {
            @Override
            public void onResponse(Call<PanchayathResult> call, Response<PanchayathResult> response) {

                if (response.isSuccessful()) {
                    panchayatResults = new ArrayList<>();
                    if (response.body() != null) {
                        panchayatResults = response.body().getPanchayaths();
//                        if (panchayatResults != null && !panchayatResults.isEmpty())

//                        else
                        hideProgresDialog();
                    } else
                        hideProgresDialog();
                } else
                    hideProgresDialog();
                selectedPanchayat = 0;
                selectedVillage = 0;
                initPanchayathSpinner(autoLoad);
            }

            @Override
            public void onFailure(Call<PanchayathResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MemberAccessForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPanchayathSpinner(boolean autoLoad) {

        panchayat.setEnabled(autoLoad);
        final ArrayAdapter<Panchayath> panchayathArrayAdapter = new ArrayAdapter<Panchayath>(this, R.layout.spinner_item, panchayatResults);
        panchayat.setAdapter(panchayathArrayAdapter);
        panchayathArrayAdapter.notifyDataSetChanged();

        if (null == panchayatResults || panchayatResults.isEmpty()) {
            villageResults.clear();
            initVillageSpinner(autoLoad);

        }

        panchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, panchayathArrayAdapter.getItem(pos).getPanchayatId() + "-" + panchayathArrayAdapter.getItem(pos).getPanchayatName(), Toast.LENGTH_SHORT).show();
                selectedPanchayat = panchayathArrayAdapter.getItem(pos).getPanchayatId();
                panchayathSelected = true;

                if (autoLoad) {
                    showProgresDialog();
                    loadVillages(autoLoad);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                panchayathSelected = false;
            }
        });
    }

    public void loadVillages(boolean autoLoad) {
        villageResults.clear();
        retrofitInterface.getVillage(selectedDistrict, selectedAssembly, selectedUnion, selectedPanchayat).enqueue(new Callback<VillageResult>() {
            @Override
            public void onResponse(Call<VillageResult> call, Response<VillageResult> response) {

                if (response.isSuccessful()) {
                    villageResults = new ArrayList<>();
                    if (response.body() != null) {
                        villageResults = response.body().getResult();
//                        if (villageResults != null && !villageResults.isEmpty())
//                        else
                        hideProgresDialog();
                    } else
                        hideProgresDialog();
                } else
                    hideProgresDialog();

                initVillageSpinner(autoLoad);
            }

            @Override
            public void onFailure(Call<VillageResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MemberAccessForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initVillageSpinner(boolean autoLoad) {
        hideProgresDialog();
        village.setEnabled(autoLoad);
        final ArrayAdapter<Village> villageArrayAdapter = new ArrayAdapter<Village>(this, R.layout.spinner_item, villageResults);
        village.setAdapter(villageArrayAdapter);
        villageArrayAdapter.notifyDataSetChanged();
        village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(AddUserActivity.this, villageArrayAdapter.getItem(pos).getPanchayatId() + "-" + villageArrayAdapter.getItem(pos).getVillageName(), Toast.LENGTH_SHORT).show();
                selectedVillage = villageArrayAdapter.getItem(pos).getVillageId();
                villageSelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                villageSelected = false;
            }
        });

        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View view) {

        if (null != view) {
            switch (view.getId()) {
                case R.id.next:
                    getMembersAndNavigateToMembersList();
                    break;
            }
        }
    }

    private void getMembersAndNavigateToMembersList() {
        showProgresDialog();

        retrofitInterface.gethMemberList(userId, selectedDistrict, selectedAssembly, selectedUnion, selectedPanchayat, selectedVillage).enqueue(new Callback<MemberListResult>() {
            @Override
            public void onResponse(Call<MemberListResult> call, Response<MemberListResult> response) {
                hideProgresDialog();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<MemberList> membersList = response.body().getMemberList();
                        if (null != membersList && !membersList.isEmpty()) {
                            AppContext.getInstance().add(Constants.MEMBER_ACCESS_LIST, membersList);
                            navigateToMemberAccessList();
                        } else
                            Toast.makeText(MemberAccessForm.this, "No user list available at this movement, please try after sometime.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberListResult> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MemberAccessForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMemberAccessList() {
        Intent i = new Intent(this, MemberAccessList.class);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);
        startActivity(i);
    }
}
