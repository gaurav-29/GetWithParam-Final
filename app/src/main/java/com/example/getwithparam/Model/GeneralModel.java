package com.example.getwithparam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GeneralModel {


    @SerializedName("data")
    public ArrayList<Data> data;
    @SerializedName("msg")
    public String msg;
    @SerializedName("status")
    public String status;

    public static class Data {
        @SerializedName("password")
        public String password;
        @SerializedName("email")
        public String email;
        @SerializedName("city")
        public String city;
        @SerializedName("mobile_number")
        public String mobile_number;
        @SerializedName("last_name")
        public String last_name;
        @SerializedName("first_name")
        public String first_name;

        @Override
        public String toString() {
            return "Data{" +
                    "password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    ", city='" + city + '\'' +
                    ", mobile_number='" + mobile_number + '\'' +
                    ", last_name='" + last_name + '\'' +
                    ", first_name='" + first_name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
