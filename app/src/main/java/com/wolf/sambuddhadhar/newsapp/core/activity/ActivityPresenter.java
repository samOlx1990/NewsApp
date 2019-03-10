package com.wolf.sambuddhadhar.newsapp.core.activity;

import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivityComponent.ActivityScope;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.ItemClickEvent;
import com.wolf.sambuddhadhar.newsapp.core.util.ScreenKey;
import com.wolf.sambuddhadhar.newsapp.util.Change;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import javax.inject.Inject;

@ActivityScope
class ActivityPresenter implements ObservableTransformer<Event, Change<ActivityUi>>{

  @Inject
  public ActivityPresenter() {
  }

  @Override
  public ObservableSource<Change<ActivityUi>> apply(Observable<Event> upstream) {
    Observable<Event> sharedStream = upstream
        .share();
    return Observable.merge(activityCreateUiChange(sharedStream),
        loadScreenUiChange(sharedStream),
        backPressUiChange(sharedStream));
  }

  private Observable<Change<ActivityUi>> backPressUiChange(Observable<Event> events) {
    return events
        .ofType(BackPressEvent.class)
        .map(__ -> ui -> ui.loadPrevious());
  }

  private Observable<Change<ActivityUi>> loadScreenUiChange(Observable<Event> events) {
    return events
        .ofType(ItemClickEvent.class)
        .map(event -> ui -> ui.loadScreen(ScreenKey.DETAILS, event.url()));
  }

  private Observable<Change<ActivityUi>> activityCreateUiChange(Observable<Event> events) {
    return events
        .ofType(ActivityCreateEvent.class)
        .map(__ -> ui -> ui.loadScreen(ScreenKey.LIST,null));
  }
}
