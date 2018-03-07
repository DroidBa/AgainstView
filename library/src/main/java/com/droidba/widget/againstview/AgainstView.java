package com.droidba.widget.againstview;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.droidba.widget.againstview.utils.DensityUtil;
import java.util.Stack;

/**
 * Created by leo on 2018/1/30.
 */

public class AgainstView extends ViewGroup {
  final int DESIGN_SPECIAL_HEIGHT = 72;//设计稿总决赛高
  final int DESIGN_THEIGHT = 448;//设计稿布局高

  final int DESIGN_WIDTH = 80;//设计稿item宽
  final int DESIGN_SPECIAL_WIDTH = 265;//设计稿总决赛宽
  final int DESIGN_TWIDTH = 375;//设计稿屏幕宽

  private int mMinItemMargin;//item之间间隙单元值

  private int mItemWidth;
  private int mSpecialItemWidth;
  private int mItemHeight;
  private int mSpecialItemHeight;
  private int mRoundMargin;
  private int mLineWidth;

  boolean mNeedRelayout;
  boolean isFristComing = true;
  AgainstAdapter mAdapter;
  DataSetObserver mDataSetObserver;
  Context mContext;

  private Recycler mRecycler;

  public AgainstView(Context context) {
    super(context, null);
  }

  public AgainstView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AgainstView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;
    setWillNotDraw(false);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    mRoundMargin = DensityUtil.dp2px(getContext(), 20);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    final int w;
    final int h;

