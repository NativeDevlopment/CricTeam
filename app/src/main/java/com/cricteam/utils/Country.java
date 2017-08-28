package com.cricteam.utils;

/**
 * Created by Amar on 8/16/2017.
 */

 public class Country {
    private final String nameCode;
    private final String phoneCode;
    private final String name;

    public String getNameCode() {
        return nameCode;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public String getName() {
        return name;
    }

    public Country(String nameCode, String phoneCode, String name) {
        this.nameCode = nameCode;
        this.phoneCode = phoneCode;
        this.name = name;
    }

    @Override
    public String toString() {
        return name +" ("+nameCode+")" +" +"+phoneCode;
    }
}
