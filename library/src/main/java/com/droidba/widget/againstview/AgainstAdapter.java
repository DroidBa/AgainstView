package com.droidba.widget.againstview;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.math.BigDecimal;

/**
 * Created by leo on 2018/1/30.
 */

public interface AgainstAdapter {
  int IGNORE_ITEM_VIEW_TYPE = -1;

  void registerDataSetObserver(DataSetObserver observer);

  void unregisterDataSetObserver(DataSetObserver observer);

  int getMaxGroup();

  int getItemHeight();

  int getItemWidth();

  View getView(int round, int group, View convertView, ViewGroup parent, boolean isFristComing,
      int design);

  int getItemViewType(int round, int group);

  int getViewTypeCount();
}
