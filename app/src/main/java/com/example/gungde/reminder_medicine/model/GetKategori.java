package com.example.gungde.reminder_medicine.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetKategori {
    @SerializedName("data")
    private List<Data> data;
    @SerializedName("status")
    private String status;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Data {
        @SerializedName("kategori")
        private String kategori;

        public String getKategori() {
            return kategori;
        }

        public void setKategori(String kategori) {
            this.kategori = kategori;
        }
    }
}
