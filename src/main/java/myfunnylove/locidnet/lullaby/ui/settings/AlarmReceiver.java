package myfunnylove.locidnet.lullaby.ui.settings;

/**
 * Created by Michaelan on 12/10/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import myfunnylove.locidnet.lullaby.player.PlaybackService;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "BANANEALARM";
    Intent intent;
    PendingIntent pendingIntent;
    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "BroadcastReceiver has received alarm intent.");
        Intent service1 = new Intent(context, PlaybackService.class);
        service1.setAction(PlaybackService.ACTION_PLAY_TOGGLE);
        context.startService(service1);

    }

}