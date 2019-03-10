package com.wolf.sambuddhadhar.newsapp.core.newslist.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wolf.sambuddhadhar.newsapp.R;
import com.wolf.sambuddhadhar.newsapp.core.activity.NewsActivity;
import com.wolf.sambuddhadhar.newsapp.core.newslist.events.ItemClickEvent;
import com.wolf.sambuddhadhar.newsapp.core.newslist.model.Articles;
import com.wolf.sambuddhadhar.newsapp.core.newslist.ui.NewsListAdapter.NewsHolder;
import com.wolf.sambuddhadhar.newsapp.util.Event;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import javax.inject.Inject;

public class NewsListAdapter extends RecyclerView.Adapter<NewsHolder> {

  @Inject Picasso picasso;

  private final Context context;
  private List<Articles> articles;
  private final PublishSubject<Event> uiEvents;

  public NewsListAdapter(Context context, List<Articles> articles, PublishSubject<Event> uiEvents) {
    this.context = context;
    this.articles = articles;
    this.uiEvents = uiEvents;
    NewsActivity.activityComponent().inject(this);
  }

  @NonNull
  @Override
  public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View item = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.news_item, parent, false);
    NewsHolder holder = new NewsHolder(item, picasso);
    holder.itemView.setOnClickListener(__ -> uiEvents.onNext(ItemClickEvent.create(holder.article.url())));
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
    holder.setData(articles.get(position));
    holder.render();
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }

  public void refreshData(List<Articles> articles) {
    this.articles = articles;
    notifyDataSetChanged();
  }

  static class NewsHolder extends RecyclerView.ViewHolder {

    private final Picasso picasso;
    @BindView(R.id.img_thumb) ImageView thumb;
    @BindView(R.id.news_headline) TextView headline;

    Articles article;

    public NewsHolder(View itemView, Picasso picasso) {
      super(itemView);
      this.picasso = picasso;
      ButterKnife.bind(this, itemView);
    }

    void setData(Articles article) {
      this.article = article;
    }

    void render() {
      picasso.load(article.imageUrl())
          .error(R.drawable.ic_launcher_background)
          .into(thumb);
      headline.setText(article.title());
    }

  }

}
