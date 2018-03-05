package com.droidba.against;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
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
      case TYPE_ITEM:
        if (convertView == null) {
          convertView =
              LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        }
        ImageView view = (ImageView) convertView.findViewById(R.id.d);
        view.setImageResource(R.drawable.p1);
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
    translateAnimation.setFillAfter(false);
    translateAnimation.setDuration(500);
    view.startAnimation(translateAnimation);
  }

  @Override
  public int getItemViewType(int round, int group) {
    if (round == 3) {
      return TYPE_CENTER;
    } else {
      return TYPE_ITEM;
    }
  }

  @Override
  public int getViewTypeCount() {
    return 2;
  }
}
