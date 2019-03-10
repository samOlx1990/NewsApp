package com.wolf.sambuddhadhar.newsapp.core.newsdetails.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolf.sambuddhadhar.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailsFragment extends Fragment implements NewsDetailsUi{

  private static final String KEY_URL = "url";

  public NewsDetailsFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   * @return A new instance of fragment NewsDetailsFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static NewsDetailsFragment newInstance(String url) {
    NewsDetailsFragment fragment = new NewsDetailsFragment();
    Bundle args = new Bundle();
    args.putString(KEY_URL, url);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
  {
    return inflater.inflate(R.layout.fragment_news_details, container, false);
  }

}
