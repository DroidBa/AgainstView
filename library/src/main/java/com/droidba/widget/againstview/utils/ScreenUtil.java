package com.droidba.widget.againstview.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtil {

  private static int screenWidth = 0;
  private static int screenHeight = 0;
  private static int statusBarHeight = 0;
  private static int contentHeight = 0;

  private ScreenUtil() {
    throw new UnsupportedOperationException("ScreenUtil cannot be instantiated");
  }

  public static int getScreenWidth(Context context) {
    if (screenWidth == 0) {
      WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      DisplayMetrics outMetrics = new DisplayMetrics();
      wm.getDefaultDisplay().getMetrics(outMetrics);
      screenWidth = outMetrics.widthPixels;
    }
    return screenWidth;
  }

  public static int getScreenHeight(Context context) {
    if (screenHeight == 0) {
      WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      DisplayMetrics outMetrics = new DisplayMetrics();
      wm.getDefaultDisplay().getMetrics(outMetrics);
      screenHeight = outMetrics.heightPixels;
    }
    return screenHeight;
  }

  /**
   * 获取状态栏高度
   *
   * @param context context
   * @return 状态栏高度
   */
  public static int getStatusBarHeight(Context context) {
    if (statusBarHeight == 0) {
      int resourceId =
          context.getResources().getIdentifier("status_bar_height", "dimen", "android");
      statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
    }
    return statusBarHeight;
  }

  public static int getContentHeight(Context context) {
    return getScreenHeight(context) - getStatusBarHeight(context);
  }
}