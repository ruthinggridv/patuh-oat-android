package com.example.gungde.reminder_medicine.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gungde.reminder_medicine.DetailArtikel;
import com.example.gungde.reminder_medicine.R;
import com.example.gungde.reminder_medicine.model.ArtikelModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.MainViewHolder> {

    private List<ArtikelModel> listArray;
    private Activity context;

    public ArtikelAdapter(List<ArtikelModel> listArray, Activity context) {
        this.listArray = listArray;
        this.context = context;
    }

    @Override
    public ArtikelAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_artikel_detail2, parent, false);
        MainViewHolder viewHolder = new MainViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArtikelAdapter.MainViewHolder holder, int position) {
        ArtikelModel post = listArray.get(position);
        holder.getTxTitle().setText(post.getTitle().getRendered());
        holder.getTxMessage().setText(Html.fromHtml(post.getExcerpt().getRendered()));
//        Log.e("ARTIKEL", post.getEmbedded().getAuthor().get(0).getId());
//        Glide.with(context).load(post.getEmbedded().ge
// tWpFeaturedmedia().get(0).getSourceUrl()).into(holder.getImgArticle());
    }

    @Override
    public int getItemCount() {
        return listArray.size();
    }

    public void refill(ArtikelModel post) {
        listArray.add(post);
        notifyDataSetChanged();
    }

    public void setFilter(List<ArtikelModel> newList) {
        listArray = new ArrayList<>();
        listArray.addAll(newList);
        notifyDataSetChanged();
    }

    public void clear() {
        listArray.clear();
    }

    public void refill(List<ArtikelModel> list) {
        listArray.addAll(list);
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tx_title)
        TextView txTitle;
        @BindView(R.id.tx_message)
        TextView txMessage;
//        @BindView(R.id.img_article)
//        ImageView imgArticle;

        public MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ArtikelModel post = listArray.get(getLayoutPosition());
            Intent i = new Intent(context, DetailArtikel.class);
            i.putExtra("artikel", post);
            context.startActivity(i);
        }

        public TextView getTxTitle() {
            return txTitle;
        }

        public TextView getTxMessage() {
            return txMessage;
        }

//        public ImageView getImgArticle() {
//            return imgArticle;
//        }
    }
}
