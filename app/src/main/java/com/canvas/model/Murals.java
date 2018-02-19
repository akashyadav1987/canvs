package com.canvas.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by akashyadav on 12/2/17.
 */

public class Murals  extends RealmObject implements RealmModel, Parcelable {
    @PrimaryKey
    private int id;
    private  int active;
    private int derelict;
    private  String image_resource_id;
    private String location_text;
    private String tags;
    private String artist_text;
    private String additional_link_first;
    private String additional_link_second;
    private String freshWhenAdded;

    public double getDistanceInKms() {
        return distanceInKms;
    }

    public void setDistanceInKms(double distanceInKms) {
        this.distanceInKms = distanceInKms;
    }

    private double distanceInKms;

    public boolean isNearBy() {
        return isNearBy;
    }

    public void setNearBy(boolean nearBy) {
        isNearBy = nearBy;
    }

    @Ignore

    private boolean isNearBy;

    public String getFreshWhenAdded() {
        return freshWhenAdded;
    }

    public void setFreshWhenAdded(String freshWhenAdded) {
        this.freshWhenAdded = freshWhenAdded;
    }

    public Murals(){

    }
    public Murals(Parcel in) {
        id = in.readInt();
        active = in.readInt();
        derelict = in.readInt();
        image_resource_id = in.readString();
        location_text = in.readString();
        tags = in.readString();
        artist_text = in.readString();
        additional_link_first = in.readString();
        additional_link_second = in.readString();
        additional_link_third = in.readString();
        about_text = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        title = in.readString();
        author = in.readString();
        image_path = in.readString();
        popularity = in.readInt();
        freshWhenAdded = in.readString();
        distanceInKms = in.readDouble();
    }

    public static final Creator<Murals> CREATOR = new Creator<Murals>() {
        @Override
        public Murals createFromParcel(Parcel in) {
            return new Murals(in);
        }

        @Override
        public Murals[] newArray(int size) {
            return new Murals[size];
        }
    };

    public String getAdditional_limk_third() {
        return additional_link_third;
    }

    public void setAdditional_link_third(String additional_limk_third) {
        this.additional_link_third = additional_limk_third;
    }

    private String additional_link_third;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getArtist_text() {
        return artist_text;
    }

    public void setArtist_text(String artist_text) {
        this.artist_text = artist_text;
    }

    public String getAdditional_link_first() {
        return additional_link_first;
    }

    public void setAdditional_link_first(String additional_limk_first) {
        this.additional_link_first = additional_limk_first;
    }

    public String getAdditional_link_second() {
        return additional_link_second;
    }

    public void setAdditional_link_second(String additional_limk_second) {
        this.additional_link_second = additional_limk_second;
    }

    public String getLocation_text() {
        return location_text;
    }

    public void setLocation_text(String location_text) {
        this.location_text = location_text;
    }

    public String getAbout_text() {
        return about_text;
    }

    public void setAbout_text(String about_text) {
        this.about_text = about_text;
    }

    private String about_text;

    public String getImage_resource_id() {
        return image_resource_id;
    }

    public void setImage_resource_id(String image_resource_id) {
        this.image_resource_id = image_resource_id;
    }

    double latitude,longitude;
   String title,author,image_path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getDerelict() {
        return derelict;
    }

    public void setDerelict(int derelict) {
        this.derelict = derelict;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

  private   int popularity;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(active);
        parcel.writeInt(derelict);
        parcel.writeString(image_resource_id);
        parcel.writeString(location_text);
        parcel.writeString(tags);
        parcel.writeString(artist_text);
        parcel.writeString(additional_link_first);
        parcel.writeString(additional_link_second);
        parcel.writeString(additional_link_third);
        parcel.writeString(about_text);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(image_path);
        parcel.writeInt(popularity);
        parcel.writeString(freshWhenAdded);
        parcel.writeDouble(distanceInKms);
    }
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((String.valueOf(id) == null) ? 0 : String.valueOf(id).hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Murals other = (Murals) obj;
        if (id != other.id)
            return false;
        if (id == 0) {
            if (other.id != 0)
                return false;
        } else if (!String.valueOf(id).equals(other.id))
            return false;
        return true;
    }
}
