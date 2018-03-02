package com.droidba.against;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.droidba.widget.againstview.AgainstAdapter;
import com.droidba.widget.againstview.BaseAdapter;

/**
 * Created by leo on 2018/p2/p2.
 */

public class Adapters extends BaseAdapter {
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
  public View getView(int round, int group, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
    }
    ImageView view = (ImageView) convertView.findViewById(R.id.d);
    if (round == 3) {
      view.setImageResource(R.drawable.p2);
    } else {
      view.setImageResource(R.drawable.p1);
    }
    return convertView;
  }

  @Override
  public int getItemViewType(int round, int group) {
    return 0;
  }

  @Override
  public int getViewTypeCount() {
    return 1;
  }
}
