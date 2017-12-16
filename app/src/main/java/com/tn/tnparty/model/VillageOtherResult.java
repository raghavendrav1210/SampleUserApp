package com.tn.tnparty.model;

/**
 * Created by raghav on 12/7/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VillageOtherResult {

    @SerializedName("districts")
    @Expose
    private Object districts;
    @SerializedName("assemblyMaster")
    @Expose
    private Object assemblyMaster;
    @SerializedName("unionMaster")
    @Expose
    private Object unionMaster;
    @SerializedName("panchayatMaster")
    @Expose
    private Object panchayatMaster;
    @SerializedName("villageMaster")
    @Expose
    private List<Village> villageMaster = null;
    @SerializedName("locationData")
    @Expose
    private Object locationData;

    public Object getDistricts() {
        return districts;
    }

    public void setDistricts(Object districts) {
        this.districts = districts;
    }

    public Object getAssemblyMaster() {
        return assemblyMaster;
    }

    public void setAssemblyMaster(Object assemblyMaster) {
        this.assemblyMaster = assemblyMaster;
    }

    public Object getUnionMaster() {
        return unionMaster;
    }

    public void setUnionMaster(Object unionMaster) {
        this.unionMaster = unionMaster;
    }

    public Object getPanchayatMaster() {
        return panchayatMaster;
    }

    public void setPanchayatMaster(Object panchayatMaster) {
        this.panchayatMaster = panchayatMaster;
    }

    public List<Village> getVillages() {
        return villageMaster;
    }

    public void setVillageMaster(List<Village> villageMaster) {
        this.villageMaster = villageMaster;
    }

    public Object getLocationData() {
        return locationData;
    }

    public void setLocationData(Object locationData) {
        this.locationData = locationData;
    }

}