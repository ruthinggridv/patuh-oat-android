package com.example.gungde.reminder_medicine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gungde.reminder_medicine.model.ArtikelModel;
import com.example.gungde.reminder_medicine.model.AturobatModel;
import com.example.gungde.reminder_medicine.model.UpdateArtikelModel;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.example.gungde.reminder_medicine.utils.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailArtikel extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.img_article)
    ImageView mImgArticle;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.web_view)
    WebView webView;

    private long startTime=0;

    private ArtikelModel post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_detail_artikel);

        setToolbar();
        setRefreshLayout();
        setWebView();
        startTime = System.currentTimeMillis();



    }

    private void setToolbar() {
        post = getIntent().getParcelableExtra("artikel");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarLayout.setTitle(post.getTitle().getRendered());
        mToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        mToolbarLayout.setCollapsedTitleGravity(getResources().getColor(R.color.colorGray800));
    }

    private void setRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setWebView() {
        webView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        setUrlToLoad();
    }

    private void setUrlToLoad() {
        webView.loadUrl(post.getLink());
    }

    @OnClick(R.id.fab)
    public void fabOnClick(View v){
        onShare();
    }

    private void onShare() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, post.getTitle().getRendered()
                + "\n"
                + "Link : "
                + post.getLink());
        i.setType("text/plain");
        startActivity(i);
    }

    @Override
    public void onRefresh() {
        webView.reload();
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                long endTime = System.currentTimeMillis();
                final long seconds = (endTime - startTime) / 1000;
                Log.e("second",""+seconds);
                SharedPreferences pref = getSharedPreferences("medical", Context.MODE_PRIVATE);
                final  String kategori = (pref.getString("kategori", "0"));

                if(seconds>=90&& kategori.equals("Pasien") ){
                    final String id_user = (pref.getString("id_user", "0"));
                    final String id_artikel= post.getId().toString().trim();
                    final String judul = post.getTitle().getRendered();
                    final String poin="1";
                    GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<UpdateArtikelModel> call = api.postArtikel(id_artikel, id_user, judul, poin);
                    call.enqueue(new Callback<UpdateArtikelModel>() {
                        @Override
                        public void onResponse(Call<UpdateArtikelModel> call, Response<UpdateArtikelModel> response) {
                            if (response.isSuccessful()) {

                                finish();
                            } else {
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateArtikelModel> call, Throwable t) {
                            Log.e("ERROR", t.toString());
                        }
                    });
                }
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        long endTime = System.currentTimeMillis();
        final long seconds = (endTime - startTime) / 1000;
        Log.e("second",""+seconds);
        SharedPreferences pref = getSharedPreferences("medical", Context.MODE_PRIVATE);
        final  String kategori = (pref.getString("kategori", "0"));

        if(seconds>=90&& kategori.equals("Pasien") ){
            final String id_user = (pref.getString("id_user", "0"));
            final String id_artikel= post.getId().toString().trim();
            final String judul = post.getTitle().getRendered();
            final String poin="1";
            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<UpdateArtikelModel> call = api.postArtikel(id_artikel, id_user, judul, poin);
            call.enqueue(new Callback<UpdateArtikelModel>() {
                @Override
                public void onResponse(Call<UpdateArtikelModel> call, Response<UpdateArtikelModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(DetailArtikel.this, "Update Artikel Berhasil", Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(DetailArtikel.this, "Update Artikel Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateArtikelModel> call, Throwable t) {
                    Log.e("ERROR", t.toString());
                }
            });
        }
    }

}
