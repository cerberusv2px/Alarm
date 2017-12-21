package com.v2px.sujin.alarmmmmm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStop);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlarm();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAlarm();
            }
        });

    }

    private void startAlarm() {

        if (alarmManager != null) {
            return;
        }

        String dateValue = "2017-12-20T16:53:000.000Z";
        Calendar calendar = new GregorianCalendar();

        try {
            Date date = dateFormat.parse(dateValue);
            calendar.set(Calendar.HOUR_OF_DAY,date.getHours());
            calendar.set(Calendar.MINUTE, date.getMinutes());
            calendar.set(Calendar.SECOND, date.getSeconds());
            calendar.set(Calendar.DATE, date.getDate());
            calendar.set(Calendar.MONTH, date.getMonth());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e(TAG,calendar.getTimeInMillis()+"");

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        myIntent.setAction(AlarmReceiver.ACTION_START_ALARM);
        myIntent.putExtra("Status","Start");
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 0, pendingIntent);
    }

    private void stopAlarm() {
        if(alarmManager == null) return;
        Log.e("Alarm", "Stop alarm");

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ACTION_START_ALARM);
        intent.putExtra("Status","Stop");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        //AlarmManager alarmManager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        AlarmReceiver.stopRingtone();
        alarmManager = null;
    }
}
