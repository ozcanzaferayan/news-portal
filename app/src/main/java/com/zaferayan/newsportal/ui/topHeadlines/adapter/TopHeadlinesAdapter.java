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
import com.zaferayan.newsportal.ui.topHeadlines.listener.ArticleClickListener;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

public class TopHeadlinesAdapter extends RecyclerView.Adapter<TopHeadlinesAdapter.SourcesViewHolder> {

    private List<Article> articles;

    public TopHeadlinesAdapter(List<Article> articles) {
        this.articles = articles;
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
        final String url = article.getUrlToImage();
        loadImage(sourcesViewHolder, context, url);
        sourcesViewHolder.txtTitle.setText(article.getTitle());
        // sourcesViewHolder.txtAction.setText(article.getTitle()); // TODO: Check from SQLITE
        sourcesViewHolder.txtPublishedAt.setText(article.getPublishedAt());
        sourcesViewHolder.layout.setOnClickListener(new ArticleClickListener(article.getUrl()));
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

    public static class SourcesViewHolder extends RecyclerView.ViewHolder {
        public View layout;
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