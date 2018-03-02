package com.droidba.widget.againstview;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

/**
 * Created by leo on 2018/1/30.
 */

public abstract class BaseAdapter implements AgainstAdapter {
  private final DataSetObservable mDataSetObservable = new DataSetObservable();

  @Override
  public void registerDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.registerObserver(observer);
  }

  @Override
  public void unregisterDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.unregisterObserver(observer);
  }

  public void notifyDataSetChanged() {
    mDataSetObservable.notifyChanged();
  }

  public void notifyDataSetInvalidated() {
    mDataSetObservable.notifyInvalidated();
  }
}
