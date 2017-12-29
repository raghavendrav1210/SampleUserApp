package com.tn.tnparty.model;

/**
 * Created by raghav on 12/7/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Village {

    @SerializedName("villageId")
    @Expose
    private Integer villageId;
    @SerializedName("villageCode")
    @Expose
    private String villageCode;
    @SerializedName("villageName")
    @Expose
    private String villageName;
    @SerializedName("panchayatId")
    @Expose
    private Integer panchayatId;
    @SerializedName("unionId")
    @Expose
    private Integer unionId;
    @SerializedName("assemblyId")
    @Expose
    private Integer assemblyId;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Integer getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(Integer panchayatId) {
        this.panchayatId = panchayatId;
    }

    public Integer getUnionId() {
        return unionId;
    }

    public void setUnionId(Integer unionId) {
        this.unionId = unionId;
    }

    public Integer getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(Integer assemblyId) {
        this.assemblyId = assemblyId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return getVillageName();
    }
}