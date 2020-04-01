package com.devildart.miscellaneous.alarm_notification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.devildart.miscellaneous.R;

public class AlarmNotificationService extends Service implements View.OnTouchListener {

    private static final String TAG = AlarmNotificationService.class.getSimpleName();

    private WindowManager windowManager;
    private MediaPlayer mediaPlayer;

    private View floatyView;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        addOverlayView();
    }

    private void addOverlayView() {

        final WindowManager.LayoutParams params;
        int layoutParamsType;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            layoutParamsType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParamsType = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                layoutParamsType,
                0,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER | Gravity.START;
        params.x = 0;
        params.y = 0;

        FrameLayout interceptorLayout = new FrameLayout(this) {

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return super.dispatchKeyEvent(event);
            }
        };

        LayoutInflater inflater = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));

        if (inflater != null) {
            floatyView = inflater.inflate(R.layout.alarm_overlay_payment_notification, interceptorLayout);
            floatyView.setOnTouchListener(this);
            windowManager.addView(floatyView, params);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.payment);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer1) {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatyView != null) {
            windowManager.removeView(floatyView);
            floatyView = null;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        onDestroy();
        return true;
    }
}
