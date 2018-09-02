package com.example.gungde.reminder_medicine;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.adapter.CustomAdapter;
import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.model.GetIDUser;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.example.gungde.reminder_medicine.utils.AlarmReceiver;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Beranda extends Fragment {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private DataObat.Data obat;
    private AlarmManager alarmManager;


    @BindView(R.id.prograss)
    ProgressBar prograss;
    @BindView(R.id.listObat)
    RecyclerView listObat;
    @BindView(R.id.addfab)
    FloatingActionButton addfab;
    SharedPreferences pref;


    public Beranda() {
    }

    public static Beranda newInstance(String email) {
        Beranda fragment = new Beranda();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.activity_beranda, container, false);
        ButterKnife.bind(this, mView);
        pref = getActivity().getSharedPreferences("medical", Context.MODE_PRIVATE);

        listObat = mView.findViewById(R.id.listObat);
        addfab = mView.findViewById(R.id.addfab);

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
        if(getArguments()!=null && getArguments().containsKey("email")){
            progressDoalog.show();
            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            String email = getArguments().getString("email");
            Call<GetIDUser> call2 = api.getID(email);
            call2.enqueue(new Callback<GetIDUser>() {
                @Override
                public void onResponse(Call<GetIDUser> call, Response<GetIDUser> response) {
                    if(response.isSuccessful()){
                        addfab.hide();
                        getDataObat (response.body().getData().get(0).getId_user());
                    }
                }

                @Override
                public void onFailure(Call<GetIDUser> call, Throwable t) {
                    Log.e("Error", t.toString());
                    progressDoalog.dismiss();
                }
            });
        }else{
            progressDoalog.show();
            getDataObat(pref.getString("id_user", "0"));
        }
    }

    private void getDataObat(String id_user) {
        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DataObat> call = api.getObat(id_user);
        call.enqueue(new Callback<DataObat>() {

            @Override
            public void onResponse(@NonNull Call<DataObat> call, @NonNull Response<DataObat> response) {
                DataObat resp = response.body();
                if (response.body().getData().size() != 0) {
                    generateDataList(response.body().getData());
                }
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<DataObat> call, Throwable t) {
                Log.e("Error", t.toString());
                Toast.makeText(getActivity(), "Kesalahan Jaringan, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }


    private void generateDataList(List<DataObat.Data> data) {
        recyclerView = getActivity().findViewById(R.id.listObat);
        adapter = new CustomAdapter(getActivity(), data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//        Calendar calendar = Calendar.getInstance();
//        String time = obat.getWaktu_alarm().toString().trim();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(3)));
//        calendar.set(Calendar.SECOND, 0);
//
////        SharedPreferences prefs = getSharedPreferences("time", Context.MODE_PRIVATE);
//        Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);
//        myIntent.putExtra("id_obatt", obat.getId_obat());
//        myIntent.putExtra("jlh_makss", obat.getJlh_maks());
//        myIntent.putExtra("judull", obat.getNama_obat());
//        myIntent.putExtra("jlh_sisa", obat.getJlh_obat());
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), Integer.parseInt(obat.getId_obat()), myIntent, 0);
//
//        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @OnClick(R.id.addfab)
    public void tambah() {
        startActivity(new Intent(getActivity(), Home.class));
    }

}

