package com.example.getwithparam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RawModel {

    @SerializedName("Data")
    public ArrayList<Data> Data;
    @SerializedName("Message")
    public String Message;
    @SerializedName("Status")
    public boolean Status;
    @SerializedName("StatusCode")
    public int StatusCode;

    public static class Data {
        @SerializedName("date")
        public String date;
        @SerializedName("description")
        public String description;

        @Override
        public String toString() {
            return "Data{" +
                    "date='" + date + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RawModel{" +
                "Data=" + Data +
                ", Message='" + Message + '\'' +
                ", Status=" + Status +
                ", StatusCode=" + StatusCode +
                '}';
    }
}
