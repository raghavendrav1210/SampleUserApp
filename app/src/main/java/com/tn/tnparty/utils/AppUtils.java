package com.tn.tnparty.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by raghav on 12/7/2017.
 */

public class AppUtils {

    public static String getFormattedDate(Date date, String format) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getFormattedDateString(String inputDate, String inFormat, String reqFormat) {
        SimpleDateFormat fromUser = new SimpleDateFormat(inFormat);
        SimpleDateFormat myFormat = new SimpleDateFormat(reqFormat);

        try {

            return myFormat.format(fromUser.parse(inputDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean checkNetworkConnectivity(Context mContext) {

        ConnectivityManager ConnectionManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static Bitmap getResizedBitmapLessThan1MB(Bitmap image) {

        int maxSize = 1024 * 1024;
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
        Bitmap reduced_bitmap = Bitmap.createScaledBitmap(image, width, height, true);

        return reduced_bitmap;
    }

    public static File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap getImgFrmBase64(String base64) {

        if (base64 != null && !base64.trim().equals("")) {
            byte[] finalDecodedString = Base64.decode(new String(Base64.decode(base64, Base64.DEFAULT)), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(finalDecodedString, 0, finalDecodedString.length);
            return decodedByte;
        }
        return null;
    }

    public static String getRoleDesc(int userRole) {
        String roleDesc = "";
        switch (userRole) {
            case 1:
            case 2:
                roleDesc = "Admin";
                break;
            case 3:
                roleDesc = "District Head";
                break;
            case 4:
                roleDesc = "Assembly Head";
                break;
            case 5:
                roleDesc = "Union Head";
                break;
            case 6:
                roleDesc = "Panchyath Head";
                break;
            case 7:
                roleDesc = "Village Head";
                break;
            case 8:
                roleDesc = "Member";
                break;

            default:
                roleDesc = "";
                break;
        }

        return roleDesc;
    }

}
