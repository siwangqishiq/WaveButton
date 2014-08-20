package com.xinlan.wavebtn;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

public class WaveView extends View 
{
    public static final int MAX_WAVE=3;
    private boolean isPlay = false;
    private LinkedList<Circle> dataList = new LinkedList<Circle>();
    private int max_alpha = 140;
    private float dRadius = 1.5f;

    public WaveView(Context context)
    {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {

    }
    
    public boolean getIsPlay()
    {
        return isPlay;
    }
    
    public void play()
    {
        Circle circle = circleFactory();
        dataList.add(circle);
        isPlay = true;
        invalidate();
    }
    
    public void stop()
    {
        isPlay = false;
        dataList.clear();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        if(isPlay)
        {
            update(canvas);
            invalidate();
        }
    }

    private void update(Canvas canvas)
    {
        int width = getWidth();
        int height = getHeight();
        int centerX = width >> 1;
        int centerY = height >> 1;

        for (int i = 0; i < dataList.size(); i++)
        {
            Circle circle = dataList.get(i);
            circle.radius += dRadius;
            if (circle.radius >= centerX)
            {
                dataList.remove(i);
                continue;
            }
            //透明度 与 半径的函数关系式
            int alpha = (int)(max_alpha *(1- (circle.radius/centerX)));
            circle.paint.setAlpha(alpha);
            
            circle.mRadialGradient = new RadialGradient(centerX, centerY,
                    circle.radius,
                    new int[] { Color.TRANSPARENT, Color.WHITE }, null,
                    TileMode.REPEAT);
             // 绘制环形渐变
            circle.paint.setShader(circle.mRadialGradient);
            canvas.drawCircle(centerX, centerY, circle.radius, circle.paint);
        }// end for i

        if (dataList.size() > 0)
        {
            Circle lastCircle = dataList.get(0);
            int maxDistance = centerX / MAX_WAVE;
            if (lastCircle.radius > maxDistance)
            {
                Circle circle = circleFactory();
                dataList.addFirst(circle);
            }
        }// end if

    }

    private Circle circleFactory()
    {
        Circle c = new Circle();
        c.radius = 30;

        return c;
    }

    private final class Circle
    {
        float radius;
        Paint paint;
        RadialGradient mRadialGradient;

        public Circle()
        {
            paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setAlpha(max_alpha);
            paint.setAntiAlias(true);

            radius = 1;
        }
    }
}// end class
