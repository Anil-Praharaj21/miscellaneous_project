package com.devildart.miscellaneous;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LoadingView extends View {

    private int height, width;
    private Paint bgPaint, dotPaint;
    private int radius1 = 40, radius2 = 30, radius3 = 20, radius4 = 10;
    private int maxRadius = 40;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.TRANSPARENT);

        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setColor(Color.parseColor("#97B0BA"));
        dotPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        height = 200;
        width = MeasureSpec.getSize(widthMeasureSpec);

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, modeH));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawDots(canvas);
    }

    private void drawDots(Canvas canvas) {
        int mid = width / 2;
        canvas.drawCircle( mid - 150, height / 2, radius1, dotPaint);
        canvas.drawCircle( mid - 50, height / 2, radius2, dotPaint);
        canvas.drawCircle(mid + 50, height / 2, radius3, dotPaint);
        canvas.drawCircle( mid + 150, height / 2, radius4, dotPaint);

        if (radius1 > maxRadius) {
            radius1 = 0;
        }
        radius1++;
        if (radius2 > maxRadius) {
            radius2 = 0;
        }
        radius2++;
        if (radius3 > maxRadius) {
            radius3 = 0;
        }
        radius3++;
        if (radius4 > maxRadius) {
            radius4 = 0;
        }
        radius4++;
        invalidate();
    }
}
