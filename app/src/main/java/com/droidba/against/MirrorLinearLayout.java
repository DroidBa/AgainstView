package com.droidba.against;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by leo on 2018/3/5.
 */

public class MirrorLinearLayout extends LinearLayout {
  public MirrorLinearLayout(Context context) {
    super(context);
    setWillNotDraw(false);
  }

  public MirrorLinearLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setWillNotDraw(false);
  }

  public MirrorLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setWillNotDraw(false);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.scale(-1, 1, getWidth() / 2, getHeight() / 2);
  }
}
