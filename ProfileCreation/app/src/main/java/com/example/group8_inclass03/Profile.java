package com.example.group8_inclass03;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * In Class 03
 * Profile.java
 * Devin Bonney and David Hotaran
 */
public class Profile implements Parcelable {
    String name;
    String email;
    String department;
    int id;

    public Profile(String name, String email, String department, int id) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.id = id;
    }

    public Profile() {

    }

    protected Profile(Parcel in) {
        name = in.readString();
        email = in.readString();
        department = in.readString();
        id = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.department);
        dest.writeInt(this.id);
    }
}
