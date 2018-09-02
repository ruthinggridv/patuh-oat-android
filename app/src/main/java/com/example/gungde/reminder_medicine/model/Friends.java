package com.example.gungde.reminder_medicine.model;

public class Friends {
    public String date;

    public String getKategori_pangguna() {
        return kategori_pangguna;
    }

    public void setKategori_pangguna(String kategori_pangguna) {
        this.kategori_pangguna = kategori_pangguna;
    }

    public String kategori_pangguna;

    public Friends(){

    }

    public Friends(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
