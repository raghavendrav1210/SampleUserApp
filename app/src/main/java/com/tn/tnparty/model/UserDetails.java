package com.tn.tnparty.model;

/**
 * Created by raghav on 1/3/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {

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
    @SerializedName("roleId")
    @Expose
    private Integer roleId;

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}