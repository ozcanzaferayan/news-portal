package com.zaferayan.newsportal.ui.savedArticles;

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

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ArticleViewHolder> {

    private final LayoutInflater mInflater;
    private List<Article> articles; // Create cached copy of Articles

    ArticleRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setArticles(List<Article> list) {
        articles = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        final Article article = articles.get(position);
        final Context context = holder.layout.getContext();
        final String url = article.getUrlToImage();
        loadImage(holder, context, url);
        holder.txtTitle.setText(article.getTitle());
        // sourcesViewHolder.txtAction.setText(article.getTitle()); // TODO: Check from SQLITE
        holder.txtPublishedAt.setText(article.getPublishedAt());
        holder.layout.setOnClickListener(new ArticleClickListener(article.getUrl()));

    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_top_headlines, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (articles != null)
            return articles.size();
        return 0;
    }

    private void loadImage(@NonNull final ArticleRecyclerViewAdapter.ArticleViewHolder holder, final Context context, final String url) {
        Picasso.with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {

                        Log.i("SUCCESS", "Image retrieved OFFLINE");
                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(url)
                                .error(R.drawable.bitmap_headline)
                                .into(holder.image, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i("SUCCESS", "Image retrieved ONLINE");
                                    }

                                    @Override
                                    public void onError() {
                                        Log.e("ERROR", "Could not fetch " + url);
                                        Picasso.with(context).load(R.drawable.bitmap_headline).into(holder.image);
                                    }
                                });
                    }
                });
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        ImageView image;
        TextView txtTitle;
        TextView txtAction;
        TextView txtPublishedAt;

        ArticleViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.imgHeadline);
            txtTitle = v.findViewById(R.id.txtHeadlineTitle);
            txtAction = v.findViewById(R.id.txtHeadlineAction);
            txtPublishedAt = v.findViewById(R.id.txtHeadlinePublishedAt);
        }


    }
}