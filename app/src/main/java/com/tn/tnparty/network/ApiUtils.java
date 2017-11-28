package com.tn.tnparty.network;

/**
 * Created by raghav on 11/28/2017.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static ApiInterface getAPIService() {

        return ApiClient.getClient().create(ApiInterface.class);
    }
}
