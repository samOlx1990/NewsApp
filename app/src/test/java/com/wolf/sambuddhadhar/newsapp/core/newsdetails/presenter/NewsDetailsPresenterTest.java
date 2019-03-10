package com.wolf.sambuddhadhar.newsapp.core.newsdetails.presenter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.wolf.sambuddhadhar.newsapp.core.activity.BackPressEvent;
import com.wolf.sambuddhadhar.newsapp.core.newsdetails.events.DetailsFragmentCreateEvent;
import com.wolf.sambuddhadhar.newsapp.core.newsdetails.ui.NewsDetailsUi;
import com.wolf.sambuddhadhar.newsapp.framework.TestSchedulerRule;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.subjects.PublishSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class NewsDetailsPresenterTest {

  @Rule public MockitoRule rule = MockitoJUnit.rule();
  @Rule public TestSchedulerRule schedulerRule = new TestSchedulerRule();

  @Mock NewsDetailsUi detailsUi;

  NewsDetailsPresenter presenter;
  PublishSubject<Event> uiEvents = PublishSubject.create();

  @Before
  public void setUp() throws Exception {
    presenter = new NewsDetailsPresenter();
    uiEvents
        .compose(presenter)
        .subscribe(ui -> ui.act(detailsUi));
  }

  @Test
  public void when_fragment_is_created_then_load_view() {
    String url = "abc";
    uiEvents.onNext(DetailsFragmentCreateEvent.create(url));

    verify(detailsUi).loadView(url);
    verifyNoMoreInteractions(detailsUi);
  }

  @Test
  public void when_back_button_is_pressed_then_load_previous_screen() {
    BackPressEvent event = BackPressEvent.create();
    uiEvents.onNext(event);

    verify(detailsUi).handleBack(event);
    verifyNoMoreInteractions(detailsUi);
  }

}