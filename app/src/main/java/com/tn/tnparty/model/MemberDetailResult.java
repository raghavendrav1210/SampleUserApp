package com.tn.tnparty.model;

/**
 * Created by raghav on 12/31/2017.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberDetailResult {

    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<MemberDetail> memberDetails = null;
    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MemberDetail> getMembersList() {
        return memberDetails;
    }

    public void setResult(List<MemberDetail> result) {
        this.memberDetails = result;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}