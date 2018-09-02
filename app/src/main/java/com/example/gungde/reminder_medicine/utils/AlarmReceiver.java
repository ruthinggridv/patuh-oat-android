package com.example.gungde.reminder_medicine.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.gungde.reminder_medicine.Home;

/**
 * Created by macbookpro on 7/3/18.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
            String ID = intent.getExtras().getString("id_obatt");
            String judul = intent.getExtras() != null && intent.getExtras().containsKey("judull") ? intent.getExtras().getString("judull") : "";
            String jlh_maks = intent.getExtras() != null && intent.getExtras().containsKey("jlh_makss") ? intent.getExtras().getString("jlh_makss") : "";
            Log .e("id obat", ""+ID);
            Log.i("CreateReminder", "reminder_id:-"+ID);

            Home inst = Home.instance();
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            final Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();
            ComponentName comp = new ComponentName(context.getPackageName(),
                    AlarmService.class.getName());
//            ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
//            PackageManager pm = context.getPackageManager();
//            pm.setComponentEnabledSetting(comp,
//                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                    PackageManager.DONT_KILL_APP);
//            Intent notificationIntent = new Intent(context, AlarmService.class);
//            notificationIntent.putExtra("id_obatt", ID);
//            notificationIntent.putExtra("judull", judul);
//            notificationIntent.putExtra("jlh_makss", jlh_maks);
            startWakefulService(context, (intent.setComponent(comp)));
            setResultCode(Activity.RESULT_OK);
//            String time = intent.getExtras().getString("TIME");
            /*inst.onSuccess(time);*/
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ringtone.stop();
                }
            }, 7000);
    }

}
