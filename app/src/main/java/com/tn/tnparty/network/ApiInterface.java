package com.tn.tnparty.network;

import com.tn.tnparty.model.AssemblyResult;
import com.tn.tnparty.model.DistrictResult;
import com.tn.tnparty.model.Login;
import com.tn.tnparty.model.Member;
import com.tn.tnparty.model.MemberAccessResponse;
import com.tn.tnparty.model.MemberAccessRoleUpdate;
import com.tn.tnparty.model.MemberDetail;
import com.tn.tnparty.model.MemberDetailResult;
import com.tn.tnparty.model.MemberListResult;
import com.tn.tnparty.model.PanchayathResult;
import com.tn.tnparty.model.ReportVillageMembers;
import com.tn.tnparty.model.UnionResult;
import com.tn.tnparty.model.UserDetailsResult;
import com.tn.tnparty.model.VillageResult;
import com.tn.tnparty.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<Login> login(@Field("userName") String userName, @Field("password") String password);

    @POST(Constants.MEMBER)
    Call<Member> createMember(@Body Member member);

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

    @GET(Constants.SEARCH_MEMBER)
    Call<MemberDetailResult> searchMembers(@Query("name") String memberName, @Query("UserId") long userId);

    @GET(Constants.SEARCH_MEMBER_BY_PHONE)
    Call<MemberDetailResult> searchMembersByPhoneNumber(@Query("phoneNumber") String memberName, @Query("UserId") long userId);

    @POST(Constants.SEARCH_DETAILS)
    Call<UserDetailsResult> searchUserDetails(@Query("UserId") long userId);

    @POST(Constants.MEMBER_LIST)
    Call<MemberListResult> gethMemberList(@Query("UserId") long userId, @Query("DistrictId") int districtId, @Query("AssemblyId") int assemblyId, @Query("UnionId") int unionId, @Query("PanchayatId") int panchayathId,
                                          @Query("villageId") int villageId);

    @POST(Constants.MEMBER_DETAILS)
    Call<MemberAccessResponse> getMemberDetails(@Path("memberId") int memberId, @Query("UserId") long userId);

    @POST(Constants.MEMBER_ACCESS_ROLE)
    Call<MemberAccessRoleUpdate> updateMemberRole(@Body MemberAccessRoleUpdate member);

    @POST(Constants.CHANGE_PASSWORD)
    Call<Object> changePassword(@Query("UserId") long userId, @Query("OldPassword") String oldPassword, @Query("NewPassword") String newPassword);

    @GET(Constants.REPORT_VILLAGE_MEMBERS_COUNT)
    Call<ReportVillageMembers> reportVillageMembers(@Query("UserId") long userId);
}