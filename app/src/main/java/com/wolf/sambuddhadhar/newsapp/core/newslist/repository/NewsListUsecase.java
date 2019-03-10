package com.wolf.sambuddhadhar.newsapp.core.newslist.repository;

import android.support.annotation.NonNull;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.ActivityScope;
import com.wolf.sambuddhadhar.newsapp.core.newslist.api.NewsApi;
import com.wolf.sambuddhadhar.newsapp.core.newslist.model.NewsListModel;
import com.wolf.sambuddhadhar.newsapp.core.newslist.repository.NewsResult.Success;
import io.reactivex.Single;
import javax.inject.Inject;

@ActivityScope
public class NewsListUsecase {

  private final NewsApi api;
  private static final String API_KEY = "faf323be6f8e41a4922abdc00a922a93";
  private static final String COUNTRY_CODE = "in";

  @Inject
  public NewsListUsecase(NewsApi api) {
    this.api = api;
  }

  public Single<NewsResult> news() {
    return api.news(COUNTRY_CODE, API_KEY)
        .map(newsListModel -> getSuccess(newsListModel))
        .onErrorReturnItem(NewsResult.failure());
  }

  @NonNull
  private NewsResult getSuccess(NewsListModel newsListModel) {
    if ("ok".equalsIgnoreCase(newsListModel.status())) {
      return NewsResult.success(newsListModel);
    } else {
      return NewsResult.failure();
    }
  }
}
