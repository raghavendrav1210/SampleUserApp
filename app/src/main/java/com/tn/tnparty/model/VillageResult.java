package com.tn.tnparty.model;

/**
 * Created by raghav on 12/7/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VillageResult {

    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Village> result = null;
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

    public List<Village> getResult() {
        return result;
    }

    public void setResult(List<Village> result) {
        this.result = result;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
