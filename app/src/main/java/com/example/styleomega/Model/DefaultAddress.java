package com.example.styleomega.Model;

public class DefaultAddress {
    private String appartmentNo,street,city,district,username;
    String zipcode;

    public DefaultAddress(){

    }

    public DefaultAddress(String username,String appartmentNo, String street, String city,String district,String zipcode) {
        this.username=username;
        this.appartmentNo = appartmentNo;
        this.street = street;
        this.city = city;
        this.district=district;
        this.zipcode = zipcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAppartmentNo() {
        return appartmentNo;
    }

    public void setAppartmentNo(String appartmentNo) {
        this.appartmentNo = appartmentNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
