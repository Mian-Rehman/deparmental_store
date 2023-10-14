package com.rehman.newtrend.Model;

public class PaymentSavedModel
{
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String addressOne;
    String addressTwo;
    String city;
    String state;
    String userUID;
    String paymentId;
    String servicesOrder;
    String totalPayment;
    String servicesOrderKey;
    String paymentVia;
    String paymentType;
    String ephericalId;
    String clientSecretId;
    String bookingStatus;
    String bookingDateHire;
    String bookingTimeHire;
    String currentDate;
    String currentTime;
    String bookingNames;
    String bookingPrices;

    public PaymentSavedModel(String firstName, String lastName, String email, String phoneNumber, String addressOne, String addressTwo, String city, String state, String userUID, String paymentId, String servicesOrder, String totalPayment, String servicesOrderKey, String paymentVia, String paymentType, String ephericalId, String clientSecretId, String bookingStatus, String bookingDateHire, String bookingTimeHire, String currentDate, String currentTime, String bookingNames, String bookingPrices) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.city = city;
        this.state = state;
        this.userUID = userUID;
        this.paymentId = paymentId;
        this.servicesOrder = servicesOrder;
        this.totalPayment = totalPayment;
        this.servicesOrderKey = servicesOrderKey;
        this.paymentVia = paymentVia;
        this.paymentType = paymentType;
        this.ephericalId = ephericalId;
        this.clientSecretId = clientSecretId;
        this.bookingStatus = bookingStatus;
        this.bookingDateHire = bookingDateHire;
        this.bookingTimeHire = bookingTimeHire;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.bookingNames = bookingNames;
        this.bookingPrices = bookingPrices;
    }

    public PaymentSavedModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getServicesOrder() {
        return servicesOrder;
    }

    public void setServicesOrder(String servicesOrder) {
        this.servicesOrder = servicesOrder;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getServicesOrderKey() {
        return servicesOrderKey;
    }

    public void setServicesOrderKey(String servicesOrderKey) {
        this.servicesOrderKey = servicesOrderKey;
    }

    public String getPaymentVia() {
        return paymentVia;
    }

    public void setPaymentVia(String paymentVia) {
        this.paymentVia = paymentVia;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getEphericalId() {
        return ephericalId;
    }

    public void setEphericalId(String ephericalId) {
        this.ephericalId = ephericalId;
    }

    public String getClientSecretId() {
        return clientSecretId;
    }

    public void setClientSecretId(String clientSecretId) {
        this.clientSecretId = clientSecretId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBookingDateHire() {
        return bookingDateHire;
    }

    public void setBookingDateHire(String bookingDateHire) {
        this.bookingDateHire = bookingDateHire;
    }

    public String getBookingTimeHire() {
        return bookingTimeHire;
    }

    public void setBookingTimeHire(String bookingTimeHire) {
        this.bookingTimeHire = bookingTimeHire;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getBookingNames() {
        return bookingNames;
    }

    public void setBookingNames(String bookingNames) {
        this.bookingNames = bookingNames;
    }

    public String getBookingPrices() {
        return bookingPrices;
    }

    public void setBookingPrices(String bookingPrices) {
        this.bookingPrices = bookingPrices;
    }
}
