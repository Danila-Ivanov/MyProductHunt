package ru.dtechnologies.myproducthunter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * BroadcastReceiver for start service Notification
 */
public class ReseiverForStartService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, ServiceNotifications.class);
        context.startService(intentService);
    }
}
