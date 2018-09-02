package com.example.gungde.reminder_medicine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.adapter.ReportArtikelAdapter;
import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.model.GetIDUser;
import com.example.gungde.reminder_medicine.model.ReportArtikelModel;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportArtikel extends AppCompatActivity {
    private ReportArtikelAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;


    @BindView(R.id.prograss)
    ProgressBar prograss;
    @BindView(R.id.listObat)
    RecyclerView listObat;
    SharedPreferences pref;

//    public static ReportArtikel newInstance(String email) {
//        Beranda fragment = new Beranda();
//        Bundle args = new Bundle();
//        args.putString("email", email);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_artikel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        pref = this.getSharedPreferences("medical", Context.MODE_PRIVATE);

        listObat = findViewById(R.id.listObat);

        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDataForPasien();
    }

    private void getDataForPasien() {
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("email")) {
            progressDoalog.show();
            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            String email = getIntent().getExtras().getString("email");
            Call<GetIDUser> call2 = api.getID(email);
            call2.enqueue(new Callback<GetIDUser>() {
                @Override
                public void onResponse(Call<GetIDUser> call, Response<GetIDUser> response) {
                    if (response.isSuccessful()) {
                        getDataArtikel(response.body().getData().get(0).getId_user());
                    }
                }

                @Override
                public void onFailure(Call<GetIDUser> call, Throwable t) {
                    Log.e("Error", t.toString());
                    progressDoalog.dismiss();
                }
            });
        }
    }

    //get Data Artikel
    private void getDataArtikel(String id_user) {
        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ReportArtikelModel> call = api.getReport(id_user);
        call.enqueue(new Callback<ReportArtikelModel>() {

            @Override
            public void onResponse(@NonNull Call<ReportArtikelModel> call, @NonNull Response<ReportArtikelModel> response) {
                ReportArtikelModel resp = response.body();
                if (response.isSuccessful()) {
                    generateDataList(resp.getData());
                }
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<ReportArtikelModel> call, Throwable t) {
                Log.e("Error", t.toString());
                Toast.makeText(ReportArtikel.this, "Kesalahan Jaringan, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    private void generateDataList(List<ReportArtikelModel.Data> data) {
        recyclerView = ReportArtikel.this.findViewById(R.id.listObat);
        adapter = new ReportArtikelAdapter(ReportArtikel.this, data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReportArtikel.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
