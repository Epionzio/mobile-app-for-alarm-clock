package com.example.alarmclock;

import static android.widget.TimePicker.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Button;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button btnTimer;
    private int Hour, Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        btnTimer = findViewById(R.id.btnTimer);

        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourofDay, int minutes) {
               Hour = hourofDay;
               Minute = minutes;
            }
        });
            btnTimer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Set  Alarm" + Hour + " 1 " + Minute, Toast.LENGTH_SHORT).show();
                    setTimer();
                    notification();
                }

                private void setTimer() {
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    Date date = new Date();

                    Calendar cal_alarm = Calendar.getInstance();
                    Calendar cal_now = Calendar.getInstance();

                    cal_now.setTime(date);
                    cal_alarm.setTime(date);

                    if(cal_alarm.before(cal_now)){
                        cal_alarm.add(Calendar.DATE, 1);
                    }

                    Intent i = new Intent(MainActivity.this, MyBroadcastReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, PendingIntent.FLAG_IMMUTABLE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),pendingIntent);
                }

                private void notification() {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        CharSequence name = "Alarm Reminders";
                        String description = "Hey Wake UP!!";
                        int importance = NotificationManager.IMPORTANCE_DEFAULT;

                        NotificationChannel channel = new NotificationChannel("Notify", name, importance);
                        channel.setDescription(description);

                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                }
            };

    })



    ;

}
}