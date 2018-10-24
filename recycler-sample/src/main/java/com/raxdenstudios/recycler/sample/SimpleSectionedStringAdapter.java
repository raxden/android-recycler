package com.raxdenstudios.recycler.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raxdenstudios.recycler.RecyclerSectionedAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by agomez on 30/11/2015.
 */
public class SimpleSectionedStringAdapter extends RecyclerSectionedAdapter<String, SimpleSectionedStringAdapter.StringSectionViewHolder, String, SimpleSectionedStringAdapter.StringItemViewHolder> {

    public SimpleSectionedStringAdapter(Context context) {
        super(context);
    }

    public SimpleSectionedStringAdapter(Context context, Map<String, List<String>> data) {
        super(context, data);
    }

    @Override
    public StringSectionViewHolder onCreateViewSectionHolder(ViewGroup parent) {
        return new StringSectionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.string_section, null));
    }

    @Override
    public StringItemViewHolder onCreateViewItemHolder(ViewGroup parent) {
        return new StringItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.string_row, null));
    }

    @Override
    public void onBindViewSectionHolder(StringSectionViewHolder holder, String section, int position) {
        holder.textView.setText(section);
    }

    @Override
    public void onBindViewItemHolder(StringItemViewHolder holder, String section, String item, int position) {
        holder.textView.setText(item);
    }

    public class StringSectionViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public StringSectionViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text_view);
        }

    }

    public class StringItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public StringItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text_view);
        }

    }

}
