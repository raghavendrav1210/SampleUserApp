package com.tn.tnparty.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tn.tnparty.R;
import com.tn.tnparty.model.Member;
import com.tn.tnparty.network.ApiInterface;
import com.tn.tnparty.network.ApiUtils;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdduserDetails extends AppCompatActivity implements View.OnClickListener {

    private EditText userName, fatherName, address, phone;
    private TextView dob;
    private Spinner gender;
    private FloatingActionButton addUser;
    private ImageView userPhoto;

    private int year, day, month;
    private ApiInterface retrofitInterface;

    private List<String> genderList;
    private ImageView dobIcon;

    private int currentUser;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser_details);

        retrofitInterface = ApiUtils.getAPIService();
        initViews();
    }

    private void initViews() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Member - Provide user details");
        }

        userPhoto = (ImageView) findViewById(R.id.userPhoto);
        dobIcon = (ImageView) findViewById(R.id.dobIcon);
        userName = (EditText) findViewById(R.id.userName);
        fatherName = (EditText) findViewById(R.id.fatherName);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);

        dob = (TextView) findViewById(R.id.dob);

        gender = (Spinner) findViewById(R.id.gender);

        addUser = (FloatingActionButton) findViewById(R.id.addUser);

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

    private void initGender() {

        final ArrayAdapter<String> districtArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genderList);
        gender.setAdapter(districtArrayAdapter);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(AdduserDetails.this, genderList.get(i), Toast.LENGTH_SHORT).show();
                selectedGender = districtArrayAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == dobIcon) {//view == dob ||
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AdduserDetails.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = AppUtils.checkPermission(AdduserDetails.this);
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
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

        if (userBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        }
        return null;
    }

    private void readAndSave() {
        if (doValidation())
            new CreateMemberAsyntask().execute();
    }

    private ProgressDialog pDialog = null;

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
            m.setAddress(address.getText().toString());
            m.setPhoneNumber(Long.valueOf(phone.getText().toString()));
            m.setDob(dob.getText().toString());
            m.setImage(getImageAsBase64());


            m.setCreated(AppUtils.getFormattedDate(new Date(), Constants.DATE_FORMAT));
            m.setCreatedBy(currentUser + "");

            m.setLastUpdated(AppUtils.getFormattedDate(new Date(), Constants.DATE_FORMAT));
            m.setLastUpdatedBy(currentUser + "");

            m.setLive(true);
            m.setStatus(1);
            m.setAbsoluteIndicator(false);

            retrofitInterface.createMember(m.getDistrictId(), m.getAssemblyId(), m.getUnionId(), m.getPanchayatId(), m.getVillageId(),
                    m.getName(), m.getFatherName(), m.getGender(), m.getAddress(), m.getPhoneNumber(), m.getDob(), m.getImage(),
                    m.getCreated(), m.getCreatedBy(), m.getLastUpdated(), m.getLastUpdatedBy(),
                    m.getLive(), m.getStatus(), m.getAbsoluteIndicator()).enqueue(new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(AdduserDetails.this, "Member creation Successful.", Toast.LENGTH_SHORT).show();
                        success = true;
                    }
                    showResponse(success, "Member created Successfully");
//                    publishProgress(success);
                }

                @Override
                public void onFailure(Call<Member> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
//                    publishProgress(false);
                    showResponse(false, t.getMessage());
                    Toast.makeText(AdduserDetails.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
            pDialog = new ProgressDialog(AdduserDetails.this);
            pDialog.setTitle("Please wait...");
            pDialog.setMessage("Creating member.");
            pDialog.setCancelable(false);
            if (!isFinishing())
                pDialog.show();
        }
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

        if (address.getText() == null || address.getText().toString().trim().equals("")) {
            valid = false;
            address.setError(getString(R.string.error_field_required));
        }

        if (phone.getText() == null || phone.getText().toString().trim().equals("")) {
            valid = false;
            phone.setError(getString(R.string.error_field_required));
        }

        if (dob.getText() == null || dob.getText().toString().trim().equals("")) {
            valid = false;
        }


        return valid;
    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

/*        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {


            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
//                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
//                    myBitmap = getResizedBitmap(myBitmap, 500);

                    userPhoto.setImageBitmap(myBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {


                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;
                userPhoto.setImageBitmap(myBitmap);
            }
        }*/
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.
     * <p>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
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

/*    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }*/

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

    private Bitmap userBitmap = null;

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userBitmap = thumbnail;
        ImageViewCompat.setImageTintList(userPhoto, ColorStateList.valueOf(ContextCompat.getColor(AdduserDetails.this, R.color.transparent)));
        userPhoto.setImageBitmap(thumbnail);
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
        userBitmap = bm;
        ImageViewCompat.setImageTintList(userPhoto, ColorStateList.valueOf(ContextCompat.getColor(AdduserDetails.this, R.color.transparent)));
        userPhoto.setImageBitmap(bm);
    }
}