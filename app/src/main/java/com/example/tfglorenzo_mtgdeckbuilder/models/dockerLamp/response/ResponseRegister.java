package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRegister {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("insert_id")
    @Expose
    private Integer insertId;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getInsertId() {
        return insertId;
    }

    public void setInsertId(Integer insertId) {
        this.insertId = insertId;
    }
}
