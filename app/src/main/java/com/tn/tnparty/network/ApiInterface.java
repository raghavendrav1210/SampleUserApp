package com.tn.tnparty.network;

import com.tn.tnparty.model.Login;
import com.tn.tnparty.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by raghav on 11/28/2017.
 */

public interface ApiInterface {
    @POST(Constants.LOGIN)
    @FormUrlEncoded
    Call<Login> login(@Field("userName") String userName,
                      @Field("password") String password);
}
