package com.zaferayan.newsportal.ui.topHeadlines.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.zaferayan.newsportal.R;
import com.zaferayan.newsportal.ui.topHeadlines.contract.TopHeadlinesContract;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

public class TopHeadlinesAdapter extends RecyclerView.Adapter<TopHeadlinesAdapter.SourcesViewHolder> {

    private final List<Article> storedArticles;
    private final TopHeadlinesContract.Presenter presenter;
    private List<Article> articles;

    public TopHeadlinesAdapter(List<Article> articles, List<Article> storedArticles, TopHeadlinesContract.Presenter presenter) {
        this.articles = articles;
        this.storedArticles = storedArticles;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public SourcesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.list_item_top_headlines, viewGroup, false);
        return new SourcesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SourcesViewHolder sourcesViewHolder, int position) {
        final Article article = articles.get(position);
        final Context context = sourcesViewHolder.layout.getContext();
        final String imageUrl = article.getUrlToImage();
        final boolean isStored = checkIfStoredArticles(article);
        loadImage(sourcesViewHolder, context, imageUrl);
        sourcesViewHolder.txtTitle.setText(article.getTitle());
        sourcesViewHolder.txtAction.setText(getActionText(context, isStored));
        sourcesViewHolder.txtPublishedAt.setText(article.getPublishedAt());
        sourcesViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.navigateToWebView(article);
            }
        });
        sourcesViewHolder.txtAction.setOnClickListener(presenter.addOrDeleteArticle(article, isStored));
    }

    private String getActionText(Context context, boolean isStored) {
        int resId = isStored ? R.string.headline_li_remove_from_reading_list : R.string.headline_li_add_to_reading_list;
        return context.getString(resId);
    }

    private boolean checkIfStoredArticles(Article article) {
        for (Article storedArticle : storedArticles) {
            if (article.getUrl().equals(storedArticle.getUrl())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void add(int position, Article article) {
        articles.add(position, article);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        articles.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        final int size = articles.size();
        articles.clear();
        notifyItemRangeRemoved(0, size);
    }

    private void loadImage(@NonNull final SourcesViewHolder sourcesViewHolder, final Context context, final String url) {
        Picasso.with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(sourcesViewHolder.image, new Callback() {
                    @Override
                    public void onSuccess() {

                        Log.i("SUCCESS", "Image retrieved OFFLINE");
                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(url)
                                .error(R.drawable.bitmap_headline)
                                .into(sourcesViewHolder.image, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i("SUCCESS", "Image retrieved ONLINE");
                                    }

                                    @Override
                                    public void onError() {
                                        Log.e("ERROR", "Could not fetch " + url);
                                        Picasso.with(context).load(R.drawable.bitmap_headline).into(sourcesViewHolder.image);
                                    }
                                });
                    }
                });
    }

    static class SourcesViewHolder extends RecyclerView.ViewHolder {
        View layout;
        ImageView image;
        TextView txtTitle;
        TextView txtAction;
        TextView txtPublishedAt;

        SourcesViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.imgHeadline);
            txtTitle = v.findViewById(R.id.txtHeadlineTitle);
            txtAction = v.findViewById(R.id.txtHeadlineAction);
            txtPublishedAt = v.findViewById(R.id.txtHeadlinePublishedAt);
        }
    }
}