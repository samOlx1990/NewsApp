package com.wolf.sambuddhadhar.newsapp.core.newslist.api;

import com.wolf.sambuddhadhar.newsapp.core.newslist.model.NewsListModel;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

  @GET("/v2/top-headlines")
  Single<NewsListModel> news(
      @Query("country") String country,
      @Query("apiKey") String apiKey
  );

}
