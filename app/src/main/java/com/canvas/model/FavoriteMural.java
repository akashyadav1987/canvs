package com.canvas.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by akashyadav on 12/9/17.
 */

public class FavoriteMural   extends RealmObject implements RealmModel, Parcelable{

    @PrimaryKey
    private int id;
    private String active;
    private String derelict;
    private String popularity;
    private String freshWhenAdded;
    private String tags;
    private String muralTitle;


    public FavoriteMural(Parcel in) {
        id = in.readInt();
        active = in.readString();
        derelict = in.readString();
        popularity = in.readString();
        freshWhenAdded = in.readString();
        tags = in.readString();
        muralTitle = in.readString();
        artistName = in.readString();
        imageResourceID = in.readString();
        locationText = in.readString();
        aboutArtistText = in.readString();
        aboutThisText = in.readString();
        creationDate = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        additionalLink1 = in.readString();
        additionalLink2 = in.readString();
        additionalLink3 = in.readString();
        recordCreated = in.readString();
        recordModified = in.readString();
    }
    public FavoriteMural(){

    }
    public static final Creator<FavoriteMural> CREATOR = new Creator<FavoriteMural>() {
        @Override
        public FavoriteMural createFromParcel(Parcel in) {
            return new FavoriteMural(in);
        }

        @Override
        public FavoriteMural[] newArray(int size) {
            return new FavoriteMural[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDerelict() {
        return derelict;
    }

    public void setDerelict(String derelict) {
        this.derelict = derelict;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getFreshWhenAdded() {
        return freshWhenAdded;
    }

    public void setFreshWhenAdded(String freshWhenAdded) {
        this.freshWhenAdded = freshWhenAdded;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMuralTitle() {
        return muralTitle;
    }

    public void setMuralTitle(String muralTitle) {
        this.muralTitle = muralTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImageResourceID() {
        return imageResourceID;
    }

    public void setImageResourceID(String imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public String getAboutArtistText() {
        return aboutArtistText;
    }

    public void setAboutArtistText(String aboutArtistText) {
        this.aboutArtistText = aboutArtistText;
    }

    public String getAboutThisText() {
        return aboutThisText;
    }

    public void setAboutThisText(String aboutThisText) {
        this.aboutThisText = aboutThisText;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAdditionalLink1() {
        return additionalLink1;
    }

    public void setAdditionalLink1(String additionalLink1) {
        this.additionalLink1 = additionalLink1;
    }

    public String getAdditionalLink2() {
        return additionalLink2;
    }

    public void setAdditionalLink2(String additionalLink2) {
        this.additionalLink2 = additionalLink2;
    }

    public String getAdditionalLink3() {
        return additionalLink3;
    }

    public void setAdditionalLink3(String additionalLink3) {
        this.additionalLink3 = additionalLink3;
    }

    public String getRecordCreated() {
        return recordCreated;
    }

    public void setRecordCreated(String recordCreated) {
        this.recordCreated = recordCreated;
    }

    public String getRecordModified() {
        return recordModified;
    }

    public void setRecordModified(String recordModified) {
        this.recordModified = recordModified;
    }

    private String artistName;
    private String imageResourceID;
    private String locationText;
    private String aboutArtistText;
    private String aboutThisText;
    private String creationDate;
    private String latitude;
    private String longitude;
    private String additionalLink1;
    private String additionalLink2;
    private String additionalLink3;
    private String recordCreated;
    private String recordModified;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(active);
        parcel.writeString(derelict);
        parcel.writeString(popularity);
        parcel.writeString(freshWhenAdded);
        parcel.writeString(tags);
        parcel.writeString(muralTitle);
        parcel.writeString(artistName);
        parcel.writeString(imageResourceID);
        parcel.writeString(locationText);
        parcel.writeString(aboutArtistText);
        parcel.writeString(aboutThisText);
        parcel.writeString(creationDate);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(additionalLink1);
        parcel.writeString(additionalLink2);
        parcel.writeString(additionalLink3);
        parcel.writeString(recordCreated);
        parcel.writeString(recordModified);
    }
}
