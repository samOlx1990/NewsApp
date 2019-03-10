package com.wolf.sambuddhadhar.newsapp.core.newslist.presenter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.wolf.sambuddhadhar.newsapp.core.newslist.events.ItemClickEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.NewsListFragmentCreateEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.RefreshDataEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.RetryEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.model.Articles;
import com.wolf.sambuddhadhar.newsapp.core.newslist.model.NewsListModel;
import com.wolf.sambuddhadhar.newsapp.core.newslist.repository.NewsListUsecase;
import com.wolf.sambuddhadhar.newsapp.core.newslist.repository.NewsResult;
import com.wolf.sambuddhadhar.newsapp.core.newslist.ui.NewsListUi;
import com.wolf.sambuddhadhar.newsapp.framework.TestSchedulerRule;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class NewsListPresenterTest {

  @Rule public MockitoRule rule = MockitoJUnit.rule();
  @Rule public TestSchedulerRule schedulerRule = new TestSchedulerRule();

  @Mock NewsListUsecase usecase;
  @Mock NewsListUi newsListUi;

  NewsListPresenter presenter;
  private NewsListModel successModel;
  PublishSubject<Event> uiEvents = PublishSubject.create();

  @Before
  public void setUp() throws Exception {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    presenter = new NewsListPresenter(usecase);
    Articles article1 = Articles.create("abc", "abc", "abc");
    Articles article2 = Articles.create("abcd", "abcd", "abcd");
    List<Articles> articlesList = new ArrayList<>();
    articlesList.add(article1);
    articlesList.add(article2);
    String statusOk = "ok";
    successModel = NewsListModel.create(statusOk, articlesList);
    uiEvents
        .compose(presenter)
        .subscribe(ui -> ui.act(newsListUi));
  }

  @Test
  public void fragment_create_event_api_success() {
    when(usecase.news()).thenReturn(Single.just(NewsResult.success(successModel)));

    uiEvents.onNext(NewsListFragmentCreateEvent.create());

    verify(newsListUi).showLoading();
    verify(newsListUi).hideLoading();
    verify(newsListUi).loadData(successModel.articles());
    verifyNoMoreInteractions(newsListUi);

  }

  @Test
  public void fragment_create_event_api_failure() {
    when(usecase.news()).thenReturn(Single.just(NewsResult.failure()));

    uiEvents.onNext(NewsListFragmentCreateEvent.create());

    verify(newsListUi).showLoading();
    verify(newsListUi).hideLoading();
    verify(newsListUi).loadError();
    verifyNoMoreInteractions(newsListUi);

  }

  @Test
  public void refresh_event_api_success() {
    when(usecase.news()).thenReturn(Single.just(NewsResult.success(successModel)));

    uiEvents.onNext(RefreshDataEvent.create());

    verify(newsListUi).hideLoading();
    verify(newsListUi).stopRefreshing();
    verify(newsListUi).loadData(successModel.articles());
    verifyNoMoreInteractions(newsListUi);

  }

  @Test
  public void refresh_event_api_failure() {
    when(usecase.news()).thenReturn(Single.just(NewsResult.failure()));

    uiEvents.onNext(RefreshDataEvent.create());

    verify(newsListUi).stopRefreshing();
    verifyNoMoreInteractions(newsListUi);

  }

  @Test
  public void retry_event_api_success() {
    when(usecase.news()).thenReturn(Single.just(NewsResult.success(successModel)));

    uiEvents.onNext(RetryEvent.create());

    verify(newsListUi).hideError();
    verify(newsListUi).showLoading();
    verify(newsListUi).hideLoading();
    verify(newsListUi).loadData(successModel.articles());
    verifyNoMoreInteractions(newsListUi);

  }

  @Test
  public void retry_event_api_failure() {
    when(usecase.news()).thenReturn(Single.just(NewsResult.failure()));

    uiEvents.onNext(RetryEvent.create());

    verify(newsListUi).hideError();
    verify(newsListUi).showLoading();
    verify(newsListUi).hideLoading();
    verify(newsListUi).loadError();
    verifyNoMoreInteractions(newsListUi);

  }

  @Test
  public void when_news_item_is_clicked_then_load_next_screen() {
    ItemClickEvent itemClickEvent = ItemClickEvent.create("abc");
    uiEvents.onNext(itemClickEvent);
    verify(newsListUi).loadNextScreen(itemClickEvent);
    verifyNoMoreInteractions(newsListUi);
  }

  @After
  public void tearDown() throws Exception {
    RxJavaPlugins.reset();
  }
}