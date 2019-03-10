package com.wolf.sambuddhadhar.newsapp.core.newslist.ui;

import com.wolf.sambuddhadhar.newsapp.core.newslist.model.Articles;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import java.util.List;

public interface NewsListUi {
  void showLoading();

  void hideLoading();

  void loadData(List<Articles> articles);

  void loadError();

  void hideError();

  void loadNextScreen(Event event);

  void stopRefreshing();
}
