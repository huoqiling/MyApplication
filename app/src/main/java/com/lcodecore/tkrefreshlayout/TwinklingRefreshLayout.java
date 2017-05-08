package com.lcodecore.tkrefreshlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.zyfx_.myapplication.R;
import com.lcodecore.tkrefreshlayout.Footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.GoogleDotView;
import com.lcodecore.tkrefreshlayout.processor.AnimProcessor;
import com.lcodecore.tkrefreshlayout.processor.IDecorator;
import com.lcodecore.tkrefreshlayout.processor.OverScrollDecorator;
import com.lcodecore.tkrefreshlayout.processor.RefreshProcessor;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;

/**
 * Created by lcodecore on 16/3/2.
 */
public class TwinklingRefreshLayout extends RelativeLayout implements PullListener {

    //波浪的高度,最大扩展高度
    protected float mMaxHeadHeight;
    protected float mMaxBottomHeight;

    //头部的高度
    protected float mHeadHeight;

    //允许的越界回弹的高度
    protected float mOverScrollHeight;

    //子控件
    private View mChildView;

    //头部layout
    protected FrameLayout mHeadLayout;

    //整个头部
    private FrameLayout mExtraHeadLayout;
    //附加顶部高度
    private int mExHeadHeight = 0;

    private IHeaderView mHeadView;
    private IBottomView mBottomView;

    //底部高度
    private float mBottomHeight;

    //底部layout
    private FrameLayout mBottomLayout;


    //是否刷新视图可见
    protected boolean isRefreshVisible = false;

    //是否加载更多视图可见
    protected boolean isLoadingVisible = false;

    //是否需要加载更多,默认需要
    protected boolean enableLoadmore = true;
    //是否需要下拉刷新,默认需要
    protected boolean enableRefresh = true;

    //是否在越界回弹的时候显示下拉图标
    protected boolean isOverScrollTopShow = true;
    //是否在越界回弹的时候显示上拉图标
    protected boolean isOverScrollBottomShow = true;

    //是否隐藏刷新控件,开启越界回弹模式(开启之后刷新控件将隐藏)
    protected boolean isPureScrollModeOn = false;

    //是否自动加载更多
    protected boolean autoLoadMore = false;

    //是否开启悬浮刷新模式
    protected boolean floatRefresh = false;

    //是否允许进入越界回弹模式
    protected boolean enableOverScroll = true;

    private CoContext cp;
    private int mTouchSlop;

