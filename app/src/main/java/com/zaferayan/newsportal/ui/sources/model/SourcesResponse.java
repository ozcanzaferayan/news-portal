
package com.zaferayan.newsportal.ui.sources.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class SourcesResponse {

    @SerializedName("sources")
    private List<Source> mSources;
    @SerializedName("status")
    private String mStatus;

    public List<Source> getSources() {
        return mSources;
    }

    public void setSources(List<Source> sources) {
        mSources = sources;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
