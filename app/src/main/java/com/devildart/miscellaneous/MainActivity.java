package com.devildart.miscellaneous;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devildart.miscellaneous.alarm_notification.AlarmNotificationService;
import com.devildart.miscellaneous.dialogfragment.DialogBox;
import com.devildart.miscellaneous.dialogfragment.DialogSubmitBtnCallback;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(getApplicationContext(), BroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);
        int interval = 8000;
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pendingIntent);
        } else {
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pendingIntent);
        }
        Log.d("ROLG", "onReceive: MAIN");*/

        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                /*DialogBox.getInstance(getSupportFragmentManager(), "Dialog Fragment", DialogBox.FULL_SCREEN_DIALOG)
                        .setTitleStr("Successful")
                        .setSubTitleStr("Documents has been signed and uploaded successfully. Please press okay to proceed.")
                        .setSubmitBtnStr("Okay")
                        .setStatusImageDrawable(R.drawable.payment_reminder)
                        .setSubmitBtnCallback(new DialogSubmitBtnCallback() {
                            @Override
                            public void onClick() {

                            }
                        })
                        .showDialog();*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(MainActivity.this)) {

                        // Launch service right away - the user has already previously granted permission
                        launchMainService();
                        Log.d("ROLG", "onClick: lauch");
                    }
                    else {

                        // Check that the user has granted permission, and prompt them if not
                        checkDrawOverlayPermission();
                        Log.d("ROLG", "onClick: permission");
                    }
                }
            }
        });
    }

    private void launchMainService() {

        setAlarm();
    }

    private final static int REQUEST_CODE = 10;

    private void checkDrawOverlayPermission() {

        // Checks if app already has permission to draw overlays
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {

                // If not, form up an Intent to launch the permission request
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));

                // Launch Intent, with the supplied request code
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if a request code is received that matches that which we provided for the overlay draw request
        if (requestCode == REQUEST_CODE) {

            // Double-check that the user granted it, and didn't just dismiss the request
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {

                    // Launch the service
                    launchMainService();
                } else {

                    Toast.makeText(this, "Sorry. Can't draw overlays without permission...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void setAlarm() {
        Intent myIntent = new Intent(this , AlarmNotificationService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 10);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);  //set repeating every 24 hours
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
