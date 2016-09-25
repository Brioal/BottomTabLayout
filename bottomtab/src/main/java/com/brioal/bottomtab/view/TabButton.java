package com.brioal.bottomtab.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.brioal.bottomtab.R;
import com.brioal.bottomtab.util.SizeUtil;

/**
 * 按钮组件
 * Created by Brioal on 2016/8/18.
 */

public class TabButton extends View {
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    private int mHeight; // 按钮高度
    private int mIconHeight; //Icon高度
    private int mColorSelect; //选中颜色
    private int mTextSize; //文字大小
    private int mColorNormal; //文字选中颜色
    private int mMaxExCircleRadius; //外圆半径
    private int mExCircleColor; //外圆颜色
    private int mMaxInCircleRadius; //内圆半径
    private int mInCircleColor; //内圆颜色
    private int mMaxHeight;
    private String mText; //文字
    private Paint mPaint;
    private Drawable mDrawable;

    private int mColor;
    private int mExRadius;
    private int mInRadius;
    private long mDuration;
    private int mType = 0;
    private int mNews = -1;

    private boolean isSelected = false;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    //设置未选中颜色
    public void setColorNormal(int colorNormal) {
        mColorNormal = colorNormal;
    }

    //设置选中颜色
    public void setColorSelect(int colorSelect) {
        mColorSelect = colorSelect;
    }

    //设置外圆颜色
    public void setExCircleColor(int exCircleColor) {
        mExCircleColor = exCircleColor;
    }

    //设置内圆颜色
    public void setInCircleColor(int inCircleColor) {
        mInCircleColor = inCircleColor;
    }

    //设置文字大小
    public void setTextSize(int textSize) {
        mTextSize = (int) SizeUtil.Dp2Px(getContext(), textSize);
    }

    //设置未读消息
    public void setNews(int news) {
        this.mNews = news;
        invalidate();
    }

    //设置动画时长
    public void setDuration(long duration) {
        mDuration = duration;
    }

    //设置Icon
    public void setIcon(Drawable drawable) {
        mDrawable = drawable;
    }

    //设置文字
    public void setText(String text) {
        this.mText = text;
    }

    public void setType(int type) {
        mType = type;
    }

    private void init() {
        mTextSize = (int) SizeUtil.Dp2Px(getContext(), 11);
        mExCircleColor = getResources().getColor(R.color.colorExCircle);
        mInCircleColor = getResources().getColor(R.color.colorInCircle);

        mColorSelect = getResources().getColor(R.color.colorSelect);
        mColorNormal = getResources().getColor(R.color.colorNormal);


        mText = "按钮";
        mDrawable = getResources().getDrawable(R.mipmap.ic_launcher);
        mMaxHeight = (int) SizeUtil.Dp2Px(getContext(), 50);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mColor = mColorNormal;
        mExRadius = 0;
        mInRadius = 0;
        mDuration = 160;
    }

    //设置最大高度
    public void setMaxHeight(int maxHeight) {
        mMaxHeight = (int) SizeUtil.Dp2Px(getContext(), maxHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mHeight = Math.min(heightSize, mMaxHeight);
        setMeasuredDimension(widthSize, mHeight);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!isSelected) {
                    startAnimation();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    //开始动画
    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(mDuration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (float) valueAnimator.getAnimatedValue();
                mExRadius = (int) (mMaxExCircleRadius * progress);
                mInRadius = (int) (mMaxInCircleRadius * progress);
                invalidate();
            }
        });
        mColor = mColorSelect;
        animator.start();
        isSelected = true;
    }

    //恢复
    public void reset() {
        mColor = mColorNormal;
        mExRadius = 0;
        mInRadius = 0;
        invalidate();
        isSelected = false;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mIconHeight = mHeight * 3 / 6;
        mMaxExCircleRadius = w / 2;
        mMaxInCircleRadius = (int) (mMaxExCircleRadius - SizeUtil.Dp2Px(getContext(), 10));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mExRadius != 0 || mInRadius != 0) {
            canvas.save();
            canvas.translate(getWidth() / 2, getHeight() / 2);
            mPaint.setColor(mExCircleColor);
            //绘制外圆
            canvas.drawCircle(0, 0, mExRadius, mPaint);
            mPaint.setColor(mInCircleColor);
            //绘制内圆
            canvas.drawCircle(0, 0, mInRadius, mPaint);
            canvas.restore();
        }
        if (mType != CENTER) {
            mPaint.setColor(mInCircleColor);
            if (mType == LEFT) {
                canvas.drawRect(0, 0, mExRadius, getHeight(), mPaint);
            } else {
                canvas.drawRect(mExRadius, 0, mExRadius * 2, getHeight(), mPaint);
            }
        }

        //绘制Icon
        canvas.save();
        canvas.translate(getWidth() / 2, mIconHeight / 2);
        int r = mIconHeight / 2;
        mDrawable.setBounds(-r, -r+7, r, r+7);
        mDrawable.setColorFilter(mColor, PorterDuff.Mode.SRC_IN);
        mDrawable.draw(canvas);
        canvas.restore();
        canvas.save();
        //绘制未读消息
        if (mNews != -1) {
            canvas.translate(getWidth() / 2 + r, mIconHeight / 2 - 5);
            mPaint.setColor(Color.RED);
            canvas.drawCircle(0, 0, r * 2 / 3, mPaint);
            String text = mNews + "";
            mPaint.setTextSize(SizeUtil.Dp2Px(getContext(), 10));
            Rect bound = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), bound);
            mPaint.setColor(Color.WHITE);
            canvas.drawText(text, -(bound.right - bound.left) / 2, -(bound.bottom + bound.top) / 2, mPaint);
        }
        canvas.restore();

        //绘制文字
        mPaint.setColor(mColor);
        mPaint.setTextSize(mTextSize);
        canvas.save();
        canvas.translate(getWidth() / 2, mIconHeight + (getHeight() - mIconHeight) / 2);
        Rect bound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), bound);
        canvas.drawText(mText, -(bound.right - bound.left) / 2, -(bound.bottom + bound.top) / 2, mPaint);
        canvas.restore();


    }
}
