package com.example.gungde.reminder_medicine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.adapter.ReportArtikelAdapter;
import com.example.gungde.reminder_medicine.model.GetIDUser;
import com.example.gungde.reminder_medicine.model.ReportArtikelModel;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportArtikelPasien extends Fragment {
    @BindView(R.id.listObat)
    RecyclerView listObat;
    @BindView(R.id.prograss)
    ProgressBar prograss;
    private ReportArtikelAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;


    SharedPreferences pref;

    public ReportArtikelPasien() {
    }


    public static Fragment newInstance(String email) {
        ReportArtikelPasien fragment = new ReportArtikelPasien();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_report_artikel_pasien, container, false);
        ButterKnife.bind(this, mView);
        pref = getActivity().getSharedPreferences("medical", Context.MODE_PRIVATE);

        listObat = mView.findViewById(R.id.listObat);

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDataForPasien();
    }

    private void getDataForPasien() {
        if (getArguments() != null && getArguments().containsKey("email")) {
            progressDoalog.show();
            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            String email = getArguments().getString("email");
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
                Toast.makeText(getActivity(), "Kesalahan Jaringan, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    private void generateDataList(List<ReportArtikelModel.Data> data) {
        recyclerView = getActivity().findViewById(R.id.listObat);
        adapter = new ReportArtikelAdapter(getActivity(), data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

