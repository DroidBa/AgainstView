package com.droidba.widget.againstview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class DensityUtil {

  private DensityUtil() {
    throw new UnsupportedOperationException("DensityUtil cannot be instantiated");
  }

  public static float px2sp(Context context, float pxValue) {
    return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
  }

  public static int sp2px(Context context, int spValue) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
        context.getResources().getDisplayMetrics());
  }

  public static int dp2px(Context context, int dipValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }

  public static int dp2px(int dipValue) {
    final float scale = Resources.getSystem().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }


  public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }
}