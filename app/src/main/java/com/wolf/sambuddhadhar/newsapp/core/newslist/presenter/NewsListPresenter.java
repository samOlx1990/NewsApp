package com.wolf.sambuddhadhar.newsapp.core.newslist.presenter;

import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.ActivityScope;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.ItemClickEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.NewsListFragmentCreateEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.RefreshDataEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.RetryEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.repository.NewsListUsecase;
import com.wolf.sambuddhadhar.newsapp.core.newslist.repository.NewsResult;
import com.wolf.sambuddhadhar.newsapp.core.newslist.repository.NewsResult.Success;
import com.wolf.sambuddhadhar.newsapp.core.newslist.ui.NewsListUi;
import com.wolf.sambuddhadhar.newsapp.util.Change;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

@ActivityScope
public class NewsListPresenter implements ObservableTransformer<Event, Change<NewsListUi>> {

  private final NewsListUsecase usecase;

  @Inject
  public NewsListPresenter(NewsListUsecase usecase) {
    this.usecase = usecase;
  }

  @Override
  public ObservableSource<Change<NewsListUi>> apply(Observable<Event> upstream) {
    Observable<Event> sharedStream = upstream
        .share();
    return Observable.mergeArray(fragmentCreateUiChange(sharedStream),
        refreshUiChange(sharedStream),
        retryUiChange(sharedStream),
        itemClickUiChange(sharedStream)
    );
  }

  private Observable<Change<NewsListUi>> itemClickUiChange(Observable<Event> sharedStream) {
    return sharedStream
        .ofType(ItemClickEvent.class)
        .map(event -> ui -> ui.loadNextScreen(event));
  }

  private Observable<Change<NewsListUi>> retryUiChange(Observable<Event> sharedStream) {
    return sharedStream
        .ofType(RetryEvent.class)
        .flatMap(__ -> usecase.news()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .flatMap(newsResult -> toUiChange(newsResult))
            .startWith(Observable.just(
                ui -> ui.hideError(),
                ui -> ui.showLoading())));
  }

  private Observable<Change<NewsListUi>> refreshUiChange(Observable<Event> sharedStream) {
    return sharedStream
        .ofType(RefreshDataEvent.class)
        .flatMap(__ -> usecase.news()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .flatMap(newsResult -> toRefreshUiChange(newsResult)));
  }

  private ObservableSource<Change<NewsListUi>> toRefreshUiChange(NewsResult newsResult) {
    if (newsResult instanceof NewsResult.Success
        && ((Success) newsResult).data() != null
        && ((Success) newsResult).data().articles() != null
        && !((Success) newsResult).data().articles().isEmpty())
    {
      return Observable.just(
          ui -> ui.hideLoading(),
          ui -> ui.stopRefreshing(),
          ui -> ui.loadData(((Success) newsResult).data().articles()));
    } else {
      return Observable.just(ui -> ui.stopRefreshing());
    }
  }

  private Observable<Change<NewsListUi>> fragmentCreateUiChange(Observable<Event> sharedStream) {
    return sharedStream
        .ofType(NewsListFragmentCreateEvent.class)
        .flatMap(__ -> usecase.news()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .flatMap(newsResult -> toUiChange(newsResult))
            .startWith((Change<NewsListUi>) ui -> ui.showLoading()));
  }

  private ObservableSource<Change<NewsListUi>> toUiChange(NewsResult newsResult) {
    if (newsResult instanceof NewsResult.Success
        && ((Success) newsResult).data() != null
        && ((Success) newsResult).data().articles() != null
        && !((Success) newsResult).data().articles().isEmpty())
    {
      return Observable.just(
          ui -> ui.hideLoading(),
          ui -> ui.loadData(((Success) newsResult).data().articles()));
    } else {
      return Observable.just(
          ui -> ui.hideLoading(),
          ui -> ui.loadError());
    }
  }
}
