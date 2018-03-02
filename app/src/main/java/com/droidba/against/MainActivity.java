package com.droidba.against;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.droidba.widget.againstview.AgainstView;

public class MainActivity extends AppCompatActivity {

  private AgainstView view;
  private Adapters mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    view = (AgainstView) findViewById(R.id.myAgainstView);
    mAdapter = new Adapters();
    view.setAdapter(mAdapter);
    mAdapter.notifyDataSetChanged();
  }
}
