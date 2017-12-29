package com.tn.tnparty.network;

import com.tn.tnparty.model.AssemblyResult;
import com.tn.tnparty.model.DistrictResult;
import com.tn.tnparty.model.Login;
import com.tn.tnparty.model.Member;
import com.tn.tnparty.model.PanchayathResult;
import com.tn.tnparty.model.UnionResult;
import com.tn.tnparty.model.VillageResult;
import com.tn.tnparty.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by raghav on 11/28/2017.
 */

public interface ApiInterface {
    @POST(Constants.LOGIN)
    @FormUrlEncoded
    Call<Login> login(@Field("userName") String userName, @Field("password") String password);

    @POST(Constants.MEMBER)
    @FormUrlEncoded
    Call<Member> createMember(@Field("districtId") Integer districtId,
                              @Field("assemblyId") Integer assemblyId,
                              @Field("panchayatId") Integer panchayatId,
                              @Field("unionId") Integer unionId,
                              @Field("villageId") Integer villageId,

                              @Field("name") String name,
                              @Field("father_Name") String father_Name,
                              @Field("gender") String gender,
                              @Field("address") String address,
                              @Field("phone_Number") Long phone_Number,
                              @Field("dob") String dob,
                              @Field("image") String img,

                              @Field("created") String created,
                              @Field("createdBy") String createdBy,
                              @Field("lastUpdated") String lastUpdated,
                              @Field("lastUpdatedBy") String lastUpdatedBy,

                              @Field("live") boolean live,
                              @Field("status") int status,
                              @Field("absoluteIndicator") boolean absoluteIndicator);

    @GET(Constants.DISTRICT)
    Call<DistrictResult> getDistrict();

    @GET(Constants.ASSEMBLY)
    Call<AssemblyResult> getAssembly(@Query("DistrictId") int districtId);

    @GET(Constants.UNION)
    Call<UnionResult> getUnions(@Query("DistrictId") int districtId, @Query("AssemblyId") int assemblyId);

    @GET(Constants.PANCHAYATH)
    Call<PanchayathResult> getPanchayaths(@Query("DistrictId") int districtId, @Query("AssemblyId") int assemblyId, @Query("UnionId") int unionId);

    @GET(Constants.VILLAGE)
    Call<VillageResult> getVillage(@Query("DistrictId") int districtId, @Query("AssemblyId") int assemblyId, @Query("UnionId") int unionId, @Query("PanchayatId") int panchayathId);
}