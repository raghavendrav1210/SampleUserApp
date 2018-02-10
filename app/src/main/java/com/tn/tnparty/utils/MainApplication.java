package com.tn.tnparty.utils;

import android.app.Application;

import com.adobe.creativesdk.aviary.IAviaryClientCredentials;
import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by raghav on 12/16/2017.
 */

public class MainApplication extends  Application implements IAviaryClientCredentials {

    /* Be sure to fill in the two strings below. */
    private static final String CREATIVE_SDK_CLIENT_ID      = "1be6033e02e8403598751aa1c48f52c9";
    private static final String CREATIVE_SDK_CLIENT_SECRET  = "1c53725f-4df6-4843-a51d-b42a94b26921";
//    private static final String CREATIVE_SDK_REDIRECT_URI   = "ams+314abd8f0fa72d6c1c0e997b039dbd6f7d064362://adobeid/1be6033e02e8403598751aa1c48f52c9";
//    private static final String[] CREATIVE_SDK_SCOPES       = {"email", "profile", "address"};

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
    }

    @Override
    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

    @Override
    public String getBillingKey() {
        return null;
    }

    /*@Override
    public String[] getAdditionalScopesList() {
        return CREATIVE_SDK_SCOPES;
    }

    @Override
    public String getRedirectURI() {
        return CREATIVE_SDK_REDIRECT_URI;
    }*/
}
