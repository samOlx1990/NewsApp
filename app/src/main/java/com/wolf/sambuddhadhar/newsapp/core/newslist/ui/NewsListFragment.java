package com.wolf.sambuddhadhar.newsapp.core.newslist.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.wolf.sambuddhadhar.newsapp.R;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivity;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.NewsListFragmentCreateEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.RefreshDataEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.RetryEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.model.Articles;
import com.wolf.sambuddhadhar.newsapp.core.newslist.presenter.NewsListPresenter;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends Fragment implements NewsListUi {


  @Inject NewsActivity activity;
  @Inject NewsListPresenter presenter;

  @BindView(R.id.refresh) SwipeRefreshLayout refreshLayout;
  @BindView(R.id.layout_retry) RelativeLayout retryLayout;
  @BindView(R.id.loader) ProgressBar loader;
  @BindView(R.id.news_list_holder) RecyclerView recyclerView;

  Unbinder unbinder;
  PublishSubject<Event> uiEvents = PublishSubject.create();
  NewsListAdapter adapter;

  public NewsListFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment NewsListFragment.
   */
  public static NewsListFragment newInstance() {
    NewsListFragment fragment = new NewsListFragment();
    Bundle args = new Bundle();
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
    // Inflate the layout for this fragment
    View layout = inflater.inflate(R.layout.fragment_news_list, container, false);
    unbinder = ButterKnife.bind(this, layout);
    NewsActivity.activityComponent().inject(this);
    uiEvents
        .compose(presenter)
        .subscribe(ui -> ui.act(this));
    initViews();
    uiEvents.onNext(NewsListFragmentCreateEvent.create());
    return layout;
  }

  private void initViews() {
    recyclerView.addOnScrollListener(new OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager manager = ((LinearLayoutManager)recyclerView.getLayoutManager());
        boolean enabled =manager.findFirstCompletelyVisibleItemPosition() == 0;
        refreshLayout.setEnabled(enabled);
      }
    });
    refreshLayout.setOnRefreshListener(() -> uiEvents.onNext(RefreshDataEvent.create()));
    retryLayout.setOnClickListener(__ -> uiEvents.onNext(RetryEvent.create()));
  }

  @Override
  public void showLoading() {
    if (!(loader.getVisibility() == View.VISIBLE)) {
      loader.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void hideLoading() {
    loader.setVisibility(View.GONE);
  }

  @Override
  public void loadData(List<Articles> articles) {
    if (adapter == null) {
      adapter = new NewsListAdapter(activity, articles, uiEvents);
      LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
      DividerItemDecoration itemDecoration = new DividerItemDecoration(activity,
          layoutManager.getOrientation());
      recyclerView.setLayoutManager(layoutManager);
      recyclerView.addItemDecoration(itemDecoration);
      recyclerView.setAdapter(adapter);
    } else {
      adapter.refreshData(articles);
    }
  }

  @Override
  public void loadError() {
    retryLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideError() {
    retryLayout.setVisibility(View.GONE);
  }

  @Override
  public void loadNextScreen(Event event) {
    activity.postEvents(event);
  }

  @Override
  public void stopRefreshing() {
    if (refreshLayout.isRefreshing()) {
      refreshLayout.setRefreshing(false);
    }
  }
}
