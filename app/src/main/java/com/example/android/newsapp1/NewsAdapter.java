package com.example.android.newsapp1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.newsapp1.R;
import com.example.android.newsapp1.News;


import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends ArrayAdapter<News> {

    List<News> mNewsList;

    public NewsAdapter(@NonNull Context context, List<News> newsList) {
        super(context, 0, newsList);
        mNewsList = newsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        News currentNews = mNewsList.get(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }
            TextView title = (TextView) view.findViewById(R.id.title_text_view);
            title.setText(currentNews.getTitle());
            TextView date = (TextView) view.findViewById(R.id.date_text_view);
            date.setText(currentNews.getDate());
            TextView author = (TextView) view.findViewById(R.id.author_text_view);
            author.setText(currentNews.getAuthor());
            TextView section = (TextView) view.findViewById(R.id.section_text_view);
            section.setText(currentNews.getSection());

            title.setText(currentNews.getTitle());
            date.setText(currentNews.getDate());
            author.setText(currentNews.getAuthor());
            section.setText(currentNews.getSection());

            return view;
        }
}
