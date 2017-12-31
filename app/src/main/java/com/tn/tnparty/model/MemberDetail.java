package com.tn.tnparty.model;

/**
 * Created by raghav on 12/31/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberDetail {

    @SerializedName("assemblyMaster")
    @Expose
    private Object assemblyMaster;
    @SerializedName("bloodGroup")
    @Expose
    private Object bloodGroup;
    @SerializedName("district")
    @Expose
    private Object district;
    @SerializedName("panchayatMaster")
    @Expose
    private Object panchayatMaster;
    @SerializedName("roles")
    @Expose
    private Object roles;
    @SerializedName("unionMaster")
    @Expose
    private Object unionMaster;
    @SerializedName("villageMaster")
    @Expose
    private Object villageMaster;
    @SerializedName("memberId")
    @Expose
    private Integer memberId;
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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private Object address2;
    @SerializedName("bloodGroupId")
    @Expose
    private Object bloodGroupId;
    @SerializedName("qualification")
    @Expose
    private Object qualification;
    @SerializedName("phoneNumber")
    @Expose
    private Long phoneNumber;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("comments")
    @Expose
    private Object comments;
    @SerializedName("voterId")
    @Expose
    private Object voterId;
    @SerializedName("live")
    @Expose
    private Boolean live;
    @SerializedName("barCode")
    @Expose
    private String barCode;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("absoluteIndicator")
    @Expose
    private Boolean absoluteIndicator;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("lastUpdatedBy")
    @Expose
    private Integer lastUpdatedBy;

    public Object getAssemblyMaster() {
        return assemblyMaster;
    }

    public void setAssemblyMaster(Object assemblyMaster) {
        this.assemblyMaster = assemblyMaster;
    }

    public Object getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Object bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Object getDistrict() {
        return district;
    }

    public void setDistrict(Object district) {
        this.district = district;
    }

    public Object getPanchayatMaster() {
        return panchayatMaster;
    }

    public void setPanchayatMaster(Object panchayatMaster) {
        this.panchayatMaster = panchayatMaster;
    }

    public Object getRoles() {
        return roles;
    }

    public void setRoles(Object roles) {
        this.roles = roles;
    }

    public Object getUnionMaster() {
        return unionMaster;
    }

    public void setUnionMaster(Object unionMaster) {
        this.unionMaster = unionMaster;
    }

    public Object getVillageMaster() {
        return villageMaster;
    }

    public void setVillageMaster(Object villageMaster) {
        this.villageMaster = villageMaster;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public Object getAddress2() {
        return address2;
    }

    public void setAddress2(Object address2) {
        this.address2 = address2;
    }

    public Object getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(Object bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public Object getQualification() {
        return qualification;
    }

    public void setQualification(Object qualification) {
        this.qualification = qualification;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public Object getVoterId() {
        return voterId;
    }

    public void setVoterId(Object voterId) {
        this.voterId = voterId;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getAbsoluteIndicator() {
        return absoluteIndicator;
    }

    public void setAbsoluteIndicator(Boolean absoluteIndicator) {
        this.absoluteIndicator = absoluteIndicator;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}