    public TwinklingRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public TwinklingRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwinklingRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TwinklingRefreshLayout, defStyleAttr, 0);
        try {
            mMaxHeadHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_max_head_height, (int) DensityUtil.dp2px(context, 120));
            mHeadHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_head_height, (int) DensityUtil.dp2px(context, 80));
            mMaxBottomHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_max_bottom_height, (int) DensityUtil.dp2px(context, 120));
            mBottomHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_bottom_height, (int) DensityUtil.dp2px(context, 60));
            mOverScrollHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_overscroll_height, (int) mHeadHeight);
            enableLoadmore = a.getBoolean(R.styleable.TwinklingRefreshLayout_tr_enable_loadmore, true);
            isPureScrollModeOn = a.getBoolean(R.styleable.TwinklingRefreshLayout_tr_pureScrollMode_on, false);
            isOverScrollTopShow = a.getBoolean(R.styleable.TwinklingRefreshLayout_tr_overscroll_top_show, true);
            isOverScrollBottomShow = a.getBoolean(R.styleable.TwinklingRefreshLayout_tr_overscroll_bottom_show, true);
            enableOverScroll = a.getBoolean(R.styleable.TwinklingRefreshLayout_tr_enable_overscroll, true);
        } finally {
            a.recycle();
        }

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        cp = new CoContext();

        addHeader();
        addFooter();

        setPullListener(this);
    }

    private void addHeader() {
        FrameLayout headViewLayout = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        layoutParams.addRule(ALIGN_PARENT_TOP);

        FrameLayout extraHeadLayout = new FrameLayout(getContext());
        extraHeadLayout.setId(R.id.ex_header);
        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        this.addView(extraHeadLayout, layoutParams2);
        this.addView(headViewLayout, layoutParams);

        mExtraHeadLayout = extraHeadLayout;
        mHeadLayout = headViewLayout;

        if (mHeadView == null) setHeaderView(new GoogleDotView(getContext()));
    }

    private void addFooter() {
        FrameLayout bottomViewLayout = new FrameLayout(getContext());
        LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams2.addRule(ALIGN_PARENT_BOTTOM);
        bottomViewLayout.setLayoutParams(layoutParams2);

        mBottomLayout = bottomViewLayout;
        this.addView(mBottomLayout);

        if (mBottomView == null) {
            BallPulseView ballPulseView = new BallPulseView(getContext());
            setBottomView(ballPulseView);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获得子控件
        //onAttachedToWindow方法中mChildView始终是第0个child，把header、footer放到构造函数中，mChildView最后被inflate
        mChildView = getChildAt(3);

        cp.init();
        decorator = new OverScrollDecorator(cp, new RefreshProcessor(cp));
        initGestureDetector();
    }

    private IDecorator decorator;
    private GestureDetector gestureDetector;

    private void initGestureDetector() {
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent ev) {
                decorator.onFingerDown(ev);
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                decorator.onFingerScroll(e1, e2, distanceX, distanceY, vy);
                return false;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                decorator.onFingerFling(e1, e2, velocityX, velocityY);
                return false;
            }
        });
    }

    private VelocityTracker moveTracker;
    private int mPointerId;
    private float vy;

    private void obtainTracker(MotionEvent event) {
        if (null == moveTracker) {
            moveTracker = VelocityTracker.obtain();
        }
        moveTracker.addMovement(event);
    }

    private void releaseTracker() {
        if (null != moveTracker) {
            moveTracker.clear();
            moveTracker.recycle();
            moveTracker = null;
        }
    }

    /*************************************
     * 触摸事件处理
     *****************************************/
    int mMaxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //1.监听fling动作 2.获取手指滚动速度（存在滚动但非fling的状态）
        //TODO 考虑是否可以去除GestureDetector只保留VelocityTracker
        obtainTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                moveTracker.computeCurrentVelocity(1000, mMaxVelocity);
                vy = moveTracker.getYVelocity(mPointerId);
                releaseTracker();
                break;
        }
        gestureDetector.onTouchEvent(event);

        return super.dispatchTouchEvent(event);
    }

    /**
     * 拦截事件
     *
     * @return return true时,ViewGroup的事件有效,执行onTouchEvent事件
     * return false时,事件向下传递,onTouchEvent无效
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = decorator.interceptTouchEvent(ev);
        return intercept || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean consume = decorator.dealTouchEvent(e);
        return consume || super.onTouchEvent(e);
    }

    /*************************************
     * 开放api区
     *****************************************/
    //主动刷新
    public void startRefresh() {
        cp.startRefresh();
    }

    //主动加载跟多
    public void startLoadMore() {
        cp.startLoadMore();
    }

    /**
     * 刷新结束
     */
    public void finishRefreshing() {
        cp.finishRefreshing();
    }

    /**
     * 加载更多结束
     */
    public void finishLoadmore() {
        cp.finishLoadmore();
    }

    /**
     * 手动设置刷新View
     */
    public void setTargetView(View targetView) {
        if (targetView != null) mChildView = targetView;
    }

    /**
     * 手动设置RefreshLayout的装饰
     */
    public void setDecorator(IDecorator decorator1) {
        if (decorator1 != null) decorator = decorator1;
    }

    /**
     * 设置头部View
     */
    public void setHeaderView(final IHeaderView headerView) {
        if (headerView != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    mHeadLayout.removeAllViewsInLayout();
                    mHeadLayout.addView(headerView.getView());
                }
            });
            mHeadView = headerView;
        }
    }

    /**
     * 设置固定在顶部的header
     */
    public void addFixedExHeader(final View view) {

        post(new Runnable() {
            @Override
            public void run() {
                if (view != null && mExtraHeadLayout != null) {
                    mExtraHeadLayout.addView(view);
                    mExtraHeadLayout.bringToFront();
                    cp.onAddExHead();
                    cp.setExHeadFixed();
                }
            }
        });
    }

    /**TODO 适配可以随界面滚动的Header
     public void addNormalExHeader(View view) {
     if (view != null && mExtraHeadLayout != null) {
     mExtraHeadLayout.addView(view);
     cp.onAddExHead();
     cp.setExHeadNormal();
     }
     }
     **/

    /**
     * 获取额外附加的头部
     */
    public View getExtraHeaderView() {
        return mExtraHeadLayout;
    }

    /**
     * 设置底部View
     */
    public void setBottomView(final IBottomView bottomView) {
        if (bottomView != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    mBottomLayout.removeAllViewsInLayout();
                    mBottomLayout.addView(bottomView.getView());
                }
            });
            mBottomView = bottomView;
        }
    }

    public void setFloatRefresh(boolean ifOpenFloatRefreshMode) {
        floatRefresh = ifOpenFloatRefreshMode;
        post(new Runnable() {
            @Override
            public void run() {
                if (mHeadLayout != null) mHeadLayout.bringToFront();
            }
        });
    }

    /**
     * 设置wave的下拉高度
     */
    public void setMaxHeadHeight(float maxHeightDp) {
        this.mMaxHeadHeight = DensityUtil.dp2px(getContext(), maxHeightDp);
    }

    /**
     * 设置下拉头的高度
     */
    public void setHeaderHeight(float headHeightDp) {
        this.mHeadHeight = DensityUtil.dp2px(getContext(), headHeightDp);
    }

    public void setMaxBottomHeight(float maxBottomHeight){
        mMaxBottomHeight = DensityUtil.dp2px(getContext(),maxBottomHeight);
    }

    /**
     * 设置底部高度
     */
    public void setBottomHeight(float bottomHeightDp) {
        this.mBottomHeight = DensityUtil.dp2px(getContext(), bottomHeightDp);
    }

    /**
     * 是否允许加载更多
     */
    public void setEnableLoadmore(boolean enableLoadmore1) {
        enableLoadmore = enableLoadmore1;
        if (mBottomView != null) {
            if (enableLoadmore) mBottomView.getView().setVisibility(VISIBLE);
            else mBottomView.getView().setVisibility(GONE);
        }
    }

    /**
     * 是否允许下拉刷新
     */
    public void setEnableRefresh(boolean enableRefresh1) {
        this.enableRefresh = enableRefresh1;
    }

    /**
     * 是否允许越界时显示刷新控件
     */
    public void setOverScrollTopShow(boolean isOverScrollTopShow) {
        this.isOverScrollTopShow = isOverScrollTopShow;
    }

    public void setOverScrollBottomShow(boolean isOverScrollBottomShow) {
        this.isOverScrollBottomShow = isOverScrollBottomShow;
    }

    public void setOverScrollRefreshShow(boolean isOverScrollRefreshShow) {
        this.isOverScrollTopShow = isOverScrollRefreshShow;
        this.isOverScrollBottomShow = isOverScrollRefreshShow;
    }

    /**
     * 是否允许开启越界回弹模式
     */
    public void setEnableOverScroll(boolean enableOverScroll1) {
        this.enableOverScroll = enableOverScroll1;
    }

    /**
     * 是否开启纯净的越界回弹模式,开启时刷新和加载更多控件不显示
     */
    public void setPureScrollModeOn(boolean pureScrollModeOn) {
        isPureScrollModeOn = pureScrollModeOn;
        if (pureScrollModeOn) {
            isOverScrollTopShow = false;
            isOverScrollBottomShow = false;
            setMaxHeadHeight(mOverScrollHeight);
            setHeaderHeight(mOverScrollHeight);
            setMaxBottomHeight(mOverScrollHeight);
            setBottomHeight(mOverScrollHeight);
        }
    }

    /**
     * 设置越界高度
     */
    public void setOverScrollHeight(float overScrollHeightDp) {
        this.mOverScrollHeight = DensityUtil.dp2px(getContext(), overScrollHeightDp);
    }

    /**
     * 设置OverScroll时自动加载更多
     *
     * @param ifAutoLoadMore 为true表示底部越界时主动进入加载跟多模式，否则直接回弹
     */
    public void setAutoLoadMore(boolean ifAutoLoadMore) {
        autoLoadMore = ifAutoLoadMore;
        setEnableLoadmore(true);
    }

    /**
     * 设置刷新控件监听器
     */
    private RefreshListenerAdapter refreshListener;

    public void setOnRefreshListener(RefreshListenerAdapter refreshListener) {
        if (refreshListener != null) {
            this.refreshListener = refreshListener;
        }
    }

    //设置拖动屏幕的监听器
    private PullListener pullListener;

    private void setPullListener(PullListener pullListener) {
        this.pullListener = pullListener;
    }

    @Override
    public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {
        mHeadView.onPullingDown(fraction, mMaxHeadHeight, mHeadHeight);
        if (!enableRefresh) return;
        if (refreshListener != null) refreshListener.onPullingDown(refreshLayout, fraction);
    }

    @Override
    public void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction) {
        mBottomView.onPullingUp(fraction, mMaxHeadHeight, mHeadHeight);
        if (!enableLoadmore) return;
        if (refreshListener != null) refreshListener.onPullingUp(refreshLayout, fraction);
    }

    @Override
    public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
        mHeadView.onPullReleasing(fraction, mMaxHeadHeight, mHeadHeight);
        if (!enableRefresh) return;
        if (refreshListener != null)
            refreshListener.onPullDownReleasing(refreshLayout, fraction);
    }

    @Override
    public void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
        mBottomView.onPullReleasing(fraction, mMaxBottomHeight, mBottomHeight);
        if (!enableLoadmore) return;
        if (refreshListener != null) refreshListener.onPullUpReleasing(refreshLayout, fraction);
    }

    @Override
    public void onRefresh(TwinklingRefreshLayout refreshLayout) {
        mHeadView.startAnim(mMaxHeadHeight, mHeadHeight);
        if (refreshListener != null) refreshListener.onRefresh(refreshLayout);
    }

    @Override
    public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
        mBottomView.startAnim(mMaxBottomHeight, mBottomHeight);
        if (refreshListener != null) refreshListener.onLoadMore(refreshLayout);
    }

    @Override
    public void onFinishRefresh() {
        if (!isRefreshVisible) return;
        mHeadView.onFinish(new OnAnimEndListener() {
            @Override
            public void onAnimEnd() {
                cp.finishRefreshAfterAnim();
            }
        });
    }

    @Override
    public void onFinishLoadMore() {
        if (!isLoadingVisible) return;
        mBottomView.onFinish();
    }

    @Override
    public void onRefreshCanceled() {
        if (refreshListener != null) refreshListener.onRefreshCanceled();
    }

    @Override
    public void onLoadmoreCanceled() {
        if (refreshListener != null) refreshListener.onLoadmoreCanceled();
    }


    public class CoContext {
        private AnimProcessor animProcessor;

        private final static int PULLING_TOP_DOWN = 0;
        private final static int PULLING_BOTTOM_UP = 1;
        private int state = PULLING_TOP_DOWN;

        private static final int EX_MODE_NORMAL = 0;
        private static final int EX_MODE_FIXED = 1;
        private int exHeadMode = EX_MODE_NORMAL;


        public CoContext() {
            animProcessor = new AnimProcessor(this);
        }

        public void init() {
            if (isPureScrollModeOn) {
                setOverScrollTopShow(false);
                setOverScrollBottomShow(false);
                if (mHeadLayout != null) mHeadLayout.setVisibility(GONE);
                if (mBottomLayout != null) mBottomLayout.setVisibility(GONE);
            }
        }

        public AnimProcessor getAnimProcessor() {
            return animProcessor;
        }

        public float getMaxHeadHeight() {
            return mMaxHeadHeight;
        }

        public int getHeadHeight() {
            return (int) mHeadHeight;
        }

        public int getExtraHeadHeight() {
            return mExtraHeadLayout.getHeight();
        }

        public int getMaxBottomHeight(){
            return (int) mMaxBottomHeight;
        }
        public int getBottomHeight() {
            return (int) mBottomHeight;
        }

        public int getOsHeight() {
            return (int) mOverScrollHeight;
        }

        public View getTargetView() {
            return mChildView;
        }

        public View getHeader() {
            return mHeadLayout;
        }

        public View getFooter() {
            return mBottomLayout;
        }

        public int getTouchSlop() {
            return mTouchSlop;
        }

        public void resetHeaderView() {
            if (mHeadView != null) mHeadView.reset();
        }

        public void resetBottomView() {
            if (mBottomView != null) mBottomView.reset();
        }

        public View getExHead() {
            return mExtraHeadLayout;
        }

        public void setExHeadNormal() {
            exHeadMode = EX_MODE_NORMAL;
        }

        public void setExHeadFixed() {
            exHeadMode = EX_MODE_FIXED;
        }

        public boolean isExHeadNormal() {
            return exHeadMode == EX_MODE_NORMAL;
        }

        public boolean isExHeadFixed() {
            return exHeadMode == EX_MODE_FIXED;
        }

        /**
         * 在添加附加Header前锁住，阻止一些额外的位移动画
         */
        private boolean isExHeadLocked = true;

        public boolean isExHeadLocked() {
            return isExHeadLocked;
        }

        //添加了额外头部时触发
        public void onAddExHead() {
            isExHeadLocked = false;
            LayoutParams params = (LayoutParams) mChildView.getLayoutParams();
            params.addRule(BELOW, mExtraHeadLayout.getId());
            mChildView.setLayoutParams(params);
            requestLayout();
        }


        /**
         * 主动刷新、加载更多、结束
         */
        public void startRefresh() {
            post(new Runnable() {
                @Override
                public void run() {
                    setStatePTD();
                    if (!isPureScrollModeOn && mChildView != null) {
                        setRefreshing(true);
                        animProcessor.animHeadToRefresh();
                    }
                }
            });
        }

        public void startLoadMore() {
            post(new Runnable() {
                @Override
                public void run() {
                    setStatePBU();
                    if (!isPureScrollModeOn && mChildView != null) {
                        setLoadingMore(true);
                        animProcessor.animBottomToLoad();
                    }
                }
            });
        }

        public void finishRefreshing() {
            onFinishRefresh();
        }

        public void finishRefreshAfterAnim() {
            if (isRefreshVisible() && mChildView != null) {
                setRefreshing(false);
                animProcessor.animHeadBack();
            }
        }

        public void finishLoadmore() {
            onFinishLoadMore();
            if (isLoadingVisible() && mChildView != null) {
                setLoadingMore(false);
                animProcessor.animBottomBack();
            }
        }

        //TODO 支持分别设置头部或者顶部允许越界
        //private boolean enableOverScrollTop = false, enableOverScrollBottom = false;

        public boolean enableOverScroll() {
            return enableOverScroll;
        }

        public boolean allowPullDown() {
            return enableRefresh || enableOverScroll;
        }

        public boolean allowPullUp() {
            return enableLoadmore || enableOverScroll;
        }

        public boolean enableRefresh(){
            return enableRefresh;
        }

        public boolean enableLoadmore(){
            return enableLoadmore;
        }

        public boolean allowOverScroll() {
            return (!isRefreshVisible && !isLoadingVisible);
        }

        public boolean isRefreshVisible() {
            return isRefreshVisible;
        }

        public boolean isLoadingVisible() {
            return isLoadingVisible;
        }

        public void setRefreshing(boolean refreshing) {
            isRefreshVisible = refreshing;
        }

        public void setLoadingMore(boolean loadingMore) {
            isLoadingVisible = loadingMore;
        }

        public boolean isOpenFloatRefresh() {
            return floatRefresh;
        }

        public boolean autoLoadMore() {
            return autoLoadMore;
        }

        public boolean isPureScrollModeOn() {
            return isPureScrollModeOn;
        }

        public boolean isOverScrollTopShow() {
            return isOverScrollTopShow;
        }

        public boolean isOverScrollBottomShow() {
            return isOverScrollBottomShow;
        }

        public void onPullingDown(float offsetY) {
            pullListener.onPullingDown(TwinklingRefreshLayout.this, offsetY / mHeadHeight);
        }

        public void onPullingUp(float offsetY) {
            pullListener.onPullingUp(TwinklingRefreshLayout.this, offsetY / mBottomHeight);
        }

        public void onRefresh() {
            pullListener.onRefresh(TwinklingRefreshLayout.this);
        }

        public void onLoadMore() {
            pullListener.onLoadMore(TwinklingRefreshLayout.this);
        }

        public void onFinishRefresh() {
            pullListener.onFinishRefresh();
        }

        public void onFinishLoadMore() {
            pullListener.onFinishLoadMore();
        }

        public void onPullDownReleasing(float offsetY) {
            pullListener.onPullDownReleasing(TwinklingRefreshLayout.this, offsetY / mHeadHeight);
        }

        public void onPullUpReleasing(float offsetY) {
            pullListener.onPullUpReleasing(TwinklingRefreshLayout.this, offsetY / mBottomHeight);
        }

        public void onRefreshCanceled() {
            pullListener.onRefreshCanceled();
        }

        public void onLoadmoreCanceled() {
            pullListener.onLoadmoreCanceled();
        }

        public void setStatePTD() {
            state = PULLING_TOP_DOWN;
        }

        public void setStatePBU() {
            state = PULLING_BOTTOM_UP;
        }

        public boolean isStatePTD() {
            return PULLING_TOP_DOWN == state;
        }

        public boolean isStatePBU() {
            return PULLING_BOTTOM_UP == state;
        }
    }
}
