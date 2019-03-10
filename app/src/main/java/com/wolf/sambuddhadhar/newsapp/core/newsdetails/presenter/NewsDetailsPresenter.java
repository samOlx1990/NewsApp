package com.wolf.sambuddhadhar.newsapp.core.newsdetails.presenter;

import com.wolf.sambuddhadhar.newsapp.core.activity.BackPressEvent;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.ActivityScope;
import com.wolf.sambuddhadhar.newsapp.core.newsdetails.events.DetailsFragmentCreateEvent;
import com.wolf.sambuddhadhar.newsapp.core.newsdetails.ui.NewsDetailsUi;
import com.wolf.sambuddhadhar.newsapp.util.Change;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;

@ActivityScope
public class NewsDetailsPresenter implements ObservableTransformer<Event, Change<NewsDetailsUi>> {

  @Inject
  public NewsDetailsPresenter() {
  }

  @Override
  public ObservableSource<Change<NewsDetailsUi>> apply(Observable<Event> upstream) {
    Observable<Event> sharedStream = upstream
        .share();
    return Observable.merge(fragmentCreateUiChange(sharedStream), backPressUiChange(sharedStream));
  }

  private Observable<Change<NewsDetailsUi>> backPressUiChange(Observable<Event> sharedStream) {
    return sharedStream
        .ofType(BackPressEvent.class)
        .map(event -> ui -> ui.handleBack(event));
  }

  private Observable<Change<NewsDetailsUi>> fragmentCreateUiChange(Observable<Event> sharedStream) {
    return sharedStream
        .ofType(DetailsFragmentCreateEvent.class)
        .map(event -> ui -> ui.loadView(event.url()));
  }
}
