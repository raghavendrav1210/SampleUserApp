package com.tn.tnparty.model;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by raghav on 12/6/2017.
 */

public class AssemblyMainResult {
    @SerializedName("districts")
    @Expose
    private Object districts;
    @SerializedName("assemblyMaster")
    @Expose
    private List<Assembly> assemblyMaster = null;
    @SerializedName("locationData")
    @Expose
    private Object locationData;

    public Object getDistricts() {
        return districts;
    }

    public void setDistricts(Object districts) {
        this.districts = districts;
    }

    public List<Assembly> getAssemblyMaster() {
        return assemblyMaster;
    }

    public void setAssemblyMaster(List<Assembly> assemblyMaster) {
        this.assemblyMaster = assemblyMaster;
    }

    public Object getLocationData() {
        return locationData;
    }

    public void setLocationData(Object locationData) {
        this.locationData = locationData;
    }

}