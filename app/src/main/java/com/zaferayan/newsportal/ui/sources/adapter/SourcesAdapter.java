package com.zaferayan.newsportal.ui.sources.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zaferayan.newsportal.R;
import com.zaferayan.newsportal.ui.sources.listener.SourceClickListener;
import com.zaferayan.newsportal.ui.sources.model.Source;

import java.util.List;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourcesViewHolder> {

    private List<Source> sources;

    public SourcesAdapter(List<Source> sources) {
        this.sources = sources;
    }

    @NonNull
    @Override
    public SourcesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.list_item_sources, viewGroup, false);
        return new SourcesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SourcesViewHolder sourcesViewHolder, int position) {
        final Source source = sources.get(position);
        sourcesViewHolder.txtName.setText(source.getName());
        sourcesViewHolder.txtDescription.setText(source.getDescription());
        sourcesViewHolder.layout.setOnClickListener(new SourceClickListener(source));
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public void add(int position, Source source) {
        sources.add(position, source);
        notifyItemInserted(position);
    }

    public static class SourcesViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        TextView txtName;
        TextView txtDescription;

        SourcesViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.sourcesName);
            txtDescription = v.findViewById(R.id.sourcesDescription);
        }
    }
}