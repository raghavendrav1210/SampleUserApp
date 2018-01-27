package com.tn.tnparty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PH052323 on 1/27/2018.
 */

public class Role {

    @SerializedName("roleId")
    @Expose
    private String roleId;

    @SerializedName("roleName")
    @Expose
    private String roleName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.getRoleName();
    }
}
