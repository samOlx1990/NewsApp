package com.wolf.sambuddhadhar.newsapp.core.newslist.repository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.wolf.sambuddhadhar.newsapp.core.newslist.api.NewsApi;
import com.wolf.sambuddhadhar.newsapp.core.newslist.model.Articles;
import com.wolf.sambuddhadhar.newsapp.core.newslist.model.NewsListModel;
import com.wolf.sambuddhadhar.newsapp.framework.TestSchedulerRule;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class NewsListUsecaseTest {

  @Rule public MockitoRule rule = MockitoJUnit.rule();
  @Rule public TestSchedulerRule schedulerRule = new TestSchedulerRule();

  @Mock NewsApi api;

  private NewsListModel successModel;
  private NewsListModel failureModel;
  private NewsListUsecase usecase;

  @Before
  public void setUp() throws Exception {
    Articles article1 = Articles.create("abc", "abc", "abc");
    Articles article2 = Articles.create("abcd", "abcd", "abcd");
    List<Articles> articlesList = new ArrayList<>();
    articlesList.add(article1);
    articlesList.add(article2);
    String statusOk = "ok";
    String statusfail = "fail";
    successModel = NewsListModel.create(statusOk, articlesList);
    failureModel = NewsListModel.create(statusfail, null);
    usecase = new NewsListUsecase(api);
  }

  @Test
  public void when_api_call_is_successful_then_return_success_result() {
    when(api.news(anyString(), anyString())).thenReturn(Single.just(successModel));
    usecase.news()
        .test()
        .assertValue(newsResult -> newsResult instanceof NewsResult.Success);
  }

  @Test
  public void when_api_call_is_failed_then_return_failure_result() {
    when(api.news(anyString(), anyString())).thenReturn(Single.just(failureModel));
    usecase.news()
        .test()
        .assertValue(newsResult -> newsResult instanceof NewsResult.Failure);
  }
}