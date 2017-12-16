package com.tn.tnparty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by raghav on 12/3/2017.
 */

public class AssemblyResult1 {
    @SerializedName("assemblyMaster")
    @Expose
    private List<Assembly> assemblyMaster = null;

    public List<Assembly> getAssemblyMaster() {
        return assemblyMaster;
    }

    public void setAssemblyMaster(List<Assembly> assemblyMaster) {
        this.assemblyMaster = assemblyMaster;
    }
}
