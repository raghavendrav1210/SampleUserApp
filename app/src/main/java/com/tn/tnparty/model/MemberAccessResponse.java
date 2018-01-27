package com.tn.tnparty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PH052323 on 1/27/2018.
 */

public class MemberAccessResponse extends Response {


    @SerializedName("result")
    @Expose
    private MemberAccessEditItem memberAccessItem;

    public MemberAccessEditItem getMemberAccessItem() {
        return memberAccessItem;
    }

    public void setMemberAccessItem(MemberAccessEditItem memberAccessItem) {
        this.memberAccessItem = memberAccessItem;
    }
}



