
package com.tn.tnparty.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<LoginResult> loginResult = null;
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

    public List<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(List<LoginResult> loginResult) {
        this.loginResult = loginResult;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
