package com.yway.scomponent.commonres.view.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;


/**
 * @ProjectName: Android-Project
 * @Package: com.fatalsignal.outsourcing.commonres.view
 * @ClassName: PerfectArcView
 * @Description:
 * @Author: Yuanweiwei
 * @CreateDate: 2020/1/13 14:24
 * @UpdateUser:
 * @UpdateDate: 2020/1/13 14:24
 * @UpdateRemark:
 * @Version: 1.0
 */
public class PerfectArcImageView extends AppCompatImageView {
    private Paint mPaint;
    private int mHeight;
    private int mWidth;
    private RectF mRect = new RectF();
    private Point mCircleCenter;
    private float mRadius;

    public PerfectArcImageView(Context context) {
        super(context);
        init();
    }

    public PerfectArcImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PerfectArcImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mCircleCenter = new Point();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight();
        int width = getWidth();
        mWidth = width;
        // 半径
        mRadius = width * 2;
        // 矩形
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width;
        mRect.bottom = mHeight;
        // 圆心坐标
        mCircleCenter.x = width / 2;
        mCircleCenter.y = mHeight - width * 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(mCircleCenter.x, mCircleCenter.y, mRadius, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            canvas.drawBitmap(bitmap, null, mRect, mPaint);
        }
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

}
