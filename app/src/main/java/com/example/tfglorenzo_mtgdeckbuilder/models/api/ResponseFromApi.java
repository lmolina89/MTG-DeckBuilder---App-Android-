package com.example.tfglorenzo_mtgdeckbuilder.models.api;

import com.example.proyectoconmenu.models.singleCardInfo.SingleCardInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFromApi {

    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("total_cards")
    @Expose
    private Integer totalCards;
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    @SerializedName("data")
    @Expose
    private List<SingleCardInfo> data = null;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(Integer totalCards) {
        this.totalCards = totalCards;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<SingleCardInfo> getData() {
        return data;
    }

    public void setData(List<SingleCardInfo> data) {
        this.data = data;
    }

}
