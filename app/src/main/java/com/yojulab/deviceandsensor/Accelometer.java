package com.yojulab.deviceandsensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by sanghunoh on 11/18/18.
 */

public class Accelometer extends View {
    float x_accelometer = 0;
    float y_accelometer = 0;
    float z_accelometer = 0;

    public Accelometer(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.CYAN);
        paint.setTextSize(30);

        paint.setStrokeWidth((float) (Math.abs(z_accelometer)*3.0));
        canvas.drawLine(200,200,200,400, paint);
        canvas.drawText("" + z_accelometer, 200, 200, paint);

        paint.setStrokeWidth((float) (Math.abs(x_accelometer)*3.0));
        canvas.drawLine(200,400,400,400, paint);
        canvas.drawText("" + x_accelometer, 400, 400, paint);

        paint.setStrokeWidth((float) (Math.abs(y_accelometer)*3.0));
        canvas.drawLine(100,250,200,400, paint);
        canvas.drawText("" + y_accelometer, 100, 250, paint);
    }

    public void setX_accelometer(float x_accelometer) {
        this.x_accelometer = x_accelometer;
    }

    public void setY_accelometer(float y_accelometer) {
        this.y_accelometer = y_accelometer;
    }

    public void setZ_accelometer(float z_accelometer) {
        this.z_accelometer = z_accelometer;
    }

}
