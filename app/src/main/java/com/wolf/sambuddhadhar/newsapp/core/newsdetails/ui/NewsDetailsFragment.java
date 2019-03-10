package com.wolf.sambuddhadhar.newsapp.core.newsdetails.ui;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wolf.sambuddhadhar.newsapp.R;
import com.wolf.sambuddhadhar.newsapp.core.activity.BackPressEvent;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivity;
import com.wolf.sambuddhadhar.newsapp.core.newsdetails.events.DetailsFragmentCreateEvent;
import com.wolf.sambuddhadhar.newsapp.core.newsdetails.presenter.NewsDetailsPresenter;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.PublishSubject;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailsFragment extends Fragment implements NewsDetailsUi{

  private static final String KEY_URL = "url";

  @BindView(R.id.back) ImageView backbutton;
  @BindView(R.id.webView) WebView webView;
  @BindView(R.id.progress) ProgressBar progressBar;

  @Inject NewsActivity activity;
  @Inject NewsDetailsPresenter newsDetailsPresenter;

  PublishSubject<Event> uiEvents = PublishSubject.create();

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
    View view = inflater.inflate(R.layout.fragment_news_details, container, false);
    ButterKnife.bind(this, view);
    NewsActivity.activityComponent().inject(this);
    uiEvents
        .compose(newsDetailsPresenter)
        .subscribe(ui -> ui.act(this));
    uiEvents.onNext(DetailsFragmentCreateEvent.create(getArguments().getString(KEY_URL)));
    backbutton.setOnClickListener(__ -> uiEvents.onNext(BackPressEvent.create()));
    return view;
  }

  @Override
  public void loadView(String url) {
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient() {
      @SuppressWarnings("deprecation")
      @Override
      public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
      }
      @TargetApi(android.os.Build.VERSION_CODES.M)
      @Override
      public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
        // Redirect to deprecated method, so you can use it in all SDK versions
        onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
      }

      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        progressBar.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        progressBar.setVisibility(View.GONE);
        super.onPageFinished(view, url);
      }
    });
    webView.loadUrl(url);
  }

  @Override
  public void handleBack(Event event) {
    activity.postEvents(event);
  }
}
