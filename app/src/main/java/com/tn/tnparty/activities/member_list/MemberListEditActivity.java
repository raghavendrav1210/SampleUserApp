package com.tn.tnparty.activities.member_list;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adobe.creativesdk.aviary.AdobeImageIntent;
import com.tn.tnparty.R;
import com.tn.tnparty.activities.AddUserActivity;
import com.tn.tnparty.model.Assembly;
import com.tn.tnparty.model.AssemblyResult;
import com.tn.tnparty.model.District;
import com.tn.tnparty.model.DistrictResult;
import com.tn.tnparty.model.Member;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberListEditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName, fatherName, address, phone, voterId;
    private TextView dob;
    private Spinner gender;
    private Button addUser;
    private CircleImageView userPhoto;

    private int year, day, month;
    private ApiInterface retrofitInterface;

    private List<String> genderList;
    private TextView dobIcon;

    private long currentUser;
    private String currentUserName;
    private int selectedDistrict;
    private int selectedAssembly;
    private int selectedUnion;
    private int selectedPanchayat;
    private int selectedVillage;

    private String selectedGender;

    private Bitmap myBitmap;
    private Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;

    private final CharSequence[] items = {"Take Photo", "Choose from Library",
            "Cancel"};
    private String userChoosenTask = null;

    private final int SELECT_FILE = 500;
    private final int REQUEST_CAMERA = 400;
    private String imageBe64 = null;
    private String mCameraImgPath;
    private final int LOAD_IMAGE_EDIT = 210;
    private ProgressDialog pDialog = null;
    private String editedImgPath = null;
    private Bitmap userBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list_edit);

        retrofitInterface = ApiUtils.getAPIService();
        initViews();
    }

    private void initViews() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.enterUserDetails));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userPhoto = (CircleImageView) findViewById(R.id.userPhoto);
        dobIcon = (TextView) findViewById(R.id.dobIcon);
        userName = (EditText) findViewById(R.id.userName);
        fatherName = (EditText) findViewById(R.id.fatherName);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
        voterId = (EditText) findViewById(R.id.voterId);
        dob = (TextView) findViewById(R.id.dob);

        gender = (Spinner) findViewById(R.id.gender);

        addUser = (Button) findViewById(R.id.addUser);

        userPhoto.setOnClickListener(this);
        dob.setOnClickListener(this);
        dobIcon.setOnClickListener(this);
        addUser.setOnClickListener(this);

        genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");

        if (getIntent().getExtras() != null) {
            currentUser = getIntent().getIntExtra(Constants.CURRENT_USER_ID, 0);
            currentUserName = getIntent().getStringExtra(Constants.CURRENT_USER);
            selectedDistrict = getIntent().getIntExtra(Constants.SELECTED_DISTRICT_ID, 0);
            selectedAssembly = getIntent().getIntExtra(Constants.SELECTED_ASSEMBLY_ID, 0);
            selectedUnion = getIntent().getIntExtra(Constants.SELECTED_UNION_ID, 0);
            selectedPanchayat = getIntent().getIntExtra(Constants.SELECTED_PANCHAYATH_ID, 0);
            selectedVillage = getIntent().getIntExtra(Constants.SELECTED_VILLAGE_ID, 0);

        }

        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (!permissionsToRequest.isEmpty())
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        initGender();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initGender() {

        final ArrayAdapter<String> districtArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genderList);
        gender.setAdapter(districtArrayAdapter);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MemberListEditActivity.this, genderList.get(i), Toast.LENGTH_SHORT).show();
                selectedGender = districtArrayAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == dobIcon || view.getId() == R.id.dob) {//view == dob ||
            showDatePicker();
        } else if (view == addUser) {
            readAndSave();
        } else if (view == userPhoto) {
            selectImage();
//            startActivityForResult(getPickImageChooserIntent(), 200);
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MemberListEditActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = AppUtils.checkPermission(MemberListEditActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.e("mylog", "Exception while creating file: " + ex.toString());
        }
        // Continue only if the File was successfully created
        if (photoFile != null && photoFile.exists()) {
            Log.e("mylog", "Photofile not null");
            Uri photoURI = FileProvider.getUriForFile(MemberListEditActivity.this,
                    "com.tn.tnparty.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File exStorageDir = Environment.getExternalStorageDirectory();

        File storageDir = new File(exStorageDir, "MyAppImgs");

        if (!storageDir.exists() || !storageDir.isDirectory())
            storageDir.mkdir();


        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCameraImgPath = image.getAbsolutePath();
        Log.e("img path", "Path: " + mCameraImgPath);
        return image;
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, pickerListener, year, month, day).show();
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            dob.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    private String getImageAsBase64() {

        Bitmap bitmap = BitmapFactory.decodeFile(editedImgPath);
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            String encode1 =  Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

            //server expecting 2 times base64
            return Base64.encodeToString(encode1.getBytes(),Base64.DEFAULT);
        }
        return null;
    }

    private void readAndSave() {
        if (doValidation())
            new CreateMemberAsyntask().execute();
    }

    private class CreateMemberAsyntask extends AsyncTask<Void, Boolean, Boolean> {

        private boolean success = false;

        @Override
        protected Boolean doInBackground(Void... params) {
           /* try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            Member m = new Member();

            m.setDistrictId(selectedDistrict);
            m.setAssemblyId(selectedAssembly);
            m.setUnionId(selectedUnion);
            m.setPanchayatId(selectedPanchayat);
            m.setVillageId(selectedVillage);

            m.setName(userName.getText().toString());
            m.setFatherName(fatherName.getText().toString());
            m.setGender(selectedGender);
            m.setAddress(address.getText() != null ? address.getText().toString() : "");
            m.setPhoneNumber(phone.getText() != null && phone.getText().toString() != null && !phone.getText().toString().trim().equals("")? Long.valueOf(phone.getText().toString()) : Long.valueOf(0));
            m.setDob(dob.getText() != null ? dob.getText().toString() : "");
            m.setImage(getImageAsBase64());//getImageAsBase64()


            m.setCreated(AppUtils.getFormattedDate(new Date(), Constants.DATE_FORMAT));
            m.setCreatedBy(currentUser);

            m.setLastUpdated(AppUtils.getFormattedDate(new Date(), Constants.DATE_FORMAT));
            m.setLastUpdatedBy(currentUser);
            m.setUserId(currentUser);
            m.setVoterId(voterId.getText() != null ? voterId.getText().toString() : "");
            m.setIsActive(true);
            m.setLive(true);
            m.setStatus(1);
            m.setAbsoluteIndicator(false);

            retrofitInterface.createMember(m).enqueue(new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {
                    String msg = response.message();
                    if (response.isSuccessful()) {
                        msg = "Member upated Successfully";
                        Toast.makeText(MemberListEditActivity.this, "Member creation Successful.", Toast.LENGTH_SHORT).show();
                        success = true;
                        deletTempImgs();
                    }
                    showResponse(success, msg);
//                    publishProgress(success);
                }

                @Override
                public void onFailure(Call<Member> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
//                    publishProgress(false);
                    showResponse(false, t.getMessage());
                    Toast.makeText(MemberListEditActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            return success;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            // execution of result of Long time consuming operation
/*            if (pDialog != null && pDialog.isShowing())
                pDialog.dismiss();

            if (!success)
                showialog("Member creation failed. Please Try again", !success);
            else
                showialog("Member created successfully", success);*/
        }


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MemberListEditActivity.this);
            pDialog.setTitle("Please wait...");
            pDialog.setMessage("Creating member.");
            pDialog.setCancelable(false);
            if (!isFinishing())
                pDialog.show();
        }
    }

    private void deletTempImgs() {

        File file = new File(mCameraImgPath);
        if (file.exists())
            file.delete();
    }

    private void showResponse(boolean success, String msg) {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
        showialog(msg, success);
    }

    private void showialog(String msg, final boolean success) {

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

    private boolean doValidation() {

        boolean valid = true;

        if (userName.getText() == null || userName.getText().toString().trim().equals("")) {
            valid = false;
            userName.setError(getString(R.string.error_field_required));
        }

        if (fatherName.getText() == null || fatherName.getText().toString().trim().equals("")) {
            valid = false;
            fatherName.setError(getString(R.string.error_field_required));
        }

       /* if (address.getText() == null || address.getText().toString().trim().equals("")) {
            valid = false;
            address.setError(getString(R.string.error_field_required));
        }*/

        /*if (phone.getText() == null || phone.getText().toString().trim().equals("")) {
            valid = false;
            phone.setError(getString(R.string.error_field_required));
        }*/

        /*if (voterId.getText() == null || voterId.getText().toString().trim().equals("")) {
            valid = false;
            voterId.setError(getString(R.string.error_field_required));
        }*/

        /*if (dob.getText() == null || dob.getText().toString().trim().equals("")) {
            valid = false;
        }*/

        if(null == editedImgPath || editedImgPath.trim().equals("")) {
            Toast.makeText(this, "Please provide a photo", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
                loadImageCropScreen(mCameraImgPath);
            else if (requestCode == LOAD_IMAGE_EDIT) {
                if (data != null) {
                    Uri editedImageUri = data.getData();
                    setImgAfterEdit(editedImageUri.getPath());
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AppUtils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void onCaptureImageResult(Intent data) {
        loadImageCropScreen(mCameraImgPath);//destination.getAbsolutePath());
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.e("mylog", "Exception while creating file: " + ex.toString());
        }
        // Continue only if the File was successfully created
        if (photoFile != null && photoFile.exists()) {
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(photoFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadImageCropScreen(photoFile.getAbsolutePath());
    }

    private void loadImageCropScreen(String imgPath) {

        File file = new File(imgPath);

        if (!file.exists()) {

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
                .setData(Uri.parse(imgPath))
                .withOutput(file)
                .build();

        startActivityForResult(imageEditorIntent, LOAD_IMAGE_EDIT);

    }

    private void setImgAfterEdit(String uriPath) {


        File file = AppUtils.saveBitmapToFile(new File(uriPath));

        ImageViewCompat.setImageTintList(userPhoto, ColorStateList.valueOf(ContextCompat.getColor(MemberListEditActivity.this, R.color.transparent)));

        if (file.exists()) {
            editedImgPath = uriPath;
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            userBitmap = myBitmap;
            userPhoto.setImageBitmap(myBitmap);
        }
    }

    private byte[] generateByteArrFromImg() {
        File file = new File(editedImgPath);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) { // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }
}