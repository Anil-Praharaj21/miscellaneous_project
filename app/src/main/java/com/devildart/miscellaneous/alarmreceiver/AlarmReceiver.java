package com.devildart.miscellaneous.alarmreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ROLG", "onReceive: alarmReceiver");
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }
}
