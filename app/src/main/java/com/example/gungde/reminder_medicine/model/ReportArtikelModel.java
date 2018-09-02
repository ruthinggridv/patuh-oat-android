package com.example.gungde.reminder_medicine.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportArtikelModel {

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
        @SerializedName("poin")
        private String poin;
        @SerializedName("judul")
        private String judul;
        @SerializedName("id_user")
        private String id_user;
        @SerializedName("id_artikel")
        private String id_artikel;

        public String getPoin() {
            return poin;
        }

        public void setPoin(String poin) {
            this.poin = poin;
        }

        public String getJudul() {
            return judul;
        }

        public void setJudul(String judul) {
            this.judul = judul;
        }

        public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
        }

        public String getId_artikel() {
            return id_artikel;
        }

        public void setId_artikel(String id_artikel) {
            this.id_artikel = id_artikel;
        }
    }
}
