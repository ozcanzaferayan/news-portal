
package com.zaferayan.newsportal.ui.topHeadlines.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
@Entity
public class Article {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("publishedAt")
    private String mPublishedAt;
    @SerializedName("source")
    @Ignore
    @TypeConverters(SourceConverter.class)
    private Source mSource;
    @SerializedName("title")
    private String mTitle;
    @PrimaryKey
    @NonNull
    @SerializedName("url")
    private String mUrl = "undefined";
    @SerializedName("urlToImage")
    private String mUrlToImage;

    public Article() {
    }

    public Article(Source mSource, String mTitle, @NonNull String mUrl, String mUrlToImage, String mPublishedAt) {
        this.mSource = mSource;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
        this.mUrlToImage = mUrlToImage;
        this.mPublishedAt = mPublishedAt;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPublishedAt() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss", Locale.US);
        try {
            Date date = format.parse(mPublishedAt);
            return sdfTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public Source getSource() {
        return mSource;
    }

    public void setSource(Source source) {
        mSource = source;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

}
