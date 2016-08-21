package com.brioal.bottomtab.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.brioal.bottomtab.R;
import com.brioal.bottomtab.entity.TabEntity;
import com.brioal.bottomtab.interfaces.OnTabSelectedListener;

import java.util.List;

/**
 * 按钮容纳组件
 * Created by Brioal on 2016/8/19.
 */

public class BottomLayout extends LinearLayout implements View.OnClickListener {

    List<TabEntity> mList;
    private int mColorNormal; //默认颜色
    private int mColorSelect; //选中颜色
    private int mExCircleColor; //外圆颜色
    private int mInCircleColor; //内圆颜色
    private int mTextSize; //文字大小
    private int mDuration; //动画时长
    private int mCurrentIndex = 0;
    private int mHeight = -1;

    private OnTabSelectedListener mSelectedListener;


    public BottomLayout(Context context) {
        this(context, null);
    }

    public BottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        initObtainedAttr(context, attrs);
    }

    private void initObtainedAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomLayout);
        mColorNormal = array.getColor(R.styleable.BottomLayout_colorNormal, -1);
        mColorSelect = array.getColor(R.styleable.BottomLayout_colorSelected, -1);
        mExCircleColor = array.getColor(R.styleable.BottomLayout_exCircleColor, -1);
        mInCircleColor = array.getColor(R.styleable.BottomLayout_inCircleColor, -1);
        mTextSize = (int) array.getDimension(R.styleable.BottomLayout_textSize, -1);
        mDuration = array.getInteger(R.styleable.BottomLayout_animDuration, -1);
        array.recycle();
    }

    public void setCurrentIndex(int currentIndex) {
        mCurrentIndex = currentIndex;
        ((TabButton) getChildAt(mCurrentIndex)).startAnimation();
        for (int i = 0; i < mList.size(); i++) {
            if (getChildAt(i).getId() != currentIndex) {
                ((TabButton) getChildAt(i)).reset();
            }
        }
    }

    //设置菜单高度
    public void setMenuHeight(int height) {

    }

    //设置消息数量
    public void setNews(int newSum, int index) {
        ((TabButton) getChildAt(index)).setNews(newSum);
    }

    //清除制定位置消息
    public void cleanNews(int index) {
        ((TabButton) getChildAt(index)).setNews(-1);
    }

    //设置事件监听器
    public void setSelectedListener(OnTabSelectedListener selectedListener) {
        mSelectedListener = selectedListener;
    }

    //设置未点击颜色
    public void setColorNormal(int colorNormal) {
        mColorNormal = colorNormal;
    }

    //设置点击颜色
    public void setColorSelect(int colorSelect) {
        mColorSelect = colorSelect;
    }

    //设置字体大小
    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    //设置外圆颜色
    public void setExCircleColor(int exCircleColor) {
        mExCircleColor = exCircleColor;
    }

    //设置内圆颜色
    public void setInCircleColor(int inCircleColor) {
        mInCircleColor = inCircleColor;
    }

    //设置动画时长
    public void setDuration(int duration) {
        mDuration = duration;
    }

    //设置数据源
    public void setList(List<TabEntity> list) {
        mList = list;
        if (mList != null && mList.size() >= 3) {
            for (int i = 0; i < mList.size(); i++) {
                int res = mList.get(i).getIcon();
                String text = mList.get(i).getText();
                TabButton button = new TabButton(getContext());
                if (mHeight != -1) {
                    button.setMaxHeight(mHeight);
                }
                button.setIcon(getResources().getDrawable(res));
                button.setText(text);
                int type = TabButton.CENTER;
                if (i == 0) {
                    type = TabButton.LEFT;
                } else if (i == mList.size() - 1) {
                    type = TabButton.RIGHT;
                }
                button.setType(type);
                if (mColorNormal != -1) {
                    button.setColorNormal(mColorNormal);
                }
                if (mColorSelect != -1) {
                    button.setColorSelect(mColorSelect);
                }
                if (mExCircleColor != -1) {
                    button.setExCircleColor(mExCircleColor);
                }
                if (mInCircleColor != -1) {
                    button.setInCircleColor(mInCircleColor);
                }
                if (mTextSize != -1) {
                    button.setTextSize(mTextSize);
                }
                if (mDuration != -1) {
                    button.setDuration(mDuration);
                }
                button.setId(i);
                button.setOnClickListener(this);
                button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                addView(button);
            }
            setCurrentIndex(mCurrentIndex);
        } else {
            try {
                throw new Exception("请设置两个以上的底部按钮");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (mSelectedListener != null && mCurrentIndex != id) {
            mSelectedListener.onSelected(id);
            mCurrentIndex = id;
        }
        for (int i = 0; i < mList.size(); i++) {
            if (getChildAt(i).getId() != id) {
                ((TabButton) getChildAt(i)).reset();
            }
        }
    }
}
