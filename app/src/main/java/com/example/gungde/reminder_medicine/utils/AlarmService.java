package com.example.gungde.reminder_medicine.utils;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.gungde.reminder_medicine.Minumobat;
import com.example.gungde.reminder_medicine.R;

/**
 * Created by macbookpro on 7/3/18.
 */

public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String id_obat = intent.getExtras() != null && intent.getExtras().containsKey("id_obatt") ? intent.getExtras().getString("id_obatt") : "";
        String judul = intent.getExtras() != null && intent.getExtras().containsKey("judull") ? intent.getExtras().getString("judull") : "";
        String jlh_maks = intent.getExtras() != null && intent.getExtras().containsKey("jlh_makss") ? intent.getExtras().getString("jlh_makss") : "";
        sendNotification("Waktunya minum obat "+judul+" !", id_obat, judul, jlh_maks);

    }

    private void sendNotification(String msg, String id_obat, String judul, String jlh_maks) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i = new Intent(this, Minumobat.class);
        i.putExtra("id_obatt", id_obat);
        i.putExtra("jlh_makss", jlh_maks);
        i.putExtra("judull", judul);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        this.startActivity(i);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                i, 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Patuh OAT Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}
