package com.example.gungde.reminder_medicine.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.Aturobat;
import com.example.gungde.reminder_medicine.R;
import com.example.gungde.reminder_medicine.Report;
import com.example.gungde.reminder_medicine.model.AturobatModel;
import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.example.gungde.reminder_medicine.utils.AlarmReceiver;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{
    private List<DataObat.Data> data;
    private Context context;
//    private AlarmManager alarmManager;

    public CustomAdapter(Context context,List<DataObat.Data> data){
        this.context = context;
        this.data = data;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtJudul;
        TextView txtJlh;
        TextView txtCatatan;
        TextView txtWaktu;
        TextView txtAlarm;
        TextView txtSisa;

        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtJudul = mView.findViewById(R.id.txtJudul);
            txtJlh = mView.findViewById(R.id.txtJlh);
            txtCatatan = mView.findViewById(R.id.txtCatatan);
            txtWaktu = mView.findViewById(R.id.txtWaktu);
            txtAlarm = mView.findViewById(R.id.txtAlarm);
            txtSisa = mView.findViewById(R.id.txtSisa);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, Report.class);
                    i.putExtra("obat", data.get(getAdapterPosition()));
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_menu, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtJudul.setText(data.get(position).getNama_obat());
        holder.txtJlh.setText(data.get(position).getJlh_maks());
        holder.txtCatatan.setText(data.get(position).getCatatan());
        holder.txtWaktu.setText(data.get(position).getWaktu_akhir());
        holder.txtAlarm.setText(data.get(position).getWaktu_alarm());
        holder.txtSisa.setText(data.get(position).getJlh_obat());
//
//           AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//           Calendar calendar = Calendar.getInstance();
//           String time = data.get(position).getWaktu_alarm();
//           calendar.setTimeInMillis(System.currentTimeMillis());
//           calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
//           calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(3, 5)));
//           calendar.set(Calendar.SECOND, 0);
//
//           Intent myIntent = new Intent(context, AlarmReceiver.class);
//           myIntent.putExtra("id_obatt", data.get(position).getId_obat());
//           myIntent.putExtra("jlh_makss", data.get(position).getJlh_maks());
//           myIntent.putExtra("judull", data.get(position).getNama_obat());
//           myIntent.putExtra("jlh_sisa", data.get(position).getJlh_obat());
//
//           PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(data.get(position).getId_obat()), myIntent, 0);
//           alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                   pendingIntent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
