package com.v2px.sujin.alarmmmmm;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String ACTION_START_ALARM = "ACTION_START_ALARM";

    MediaPlayer mMediaPlayer;
    static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive", ACTION_START_ALARM);
        Log.e("Intent", intent.getStringExtra("Status"));
        if (intent != null) {

            Log.e("Receiver", "staart alarmmm");
            if (intent.getAction().equals(ACTION_START_ALARM)) {

                Log.e("AlarmManagaer", "Action fired successfully");
                Toast.makeText(context, "Alarm...", Toast.LENGTH_LONG).show();
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                ringtone = RingtoneManager.getRingtone(context, uri);
                if(intent.getStringExtra("Status").equals("Stop")) {
                    stopRingtone();
                } else {
                    playRingtone();
                }

            }
        }

    }

    public static void playRingtone() {
        ringtone.play();
    }


    public static void stopRingtone() {
        ringtone.stop();
    }
}
