package com.androdev.prototyperadiostreaming.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.androdev.prototyperadiostreaming.R;
import com.androdev.prototyperadiostreaming.TabRealActivity;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public class Util {

    public static void buildNotification(Context context, Activity activity, String titleRadio) {
        Intent intent = new Intent(context, TabRealActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher));
        builder.setContentTitle("Vibe Radio");
        builder.setContentText("Now Playing " + titleRadio);
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
