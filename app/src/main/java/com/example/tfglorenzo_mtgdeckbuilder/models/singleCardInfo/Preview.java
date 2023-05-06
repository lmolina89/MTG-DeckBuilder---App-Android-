package com.example.tfglorenzo_mtgdeckbuilder.models.singleCardInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Preview {

    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("source_uri")
    @Expose
    private String sourceUri;
    @SerializedName("previewed_at")
    @Expose
    private String previewedAt;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceUri() {
        return sourceUri;
    }

    public void setSourceUri(String sourceUri) {
        this.sourceUri = sourceUri;
    }

    public String getPreviewedAt() {
        return previewedAt;
    }

    public void setPreviewedAt(String previewedAt) {
        this.previewedAt = previewedAt;
    }

}
