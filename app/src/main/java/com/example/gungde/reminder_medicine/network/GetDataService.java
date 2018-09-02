package com.example.gungde.reminder_medicine.network;


import com.example.gungde.reminder_medicine.adapter.ArtikelAdapter;
import com.example.gungde.reminder_medicine.model.ArticleModel2;
import com.example.gungde.reminder_medicine.model.ArtikelModel;
import com.example.gungde.reminder_medicine.model.AturobatModel;
import com.example.gungde.reminder_medicine.model.Base;
import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.model.DataUser;
import com.example.gungde.reminder_medicine.model.GetIDUser;
import com.example.gungde.reminder_medicine.model.GetKategori;
import com.example.gungde.reminder_medicine.model.HapusModel;
import com.example.gungde.reminder_medicine.model.InputObat;
import com.example.gungde.reminder_medicine.model.LoginModel;
import com.example.gungde.reminder_medicine.model.ReportArtikelModel;
import com.example.gungde.reminder_medicine.model.ReportObat;
import com.example.gungde.reminder_medicine.model.TranskasiModel;
import com.example.gungde.reminder_medicine.model.UpdateArtikelModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @FormUrlEncoded
    @POST("public/insertuser")
    Call<Base> postData(@Field("username") String username,
                        @Field("email") String email,
                        @Field("nohp") String nohp,
                        @Field("kategori") String kategori,
                        @Field("password") String password);

    @FormUrlEncoded
    @POST("public/login")
    Call<LoginModel> LoginUser(@Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("public/insertobat")
    Call<InputObat> postObat(@Field("id_user") String id_user,
                             @Field("nama_obat") String nama_obat,
                             @Field("jlh_maks") String jlh_maks,
                             @Field("jlh_obat") String jlh_obat,
                             @Field("catatan") String catatan,
                             @Field("waktu_akhir") String waktu_akhir,
                             @Field("waktu_alarm") String waktu_alarm);

    @GET("public/getartikel/{id_user}")
    Call<ReportArtikelModel> getReport(@Path("id_user") String id_user);

    @GET("public/getobat/{id_user}")
    Call<DataObat> getObat(@Path("id_user") String id_user);

    @GET("public/getiduser/{email}")
    Call<GetIDUser> getID(@Path("email") String email);

    @GET("public/gettrans/{id_obat}")
    Call<ReportObat> getReportObat(@Path("id_obat") String id_obat);

    @GET("public/getkategori/{email}")
    Call<GetKategori> getKategori(@Path("email") String email);

    @GET("public/getalluser")
    Call<DataUser> getDataUser();

    @GET("posts")
    Call<List<ArtikelModel>> getArticle(
            @Query("?_embed") String embed
    );

    @FormUrlEncoded
    @POST("public/inserttrans")
    Call<TranskasiModel> postTrans(@Field("id_obat") String id_obat,
                                   @Field("jlh_minum") String jlh_minum,
                                   @Field("jlh_total") String jlh_total,
                                   @Field("waktu_minum") String waktu_minum);

    @FormUrlEncoded
    @POST("public/updateobat/{id_obat}")
    Call<AturobatModel> editObat(@Path("id_obat") String id_obat,
                                 @Field("id_user") String id_user,
                                 @Field("nama_obat") String nama_obat,
                                 @Field("jlh_maks") String jlh_maks,
                                 @Field("jlh_obat") String jlh_obat,
                                 @Field("catatan") String catatan,
                                 @Field("waktu_akhir") String waktu_akhir,
                                 @Field("waktu_alarm") String waktu_alarm);

    @DELETE("public/hapusobat/{id_obat}")
    Call<HapusModel> delObat(@Path("id_obat") String id_obat);

    @FormUrlEncoded
    @POST("public/insertartikel")
    Call<UpdateArtikelModel> postArtikel(@Field("id_artikel") String id_artikel,
                                      @Field("id_user") String id_user,
                                      @Field("judul") String judul,
                                      @Field("poin") String poin);
}