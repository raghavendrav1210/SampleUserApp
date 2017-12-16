
package com.tn.tnparty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("memberId")
    @Expose
    private Integer memberId;
    @SerializedName("loginSatus")
    @Expose
    private Boolean loginSatus;
    @SerializedName("memberCode")
    @Expose
    private String memberCode;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("assemblyId")
    @Expose
    private Integer assemblyId;
    @SerializedName("unionId")
    @Expose
    private Integer unionId;
    @SerializedName("panchayatId")
    @Expose
    private Integer panchayatId;
    @SerializedName("villageId")
    @Expose
    private Integer villageId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Boolean getLoginSatus() {
        return loginSatus;
    }

    public void setLoginSatus(Boolean loginSatus) {
        this.loginSatus = loginSatus;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(Integer assemblyId) {
        this.assemblyId = assemblyId;
    }

    public Integer getUnionId() {
        return unionId;
    }

    public void setUnionId(Integer unionId) {
        this.unionId = unionId;
    }

    public Integer getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(Integer panchayatId) {
        this.panchayatId = panchayatId;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

}
