package com.example.jvtcred;

public class AddressesModel {

    private String fullname;
    private String mobileNo;
    private String address;
    private String pincode;
    private boolean selected;


    public AddressesModel(String fullname, String address, String pincode , Boolean selected,String mobileNo) {
        this.fullname = fullname;
        this.address = address;
        this.pincode = pincode;
        this.selected=selected;
        this.mobileNo = mobileNo;

    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
