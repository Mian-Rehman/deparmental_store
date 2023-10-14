package com.rehman.newtrend.Model;

public class User
{
    String userUID;
    String userCity;
    String userState;
    String userCountry;
    String userLocation;
    String userLatitude;
    String userLongitude;
    String phoneNumber;

    public User(String userUID, String userCity, String userState, String userCountry, String userLocation, String userLatitude, String userLongitude, String phoneNumber) {
        this.userUID = userUID;
        this.userCity = userCity;
        this.userState = userState;
        this.userCountry = userCountry;
        this.userLocation = userLocation;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
