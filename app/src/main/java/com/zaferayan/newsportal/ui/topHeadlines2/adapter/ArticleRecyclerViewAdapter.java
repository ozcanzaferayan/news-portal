package com.zaferayan.newsportal.ui.topHeadlines2.adapter;

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
import com.zaferayan.newsportal.ui.savedArticles.viewModel.ArticleViewModel;
import com.zaferayan.newsportal.ui.topHeadlines.listener.ArticleClickListener;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ArticleViewHolder> {

    private final LayoutInflater mInflater;
    private final ArticleViewModel articleViewModel;
    private List<Article> articles; // Create cached copy of Articles

    public ArticleRecyclerViewAdapter(Context context, ArticleViewModel articleViewModel) {
        mInflater = LayoutInflater.from(context);
        this.articleViewModel = articleViewModel;
    }

    public void setArticles(List<Article> list) {
        articles = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        final Article article = articles.get(position);
        final Context context = holder.layout.getContext();
        final String url = article.getUrlToImage();
        final boolean isSaved = article.isSaved();
        loadImage(holder, context, url);
        holder.txtTitle.setText(article.getTitle());
        holder.txtAction.setText(getActionText(context, isSaved));
        holder.txtPublishedAt.setText(article.getPublishedAt());
        holder.layout.setOnClickListener(new ArticleClickListener(article.getUrl()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleViewModel.navigateToWebView(article);
            }
        });
        holder.txtAction.setOnClickListener(articleViewModel.saveArticle(article, isSaved));

    }

    private String getActionText(Context context, boolean isStored) {
        int resId = isStored ? R.string.headline_li_remove_from_reading_list : R.string.headline_li_add_to_reading_list;
        return context.getString(resId);
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
        if (url == null || url.isEmpty()) {
            holder.image.setImageResource(R.drawable.ic_newspaper);
            return;
        }
        loadImageOnline(holder, context, url);
    }

    private void loadImageOnline(@NonNull final ArticleViewHolder holder, final Context context, final String url) {
        Picasso picasso = Picasso.with(context);
        picasso.setLoggingEnabled(true);
        picasso.load(url)
                .error(R.drawable.ic_newspaper)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {

                        Log.i("SUCCESS", "Image retrieved OFFLINE");
                    }

                    @Override
                    public void onError() {
                        loadImageOnline(context, url, holder);
                    }
                });
    }

    private void loadImageOnline(final Context context, final String url, @NonNull final ArticleViewHolder holder) {
        Picasso picasso = Picasso.with(context);
        picasso.setLoggingEnabled(true);
        picasso.load(url)
                .error(R.drawable.ic_newspaper)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i("SUCCESS", "Image retrieved ONLINE");
                    }

                    @Override
                    public void onError() {
                        Log.e("ERROR", "Could not fetch " + url);
                        Picasso.with(context).load(R.drawable.ic_newspaper).into(holder.image);
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