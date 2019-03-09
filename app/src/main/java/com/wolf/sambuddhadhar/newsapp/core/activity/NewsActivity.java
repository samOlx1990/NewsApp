package com.wolf.sambuddhadhar.newsapp.core.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.wolf.sambuddhadhar.newsapp.R;
import com.wolf.sambuddhadhar.newsapp.application.NewsApplication;
import com.wolf.sambuddhadhar.newsapp.core.util.ScreenKey;
import com.wolf.sambuddhadhar.newsapp.core.util.ScreenRouter;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.subjects.PublishSubject;
import javax.inject.Inject;

public class NewsActivity extends AppCompatActivity implements ActivityUi{

  private static NewsActivityComponent activityComponent;

  PublishSubject<Event> activityUiEvents = PublishSubject.create();

  @Inject ScreenRouter screenRouter;
  @Inject ActivityPresenter presenter;
  //faf323be6f8e41a4922abdc00a922a93

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    activityComponent = NewsApplication.component()
        .activityComponentBuilder()
        .activity(this)
        .build();
    activityComponent.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_news);
    activityUiEvents
        .compose(presenter)
        .subscribe(ui -> ui.act(this));
    activityUiEvents.onNext(ActivityCreateEvent.create());
  }

  public static NewsActivityComponent activityComponent() {
    return activityComponent;
  }

  @Override
  public void loadScreen(ScreenKey key, String param) {
    screenRouter.loadScreen(key, param);
  }

  @Override
  public void loadPrevious() {
    if (!screenRouter.loadPrevious()) {
      finish();
    }
  }

  @Override
  public void onBackPressed() {
    activityUiEvents.onNext(BackPressEvent.create());
  }

  void postEvents(Event event) {
    activityUiEvents.onNext(event);
  }

}