    if (mAdapter != null) {
      resizeItemWidth(widthSize);
      resizeItemHeight(heightSize);
      w = widthSize;
      h = heightSize;
    } else {
      if (heightMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
        w = 0;
        h = 0;
      } else {
        w = widthSize;
        h = heightSize;
      }
    }
    /*设置表格的大小*/
    setMeasuredDimension(w, h);
  }

  private void resizeItemWidth(int widthSize) {
    mItemWidth = DESIGN_WIDTH * widthSize / DESIGN_TWIDTH;
    mMinItemMargin = (widthSize - 4 * mItemWidth) / 7;
    mSpecialItemWidth = DESIGN_SPECIAL_WIDTH * widthSize / DESIGN_TWIDTH;
    mLineWidth = 10 * mMinItemMargin / 8;
  }

  private void resizeItemHeight(int heightSize) {
    mItemHeight = 56 * mItemWidth / 80;
    mSpecialItemHeight = DESIGN_SPECIAL_HEIGHT * heightSize / DESIGN_THEIGHT;

    int remainH = heightSize - 2 * mItemHeight * 11 / 4 - mSpecialItemHeight;
    if (remainH < DensityUtil.dp2px(getContext(), 12)) {//需要减少item高度了

    } else {//合适
      mRoundMargin = remainH / 6;
    }
  }

  private int computeLines() {
    double maxGroup = Math.log(8) / Math.log(2);
    int i = ((int) maxGroup) + 1;
    return i * 2 + 1;
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int width = r - l;
    int height = b - t;
    if (mAdapter != null && mNeedRelayout) {
      //第一行
      int left = 0;
      int rountOneTop_Up = 0;
      int offset = 0;
      left = left + mMinItemMargin;
      for (int i = 0; i < 4; i++) {
        makeAndSetup(0, i, left, rountOneTop_Up, left + mItemWidth, rountOneTop_Up + mItemHeight);
        left = left + mMinItemMargin + mItemWidth;
        if (i == 1) {//第三个的left
          left = width - (mItemWidth + mMinItemMargin) * 2;
        } else if (i == 2) {//最后一个的left
          left = width - mItemWidth - mMinItemMargin;
        }
      }
      //第二行
      int roundTwoTop_Up = rountOneTop_Up + mItemHeight + mRoundMargin;
      offset = (3 * mMinItemMargin + mItemWidth) / 2;
      left = offset;
      for (int i = 0; i < 2; i++) {
        makeAndSetup(1, i, left, roundTwoTop_Up, left + mItemWidth, roundTwoTop_Up + mItemHeight);
        left = width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth;
      }
      //第二行画线
      draw_roundTwoUp_lines(width, rountOneTop_Up, offset, roundTwoTop_Up);

      //makeAndSetup(-2, 0, lineLeft, lineTop, lineLeft + 1, lineBottom);

      //最中间总决赛
      int roundMiddleTop = (height - mSpecialItemHeight) / 2;
      offset = (width - mSpecialItemWidth) / 2;
      left = offset;
      makeAndSetup(3, 0, left, roundMiddleTop, left + mSpecialItemWidth,
          roundMiddleTop + mSpecialItemHeight);

      //第三行
      int roundThreeTop_Up = (int) (roundMiddleTop - 1.5 * mRoundMargin - mItemHeight);
      offset = (width - mItemWidth) / 2;
      left = offset;
      makeAndSetup(2, 0, left, roundThreeTop_Up, left + mItemWidth, roundThreeTop_Up + mItemHeight);

      makeAndSetup(-2, 0, (3 * mMinItemMargin) / 2 + mItemWidth,
          roundThreeTop_Up - 1 + mItemHeight / 2, (width - mItemWidth) / 2,
          roundThreeTop_Up + mItemHeight / 2);
      makeAndSetup(-2, 0, (width - mItemWidth) / 2 + mItemWidth,
          roundThreeTop_Up - 1 + mItemHeight / 2, width - ((3 * mMinItemMargin) / 2 + mItemWidth),
          roundThreeTop_Up + mItemHeight / 2);
      makeAndSetup(-2, 0, (3 * mMinItemMargin) / 2 + mItemWidth, roundTwoTop_Up + mItemHeight,
          (3 * mMinItemMargin) / 2 + mItemWidth + 1, roundThreeTop_Up + mItemHeight / 2);
      makeAndSetup(-2, 0, width - ((3 * mMinItemMargin) / 2 + mItemWidth) - 1,
          roundTwoTop_Up + mItemHeight, width - ((3 * mMinItemMargin) / 2 + mItemWidth),
          roundThreeTop_Up + mItemHeight / 2);

      //倒数第三行
      int roundThreeTop_Down = (int) (roundMiddleTop + 1.5 * mRoundMargin + mSpecialItemHeight);
      offset = (width - mItemWidth) / 2;
      left = offset;
      makeAndSetup(2, 1, left, roundThreeTop_Down, left + mItemWidth,
          roundThreeTop_Down + mItemHeight);

      //倒数第一行
      int roundOneTop_Down = height - mItemHeight;
      left = mMinItemMargin;
      for (int i = 0; i < 4; i++) {
        makeAndSetup(0, i + 4, left, roundOneTop_Down, left + mItemWidth,
            roundOneTop_Down + mItemHeight);
        left = left + mMinItemMargin + mItemWidth;
        if (i == 1) {//第三个的left
          left = width - (mItemWidth + mMinItemMargin) * 2;
        } else if (i == 2) {//最后一个的left
          left = width - mItemWidth - mMinItemMargin;
        }
      }
      //倒数第二行
      int roundTwoTop_Down = height - mRoundMargin - mItemHeight * 2;
      offset = (3 * mMinItemMargin + mItemWidth) / 2;
      left = offset;
      for (int i = 0; i < 2; i++) {
        makeAndSetup(1, i + 2, left, roundTwoTop_Down, left + mItemWidth,
            roundTwoTop_Down + mItemHeight);
        left = width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth;
      }
      //倒数第二行画线
      draw_roundTwoDown_line(width, offset, roundOneTop_Down, roundTwoTop_Down);

      //倒数第三行画线
      makeAndSetup(-3, 0, (3 * mMinItemMargin) / 2 + mItemWidth,
          roundThreeTop_Down - 1 + mItemHeight / 2, (width - mItemWidth) / 2,
          roundThreeTop_Down + mItemHeight / 2);
      makeAndSetup(-3, 0, (width - mItemWidth) / 2 + mItemWidth,
          roundThreeTop_Down - 1 + mItemHeight / 2, width - ((3 * mMinItemMargin) / 2 + mItemWidth),
          roundThreeTop_Down + mItemHeight / 2);
      makeAndSetup(-3, 0, (3 * mMinItemMargin) / 2 + mItemWidth,
          roundThreeTop_Down + mItemHeight / 2, (3 * mMinItemMargin) / 2 + mItemWidth + 1,
          roundTwoTop_Down);
      makeAndSetup(-3, 0, width - ((3 * mMinItemMargin) / 2 + mItemWidth) - 1,
          roundThreeTop_Down + mItemHeight / 2, width - ((3 * mMinItemMargin) / 2 + mItemWidth),
          roundTwoTop_Down);

      mNeedRelayout = false;
      isFristComing = false;
      Log.d("Leo", "onLayout: " + isFristComing);
    }
  }

  //倒数第二轮画线
  private void draw_roundTwoDown_line(int width, int offset, int roundOneTop_Down,
      int roundTwoTop_Down) {
    makeAndSetup(-3, 0, offset - mLineWidth, roundTwoTop_Down + mItemHeight / 2 - 1, offset,
        roundTwoTop_Down + mItemHeight / 2);
    makeAndSetup(-3, 0, offset + mItemWidth, roundTwoTop_Down + mItemHeight / 2 - 1,
        offset + mItemWidth + mLineWidth, roundTwoTop_Down + mItemHeight / 2);
    makeAndSetup(-3, 0, width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth - mLineWidth,
        roundTwoTop_Down + mItemHeight / 2 - 1,
        width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth,
        roundTwoTop_Down + mItemHeight / 2);
    makeAndSetup(-3, 0, width - (3 * mMinItemMargin + mItemWidth) / 2,
        roundTwoTop_Down + mItemHeight / 2 - 1,
        width - (3 * mMinItemMargin + mItemWidth) / 2 + mLineWidth,
        roundTwoTop_Down + mItemHeight / 2);//所有横线

    makeAndSetup(-3, 0, offset - mLineWidth, roundTwoTop_Down + mItemHeight / 2,
        offset - mLineWidth + 1, roundOneTop_Down);
    makeAndSetup(-3, 0, offset + mItemWidth + mLineWidth - 1, roundTwoTop_Down + mItemHeight / 2,
        offset + mItemWidth + mLineWidth, roundOneTop_Down);
    makeAndSetup(-3, 0, width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth - mLineWidth,
        roundTwoTop_Down + mItemHeight / 2,
        width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth - mLineWidth + 1,
        roundOneTop_Down);
    makeAndSetup(-3, 0, width - (3 * mMinItemMargin + mItemWidth) / 2 + mLineWidth - 1,
        roundTwoTop_Down + mItemHeight / 2,
        width - (3 * mMinItemMargin + mItemWidth) / 2 + mLineWidth, roundOneTop_Down);//所有竖线
  }

  //第二轮画线
  private void draw_roundTwoUp_lines(int width, int rountOneTop_Up, int offset,
      int roundTwoTop_Up) {
    makeAndSetup(-2, 0, offset - mLineWidth, roundTwoTop_Up + mItemHeight / 2 - 1, offset,
        roundTwoTop_Up + mItemHeight / 2);
    makeAndSetup(-2, 0, offset + mItemWidth, roundTwoTop_Up + mItemHeight / 2 - 1,
        offset + mItemWidth + mLineWidth, roundTwoTop_Up + mItemHeight / 2);
    makeAndSetup(-2, 0, width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth - mLineWidth,
        roundTwoTop_Up + mItemHeight / 2 - 1,
        width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth,
        roundTwoTop_Up + mItemHeight / 2);
    makeAndSetup(-2, 0, width - (3 * mMinItemMargin + mItemWidth) / 2,
        roundTwoTop_Up + mItemHeight / 2 - 1,
        width - (3 * mMinItemMargin + mItemWidth) / 2 + mLineWidth,
        roundTwoTop_Up + mItemHeight / 2);//所有横线

    makeAndSetup(-2, 0, offset - mLineWidth, rountOneTop_Up + mItemHeight, offset - mLineWidth + 1,
        roundTwoTop_Up + mItemHeight / 2 - 1);
    makeAndSetup(-2, 0, offset + mItemWidth + mLineWidth - 1, rountOneTop_Up + mItemHeight,
        offset + mItemWidth + mLineWidth, roundTwoTop_Up + mItemHeight / 2 - 1);
    makeAndSetup(-2, 0, width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth - mLineWidth,
        rountOneTop_Up + mItemHeight,
        width - (3 * mMinItemMargin + mItemWidth) / 2 - mItemWidth - mLineWidth + 1,
        roundTwoTop_Up + mItemHeight / 2 - 1);
    makeAndSetup(-2, 0, width - (3 * mMinItemMargin + mItemWidth) / 2 + mLineWidth - 1,
        rountOneTop_Up + mItemHeight, width - (3 * mMinItemMargin + mItemWidth) / 2 + mLineWidth,
        roundTwoTop_Up + mItemHeight / 2 - 1);//所有竖线
  }

  private View makeAndSetup(int round, int group, int left, int top, int right, int bottom) {
    final View view = makeView(round, group, right - left, bottom - top);
    view.layout(left, top, right, bottom);
    return view;
  }

  private View makeView(int round, int group, int w, int h) {
    /*确定View的种类*/
    final int itemViewType = mAdapter.getItemViewType(round, group);
    final View recycledView;
    if (itemViewType == AgainstAdapter.IGNORE_ITEM_VIEW_TYPE) {
      recycledView = null;
    } else {
      /*从View复用栈中获得相应的View*/
      recycledView = mRecycler.getRecycledView(itemViewType);
    }
    /*根据View在对阵图的位置来确定View的类型*/
    final View view = mAdapter.getView(round, group, recycledView, this, isFristComing);
    view.setTag(R.id.tag_itemViewType, itemViewType); //储存View的类型
    view.setTag(R.id.tag_itemRound, round);
    view.setTag(R.id.tag_itemGroup, group);
    /*测量View*/
    view.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
    /*将View添加Group中*/
    addAgainstView(view, round, group);
    return view;
  }

  private void addAgainstView(View view, int round, int group) {
    addView(view, round);
  }

  public void setAdapter(AgainstAdapter adapter) {
    if (mAdapter != null) {
       /*如果adpter已经有观察者，那么需要先注销*/
      mAdapter.unregisterDataSetObserver(mDataSetObserver);
    }

    mAdapter = adapter;

    mRecycler = new Recycler(mAdapter.getViewTypeCount());
    /*创建一个观察者*/
    mDataSetObserver = new AgainstDataSetObserver();
    /*数据观察者*/
    mAdapter.registerDataSetObserver(mDataSetObserver);

    /*设置进行重新填充布局*/
    mNeedRelayout = true;
    /*请求重新填充布局*/
    //requestLayout();
  }

  private class AgainstDataSetObserver extends DataSetObserver {
    /**
     * 当数据发生改变
     */
    @Override
    public void onChanged() {
      mNeedRelayout = true;
      //requestLayout();
    }

    @Override
    public void onInvalidated() {
      //Do noting
    }
  }

  /**
   * Recycler管理复用View的
   */
  public static class Recycler {
    private Stack<View>[] views;

    @SuppressWarnings("unchecked")
    public Recycler(int size) {
      views = new Stack[size];
      for (int i = 0; i < size; i++) {
        views[i] = new Stack<View>();
      }
    }

    public void addRecycledView(View view, int type) {
      views[type].push(view);
    }

    public View getRecycledView(int typeView) {
      try {
        return views[typeView].pop();
      } catch (java.util.EmptyStackException e) {
        return null;
      }
    }
  }
}
