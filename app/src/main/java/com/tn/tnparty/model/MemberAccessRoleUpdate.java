package com.tn.tnparty.model;

/**
 * Created by raghav on 1/30/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberAccessRoleUpdate {

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("memberId")
    @Expose
    private Integer memberId;
    @SerializedName("password")
    @Expose
    private Integer password;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("userId")
    @Expose
    private Long userId;
    @SerializedName("userName")
    @Expose
    private Integer userName;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserName() {
        return userName;
    }

    public void setUserName(Integer userName) {
        this.userName = userName;
    }

}