package com.pachouri.albumslist.albumslistmodule.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ankit on 10/4/18.
 */
public class AlbumsResponse implements Parcelable, Comparable<AlbumsResponse> {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    protected AlbumsResponse(Parcel in) {
        userId = in.readInt();
        id = in.readInt();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlbumsResponse> CREATOR = new Creator<AlbumsResponse>() {
        @Override
        public AlbumsResponse createFromParcel(Parcel in) {
            return new AlbumsResponse(in);
        }

        @Override
        public AlbumsResponse[] newArray(int size) {
            return new AlbumsResponse[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(id);
        dest.writeString(title);
    }

    @Override
    public int compareTo(@NonNull AlbumsResponse o) {
        return title.compareTo(o.title);
    }
}
