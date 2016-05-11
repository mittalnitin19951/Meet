package com.example.meet;

/**
 * Created by HP on 01-10-2015.
 */
public class DataProvider {

    private String Name="";
    private String Designation = "";
    private String Company_Name = "";
    private String Email_id = "";
    private String Mobile_No="";
    private String Phone_No="";
    private String Website="";
    private String Address="";


    public DataProvider()
    {

    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmail_id() {
        return Email_id;
    }

    public void setEmail_id(String email_id) {
        Email_id = email_id;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }


    public String[] getData(String name,String designation,String company_Name,String email_id,
                            String mobile_No,String phone_No, String website,String address) {
        String[] s = {name, designation, company_Name, email_id, mobile_No, phone_No, website, address};


    return s;
    }


    public String[] getData(String name,String designation,String company_Name) {
        String[] s = {name, designation, company_Name};


        return s;
    }


}
