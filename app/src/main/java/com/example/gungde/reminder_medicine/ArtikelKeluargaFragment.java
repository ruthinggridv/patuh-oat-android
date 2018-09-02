package com.example.gungde.reminder_medicine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.gungde.reminder_medicine.adapter.ArtikelAdapter;
import com.example.gungde.reminder_medicine.model.ArtikelModel;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.example.gungde.reminder_medicine.utils.ListNamaPasien;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtikelKeluargaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout mySwipeRefreshLayout;
    @BindView(R.id.progress_bar_users)
    ProgressBar mProgressBarForUsers;
    @BindView(R.id.btnReport)
    Button btnReport;

    private static final String TAG = ArtikelKeluargaFragment.class.getSimpleName();

    private ArtikelAdapter adapter;


    public ArtikelKeluargaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_artikel_keluarga, container, false);
        ButterKnife.bind(this, mView);

        setRecyclerView();
        setSwipeRefresh();

        return mView;
    }

    private void setRecyclerView() {
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ArtikelAdapter(new ArrayList<ArtikelModel>(), getActivity());
        mList.setAdapter(adapter);
    }

    private void setSwipeRefresh() {
        mySwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorGray700));
        mySwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        queryAllArticles();
    }

    private void queryAllArticles() {
        GetDataService api = RetrofitClientInstance.getRetrofitInstance2().create(GetDataService.class);
        Call<List<ArtikelModel>> call = api.getArticle("true");
        call.enqueue(new Callback<List<ArtikelModel>>() {
            @Override
            public void onResponse(Call<List<ArtikelModel>> call, Response<List<ArtikelModel>> response) {
                if (response.isSuccessful()) {
                    clearList();
//                prepareData(response.body());
                    adapter.refill(response.body());
                    hideProgressBarForUsers();
                    hideSwipeRefreshForUsers();
                } else
                    Log.e(TAG, response.errorBody().toString() + "");
            }

            @Override
            public void onFailure(Call<List<ArtikelModel>> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "");
                hideProgressBarForUsers();
                hideSwipeRefreshForUsers();
            }
        });
    }

    //    private void prepareData(List<ArtikelModel> body) {
////        mList.setVisibility(View.VISIBLE);
////        for(int i = 0;i<body.size();i++) {
////            body.get(i).setImages(images.get(i));
////            adapter.refill(body.get(i));
////            articles.add(body.get(i));
////        }
////    }
    @Override
    public void onStart() {
        super.onStart();
        showProgressBarForUsers();
        queryAllArticles();
    }

    @Override
    public void onStop() {
        super.onStop();
        clearList();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setOnQueryTextListener(this);
//        return true;
//    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }

//    @Override
//    public boolean onQueryTextChange(String newText) {
//        newText = newText.toLowerCase();
//
//        ArrayList<ArtikelModel> newArticles = new ArrayList<>();
//        for(ArtikelModel article : articles) {
//            String title = article.getTitle().getRendered().toLowerCase();
//            if(title.contains(newText)) {
//                newArticles.add(article);
//            }
//        }
//        adapter.setFilter(newArticles);
//        return true;
//    }

    private void showProgressBarForUsers() {
        mList.setVisibility(View.GONE);
        mProgressBarForUsers.setVisibility(View.VISIBLE);
    }

    private void hideProgressBarForUsers() {
        if (mProgressBarForUsers.getVisibility() == View.VISIBLE) {
            mProgressBarForUsers.setVisibility(View.GONE);
            mList.setVisibility(View.VISIBLE);
        }
    }

    private void hideSwipeRefreshForUsers() {
        mySwipeRefreshLayout.setRefreshing(false);
        mList.setVisibility(View.VISIBLE);
    }

    private void clearList() {
        adapter.clear();
    }

    @OnClick(R.id.btnReport)
    public void onViewClicked() {
        NamaPasien fragment = new NamaPasien();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(NamaPasien.class.getSimpleName());
        transaction.commit();
    }

}
