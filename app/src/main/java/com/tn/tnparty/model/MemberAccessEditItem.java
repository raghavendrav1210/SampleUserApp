package com.tn.tnparty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PH052323 on 1/27/2018.
 */

public class MemberAccessEditItem {

    @SerializedName("memberEdit")
    @Expose
    private MemberDetail memberDetail;

    @SerializedName("roleList")
    @Expose
    private List<Role> roleList;

    public MemberDetail getMemberDetail() {
        return memberDetail;
    }

    public void setMemberDetail(MemberDetail memberDetail) {
        this.memberDetail = memberDetail;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
