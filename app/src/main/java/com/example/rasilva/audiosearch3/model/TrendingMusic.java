package com.example.rasilva.audiosearch3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TrendingMusic implements Parcelable {
    private String strArtist;
    private String strAlbum;
    private String strTrack;
    private String strTrackThumb;
    private String intChartPlace;

    public TrendingMusic() {

    }

    public TrendingMusic(String strArtist, String strTrack, String intChartPlace) {
        this.strArtist = strArtist;
        this.strTrack = strTrack;
        this.intChartPlace = intChartPlace;
    }

    protected TrendingMusic(Parcel in) {
        strArtist = in.readString();
        strAlbum = in.readString();
        strTrack = in.readString();
        strTrackThumb = in.readString();
        intChartPlace = in.readString();
    }

    public static final Creator<TrendingMusic> CREATOR = new Creator<TrendingMusic>() {
        @Override
        public TrendingMusic createFromParcel(Parcel in) {
            return new TrendingMusic(in);
        }

        @Override
        public TrendingMusic[] newArray(int size) {
            return new TrendingMusic[size];
        }
    };

    public String getStrArtist() {
        return strArtist;
    }

    public void setStrArtist(String strArtist) {
        this.strArtist = strArtist;
    }

    public String getStrAlbum() {
        return strAlbum;
    }

    public void setStrAlbum(String strAlbum) {
        this.strAlbum = strAlbum;
    }

    public String getStrTrack() {
        return strTrack;
    }

    public void setStrTrack(String strTrack) {
        this.strTrack = strTrack;
    }

    public String getStrTrackThumb() {
        return strTrackThumb;
    }

    public void setStrTrackThumb(String strTrackThumb) {
        this.strTrackThumb = strTrackThumb;
    }

    public String getIntChartPlace() {
        return intChartPlace;
    }

    public void setIntChartPlace(String intChartPlace) {
        this.intChartPlace = intChartPlace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strArtist);
        dest.writeString(strAlbum);
        dest.writeString(strTrack);
        dest.writeString(strTrackThumb);
        dest.writeString(intChartPlace);
    }
}
