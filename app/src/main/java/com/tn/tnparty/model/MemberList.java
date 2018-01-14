package com.tn.tnparty.model;

/**
 * Created by raghav on 1/14/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberList {

    @SerializedName("memberId")
    @Expose
    private Integer memberId;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("assemblyId")
    @Expose
    private Integer assemblyId;
    @SerializedName("assemblyName")
    @Expose
    private String assemblyName;
    @SerializedName("unionId")
    @Expose
    private Integer unionId;
    @SerializedName("unionName")
    @Expose
    private String unionName;
    @SerializedName("panchayatId")
    @Expose
    private Integer panchayatId;
    @SerializedName("panchayatName")
    @Expose
    private String panchayatName;
    @SerializedName("villageId")
    @Expose
    private Integer villageId;
    @SerializedName("villageName")
    @Expose
    private String villageName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("phoneNumber")
    @Expose
    private Long phoneNumber;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageByte")
    @Expose
    private String imageByte;
    @SerializedName("memberCode")
    @Expose
    private String memberCode;
    @SerializedName("roleId")
    @Expose
    private Object roleId;
    @SerializedName("comments")
    @Expose
    private Object comments;
    @SerializedName("live")
    @Expose
    private Boolean live;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("barCode")
    @Expose
    private String barCode;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("absoluteIndicator")
    @Expose
    private Boolean absoluteIndicator;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdByName")
    @Expose
    private String createdByName;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("lastUpdatedBy")
    @Expose
    private String lastUpdatedBy;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(Integer assemblyId) {
        this.assemblyId = assemblyId;
    }

    public String getAssemblyName() {
        return assemblyName;
    }

    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
    }

    public Integer getUnionId() {
        return unionId;
    }

    public void setUnionId(Integer unionId) {
        this.unionId = unionId;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public Integer getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(Integer panchayatId) {
        this.panchayatId = panchayatId;
    }

    public String getPanchayatName() {
        return panchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageByte() {
        return imageByte;
    }

    public void setImageByte(String imageByte) {
        this.imageByte = imageByte;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Object getRoleId() {
        return roleId;
    }

    public void setRoleId(Object roleId) {
        this.roleId = roleId;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}