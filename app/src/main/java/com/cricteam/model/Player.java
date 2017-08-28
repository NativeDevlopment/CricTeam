package com.cricteam.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amar on 8/17/2017.
 */

public class Player implements Parcelable{
    public String name;
    public String mobileNo;
    public String email;
    public String image;
    public String payerType;
    public boolean isCaptaion;
    public boolean isVoiceCaption;
public  Player(){

}
    protected Player(Parcel in) {
        name = in.readString();
        mobileNo = in.readString();
        email = in.readString();
        image = in.readString();
        payerType = in.readString();
        isCaptaion = (boolean) in.readValue(null);
        isVoiceCaption = (boolean) in.readValue(null);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(mobileNo);
        parcel.writeString(email);
        parcel.writeString(image);
        parcel.writeString(payerType);
        parcel.writeValue(isCaptaion);
        parcel.writeValue(isVoiceCaption);
    }
}

