package com.example.gungde.reminder_medicine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gungde.reminder_medicine.R;
import com.example.gungde.reminder_medicine.Report;
import com.example.gungde.reminder_medicine.ReportArtikel;
import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.model.ReportArtikelModel;

import java.util.List;

public class ReportArtikelAdapter extends RecyclerView.Adapter<ReportArtikelAdapter.CustomViewHolder>{
    private List<ReportArtikelModel.Data> data;
    private Context context;

    public ReportArtikelAdapter(Context context,List<ReportArtikelModel.Data> data){
        this.context = context;
        this.data = data;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtJudul;
        TextView txtPoin;

        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtJudul = mView.findViewById(R.id.txtJudul);
            txtPoin = mView.findViewById(R.id.txtPoin);
        }
    }

    @Override
    public ReportArtikelAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_list_artikel_pasien, parent, false);
        return new ReportArtikelAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportArtikelAdapter.CustomViewHolder holder, int position) {
        holder.txtJudul.setText(data.get(position).getJudul());
        holder.txtPoin.setText(data.get(position).getPoin());
//        holder.txtAlarm.setText();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
