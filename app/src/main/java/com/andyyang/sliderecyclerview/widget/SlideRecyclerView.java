package com.andyyang.sliderecyclerview.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by AndyYang
 * date:2018/1/8.
 * mail:andyyang2014@126.com
 */
public class SlideRecyclerView extends XRecyclerView {

    private float mTouchSlop;
    private SlideItem mTouchView = null;//记录当前点击的item View
    private float mDownX;//x轴坐标
    private float mDownY;//y轴坐标
    private int mTouchState;//记录点击状态
    private int mTouchPosition;//记录点击位置
    private static final int TOUCH_STATE_NONE = 0; //按下状态
    private static final int TOUCH_STATE_X = 1;//横滑状态
    private static final int TOUCH_STATE_Y = 2;//竖滑状态
    //判断横竖滑动的最小值
    private static final int MAX_Y = 5;
    private static final int MAX_X = 3;
    private OnItemClickListener mListener;
    private float xUp, yUp;

    public SlideRecyclerView(Context context) {
        super(context);
    }

    public SlideRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SlideRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() != MotionEvent.ACTION_DOWN && mTouchView == null)
            return super.onTouchEvent(ev);
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按住的item的position
                int oldPosition = mTouchPosition;
                //记录位置
                mDownX = ev.getX();
                mDownY = ev.getY();
                mTouchState = TOUCH_STATE_NONE;

                //根据当前横纵坐标点获取点击的item的position
                View view = findChildViewUnder(x, y);
                if (view == null) {
                    return false;
                }

                RecyclerView.ViewHolder viewHolder = getChildViewHolder(view);
                mTouchPosition = viewHolder.getAdapterPosition();


                //判断当前点击的是否和上次点击的item是同一个，如果是同一个，并且状态是打开了的就记录状态和坐标
                //记录坐标通过Item中的downX属性
                if (mTouchPosition == oldPosition && mTouchView != null && mTouchView.isOpen()) {
                    mTouchState = TOUCH_STATE_X;
                    mTouchView.onSwipe(ev);
                    return true;
                }
                //获取当前的item的View
                final View currentView = findChildViewUnder(mDownX, mDownY);

                //如果不是同一个item 那么点击的话就关闭掉之前打开的item
                if (mTouchView != null && mTouchView.isOpen()) {
                    mTouchView.smoothCloseMenu();
                    mTouchView = null;
                    return super.onTouchEvent(ev);
                }
                //判断该view的类型
                if (currentView instanceof LinearLayout) {
                    mTouchView = (SlideItem) currentView;
                }
                if (mTouchView != null) {
                    mTouchView.onSwipe(ev);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = Math.abs((ev.getY() - mDownY));
                float dx = Math.abs((ev.getX() - mDownX));
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        //执行滑动
                        mTouchView.onSwipe(ev);
                    }
                    return true;
                } else if (mTouchState == TOUCH_STATE_NONE) {
                    //判断滑动方向，x方向执行滑动，Y方向执行滚动
                    if (Math.abs(dy) > MAX_Y) {
                        mTouchState = TOUCH_STATE_Y;
                    } else if (dx > MAX_X) {
                        mTouchState = TOUCH_STATE_X;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                xUp = x;
                yUp = y;
                //由于recyclerview的touch事件和item点击事件冲突，所以点击事件回调在此处处理
                int updx = (int) (xUp - mDownX);
                int updy = (int) (yUp - mDownY);
                if (Math.abs(updy) < mTouchSlop && Math.abs(updx) < mTouchSlop) {
                    if (mListener != null) {
                        mListener.onItemClick(mTouchPosition - 1);
                    }
                }

                //判断状态
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        mTouchView.onSwipe(ev);
                        //如过最后状态是打开 那么就重新初始化
                        if (!mTouchView.isOpen()) {
                            mTouchPosition = -1;
                            mTouchView = null;
                        }
                    }
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
