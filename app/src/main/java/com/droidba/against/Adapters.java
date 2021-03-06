package com.droidba.against;

import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.droidba.widget.againstview.BaseAdapter;
import com.yayandroid.rotatable.Rotatable;

/**
 * Created by leo on 2018/p2/p2.
 */

public class Adapters extends BaseAdapter {
  private static final int TYPE_CENTER = 0;
  private static final int TYPE_ITEM = 1;
  private static final int TYPE_LINE_ONE = 2;
  private static final int TYPE_LINE_TWO = 3;

  @Override
  public int getMaxGroup() {
    return 0;
  }

  @Override
  public int getItemHeight() {
    return 0;
  }

  @Override
  public int getItemWidth() {
    return 0;
  }

  @Override
  public View getView(int round, int group, View convertView, ViewGroup parent,
      final boolean isFristComing) {
    switch (getItemViewType(round, group)) {
      case TYPE_LINE_ONE:
      case TYPE_LINE_TWO:
        if (convertView == null) {
          convertView =
              LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line, parent, false);
        }
        if (getItemViewType(round, group) == TYPE_LINE_ONE) {
          convertView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
              if (isFristComing) {
                startTranslateAnimation(v);
              }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
          });
        } else {
          convertView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
              if (isFristComing) {
                startTranslateAnimationDown(v);
              }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
          });
        }
        FrameLayout view = (FrameLayout) convertView.findViewById(R.id.line);
        view.setBackgroundColor(convertView.getResources().getColor(R.color.colorAccent));
        return convertView;
      case TYPE_ITEM:
        if (convertView == null) {
          convertView =
              LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.d);
        imageView.setImageResource(R.drawable.p1);
        if (isHalfUp(round, group)) {
          convertView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
              if (isFristComing) {
                startTranslateAnimation(v);
              }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
          });
        } else {
          convertView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
              if (isFristComing) {
                startTranslateAnimationDown(v);
              }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
          });
        }

        return convertView;
      case TYPE_CENTER:
        if (convertView == null) {
          convertView =
              LayoutInflater.from(parent.getContext()).inflate(R.layout.item_center, parent, false);
        }
        convertView.findViewById(R.id.backView).setScaleX(-1);
        final Rotatable rotatable =
            new Rotatable.Builder(convertView.findViewById(R.id.targetView)).sides(R.id.frontView,
                R.id.backView)
                .direction(Rotatable.ROTATE_Y)
                .listener(new Rotatable.RotationListener() {
                  @Override
                  public void onRotationChanged(float newRotationX, float newRotationY) {

                  }
                })
                .build();
        return convertView;
      default:
        throw new RuntimeException("wtf?");
    }
  }

  private void startTranslateAnimation(final View view) {
    TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, -200.0f, 0.0f);
    translateAnimation.setInterpolator(new BounceInterpolator());
    translateAnimation.setFillAfter(false);
    translateAnimation.setDuration(700);
    view.startAnimation(translateAnimation);
  }

  private void startTranslateAnimationDown(final View view) {
    TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 200.0f, 0.0f);
    translateAnimation.setInterpolator(new BounceInterpolator());
    translateAnimation.setFillAfter(false);
    translateAnimation.setDuration(700);
    view.startAnimation(translateAnimation);
  }

  @Override
  public int getItemViewType(int round, int group) {
    if (round == 3) {
      return TYPE_CENTER;
    } else if (round == -2) {//上面的线
      return TYPE_LINE_ONE;
    } else if (round == -3) {//下面的线
      return TYPE_LINE_TWO;
    } else {
      return TYPE_ITEM;
    }
  }

  public boolean isHalfUp(int round, int group) {
    if (round == 0 && group < 4) {
      return true;
    } else if (round == 0 && group >= 4) {
      return false;
    } else if (round == 1 && group < 2) {
      return true;
    } else if (round == 1 && group >= 2) {
      return false;
    } else if (round == 2 && group < 1) {
      return true;
    } else if (round == 2 && group >= 1) {
      return false;
    }
    return false;
  }

  @Override
  public int getViewTypeCount() {
    return 4;
  }
}
