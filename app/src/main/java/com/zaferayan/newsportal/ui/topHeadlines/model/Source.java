
package com.zaferayan.newsportal.ui.topHeadlines.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Source {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public Source() {
    }

    public Source(String mId) {
        this.mId = mId;
    }

    public Source(String sourceId, String sourceName) {
        this.mId = sourceId;
        this.setName(sourceName);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
