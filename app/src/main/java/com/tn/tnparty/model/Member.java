
package com.tn.tnparty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("assemblyMaster")
    @Expose
    private Object assemblyMaster;
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
    private String address;
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
    private String image;
    @SerializedName("memberCode")
    @Expose
    private Object memberCode;
    @SerializedName("roleId")
    @Expose
    private Object roleId;
    @SerializedName("comments")
    @Expose
    private Object comments;
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
    private Long createdBy;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("lastUpdatedBy")
    @Expose
    private Long lastUpdatedBy;
    @SerializedName("userId")
    @Expose
    private Long userId;
    @SerializedName("voterId")
    @Expose
    private String voterId;


    public Object getAssemblyMaster() {
        return assemblyMaster;
    }

    public void setAssemblyMaster(Object assemblyMaster) {
        this.assemblyMaster = assemblyMaster;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Object memberCode) {
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

}
