package com.tn.tnparty.activities.member_list;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.tn.tnparty.R;
import com.tn.tnparty.model.Assembly;
import com.tn.tnparty.model.AssemblyResult;
import com.tn.tnparty.model.District;
import com.tn.tnparty.model.DistrictResult;
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
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberListSearchForm extends AppCompatActivity implements View.OnClickListener {

    private Spinner district, assembly, union, panchayat, village;
    private View districtView, assemblyView, unionView, panchayatView, villageView;

    private FloatingActionButton searchMembers;
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

    private String selectedDistrictName;
    private String selectedAssemblyName;
    private String selectedUnionName;
    private String selectedPanchayatName;
    private String selectedVillageName;

    private boolean districtSelected = false;
    private boolean assemblySelected = false;
    private boolean unionSelected = false;
    private boolean panchayathSelected = false;
    private boolean villageSelected = false;


    private ProgressDialog pDialog = null;
    private int userRole;
//    private TextView homeToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list_search_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        userId = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
        userName = getIntent().getStringExtra(Constants.CURRENT_USER);
        userRole = getIntent().getIntExtra(Constants.CURRENT_USER_ROLEID, 0);

        retrofitInterface = ApiUtils.getAPIService();

        initViews();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initViews() {

        district = (Spinner) findViewById(R.id.district);
        assembly = (Spinner) findViewById(R.id.assembly);
        union = (Spinner) findViewById(R.id.union);
        panchayat = (Spinner) findViewById(R.id.panchayat);
        village = (Spinner) findViewById(R.id.village);

        districtView = findViewById(R.id.districtView);
        assemblyView = findViewById(R.id.assemblyView);
        unionView = findViewById(R.id.unionView);
        panchayatView = findViewById(R.id.panchayatView);
        villageView = findViewById(R.id.villageView);

//        homeToolbarTitle = (TextView) findViewById(R.id.homeToolbarTitle);

//        homeToolbarTitle.setText(getResources().getString(R.string.membersList) + " - Logged in as " + AppUtils.getRoleDesc(userRole));

        setSpinnerHeight(district);
        setSpinnerHeight(assembly);
        setSpinnerHeight(union);
        setSpinnerHeight(panchayat);
        setSpinnerHeight(village);

        searchMembers = (FloatingActionButton) findViewById(R.id.next);
        searchMembers.setOnClickListener(this);

        searchUserDetails();
    }

    private void showProgresDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }

        pDialog = new ProgressDialog(MemberListSearchForm.this);
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

    private void searchUserDetails() {

        if (!AppUtils.checkNetworkConnectivity(this)) {
            showDialog("Unable to connect to internet. Please enable data connection.");
            return;
        }

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
                Toast.makeText(MemberListSearchForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(UserDetails userDetails) {

        if (!AppUtils.checkNetworkConnectivity(this)) {
            showDialog("Unable to connect to internet. Please enable data connection.");
            return;
        }

        if (null != userDetails) {

            switch (userRole) {
                case 1:
                case 2:
                    loadDistrict(true);
                    break;

                case 3: {
                    //set District load others
                    selectedDistrict = userDetails.getDistrictId();
                    selectedDistrictName = userDetails.getDistrictName();
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
                    selectedDistrictName = userDetails.getDistrictName();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);

                    selectedAssembly = userDetails.getAssemblyId();
                    selectedAssemblyName = userDetails.getAssemblyName();

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
                    selectedDistrictName = userDetails.getDistrictName();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);

                    selectedAssembly = userDetails.getAssemblyId();
                    selectedAssemblyName = userDetails.getAssemblyName();
                    Assembly assembly = new Assembly();
                    assembly.setAssemblyId(userDetails.getAssemblyId());
                    assembly.setAssemblyName(userDetails.getAssemblyName());
                    assemblyResults.add(assembly);

                    selectedUnion = userDetails.getUnionId();
                    selectedUnionName = userDetails.getUnionName();
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
                    selectedDistrictName = userDetails.getDistrictName();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);

                    selectedAssembly = userDetails.getAssemblyId();
                    selectedAssemblyName = userDetails.getAssemblyName();
                    Assembly assembly = new Assembly();
                    assembly.setAssemblyId(userDetails.getAssemblyId());
                    assembly.setAssemblyName(userDetails.getAssemblyName());
                    assemblyResults.add(assembly);

                    selectedUnion = userDetails.getUnionId();
                    selectedUnionName = userDetails.getUnionName();
                    Union union = new Union();
                    union.setUnionId(userDetails.getUnionId());
                    union.setUnionName(userDetails.getUnionName());
                    unionResults.add(union);

                    selectedPanchayat = userDetails.getPanchayatId();
                    selectedPanchayatName = userDetails.getPanchayatName();
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
                    selectedDistrictName = userDetails.getDistrictName();
                    District district = new District();
                    district.setDistrictId(userDetails.getDistrictId());
                    district.setDistrictName(userDetails.getDistrictName());
                    distrctResults = new ArrayList<>();
                    distrctResults.add(district);

                    selectedAssembly = userDetails.getAssemblyId();
                    selectedAssemblyName = userDetails.getAssemblyName();
                    Assembly assembly = new Assembly();
                    assembly.setAssemblyId(userDetails.getAssemblyId());
                    assembly.setAssemblyName(userDetails.getAssemblyName());
                    assemblyResults.add(assembly);

                    selectedUnion = userDetails.getUnionId();
                    selectedUnionName = userDetails.getUnionName();
                    Union union = new Union();
                    union.setUnionId(userDetails.getUnionId());
                    union.setUnionName(userDetails.getUnionName());
                    unionResults.add(union);

                    selectedPanchayat = userDetails.getPanchayatId();
                    selectedPanchayatName = userDetails.getPanchayatName();
                    Panchayath panchayath = new Panchayath();
                    panchayath.setPanchayatId(userDetails.getPanchayatId());
                    panchayath.setPanchayatName(userDetails.getPanchayatName());
                    panchayatResults.add(panchayath);

                    selectedVillage = userDetails.getVillageId();
                    selectedVillageName = userDetails.getVillageName();
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
                Toast.makeText(MemberListSearchForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

        if (!autoLoad) {
            districtView.setBackgroundColor(getResources().getColor(R.color.labelColor));
        }

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(MemberListSearchForm.this, districtArrayAdapter.getItem(pos).getDistrictName() + "-" + districtArrayAdapter.getItem(pos).getDistrictId(), Toast.LENGTH_SHORT).show();
                selectedDistrict = districtArrayAdapter.getItem(pos).getDistrictId();
                districtSelected = true;
                selectedDistrictName = districtArrayAdapter.getItem(pos).getDistrictName();

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
                Toast.makeText(MemberListSearchForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (!autoLoad) {
            assemblyView.setBackgroundColor(getResources().getColor(R.color.labelColor));
        }


        assembly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(MemberListSearchForm.this, assemblyArrayAdapter.getItem(pos).getAssemblyName() + "-" + assemblyArrayAdapter.getItem(pos).getAssemblyId(), Toast.LENGTH_SHORT).show();
                selectedAssembly = assemblyArrayAdapter.getItem(pos).getAssemblyId();
                selectedAssemblyName = assemblyArrayAdapter.getItem(pos).getAssemblyName();
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
                Toast.makeText(MemberListSearchForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

        if (!autoLoad) {
            unionView.setBackgroundColor(getResources().getColor(R.color.labelColor));
        }

        union.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(MemberListSearchForm.this, unionArrayAdapter.getItem(pos).getUnionId() + "-" + unionArrayAdapter.getItem(pos).getUnionName(), Toast.LENGTH_SHORT).show();
                selectedUnion = unionArrayAdapter.getItem(pos).getUnionId();
                selectedUnionName = unionArrayAdapter.getItem(pos).getUnionName();
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
                Toast.makeText(MemberListSearchForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

        if (!autoLoad) {
            panchayatView.setBackgroundColor(getResources().getColor(R.color.labelColor));
        }

        panchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(MemberListSearchForm.this, panchayathArrayAdapter.getItem(pos).getPanchayatId() + "-" + panchayathArrayAdapter.getItem(pos).getPanchayatName(), Toast.LENGTH_SHORT).show();
                selectedPanchayat = panchayathArrayAdapter.getItem(pos).getPanchayatId();
                selectedPanchayatName = panchayathArrayAdapter.getItem(pos).getPanchayatName();
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
                Toast.makeText(MemberListSearchForm.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initVillageSpinner(boolean autoLoad) {

        hideProgresDialog();
        village.setEnabled(autoLoad);

        if (!autoLoad) {
            villageView.setBackgroundColor(getResources().getColor(R.color.labelColor));
        }

        final ArrayAdapter<Village> villageArrayAdapter = new ArrayAdapter<Village>(this, R.layout.spinner_item, villageResults);
        village.setAdapter(villageArrayAdapter);
        villageArrayAdapter.notifyDataSetChanged();
        village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //Toast.makeText(MemberListSearchForm.this, villageArrayAdapter.getItem(pos).getPanchayatId() + "-" + villageArrayAdapter.getItem(pos).getVillageName(), Toast.LENGTH_SHORT).show();
                selectedVillage = villageArrayAdapter.getItem(pos).getVillageId();
                selectedVillageName = villageArrayAdapter.getItem(pos).getVillageName();
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

        if (view == searchMembers) {
            if (checkAlltheFieldsSelected())
                searchMember();
            else
                Toast.makeText(MemberListSearchForm.this, "Please select all the fields", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        hideProgresDialog();
        finish();
    }

    private void searchMember() {
        Intent i = new Intent(this, MemberSearchResultActivity.class);
        i.putExtra(Constants.CURRENT_USER_ID, userId);
        i.putExtra(Constants.SELECTED_DISTRICT_ID, selectedDistrict);
        i.putExtra(Constants.SELECTED_ASSEMBLY_ID, selectedAssembly);
        i.putExtra(Constants.SELECTED_UNION_ID, selectedUnion);
        i.putExtra(Constants.SELECTED_PANCHAYATH_ID, selectedPanchayat);
        i.putExtra(Constants.SELECTED_VILLAGE_ID, selectedVillage);
        i.putExtra(Constants.CURRENT_USER, userName);
        i.putExtra(Constants.CURRENT_USER_ROLEID, userRole);

        i.putExtra(Constants.SELECTED_DISTRICT_NAME, selectedDistrictName);
        i.putExtra(Constants.SELECTED_ASSEMBLY_NAME, selectedAssemblyName);
        i.putExtra(Constants.SELECTED_UNION_NAME, selectedUnionName);
        i.putExtra(Constants.SELECTED_PANCHAYATH_NAME, selectedPanchayatName);
        i.putExtra(Constants.SELECTED_VILLAGE_NAME, selectedVillageName);

        startActivity(i);
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

    private boolean checkAlltheFieldsSelected() {

        districtSelected = (district.getSelectedItem() != null ? true : false);
        assemblySelected = (assembly.getSelectedItem() != null ? true : false);
        unionSelected = (union.getSelectedItem() != null ? true : false);
        panchayathSelected = (panchayat.getSelectedItem() != null ? true : false);
        villageSelected = (village.getSelectedItem() != null ? true : false);

        return districtSelected && assemblySelected && unionSelected && panchayathSelected && villageSelected;
    }
}