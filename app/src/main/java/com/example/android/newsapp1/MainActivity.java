package com.example.android.newsapp1;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Displays information about a fire.
 */
public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final int NEWS_LOADER_ID = 1;

    /**
     * URL Constant for data retrieval from The Guardian dataset
     */
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=wildfires&api-key=e98f0c47-38ff-4191-931c-f6d54f0e62b7&show-fields=all";

    ListView mListView;
    TextView mTextView;
    NewsAdapter mAdapter;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        mListView = (ListView) findViewById(R.id.list_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextView = (TextView) findViewById(R.id.text_view);

        //set item click listener for each item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get News object of clicked item
                News clickedNews = (News) parent.getItemAtPosition(position);
                //extract url from that News object
                String articleUrl = clickedNews.getUrl();
                //create intent to view url in browser
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
                //Check to make sure device can resolve intent, if so then start it
                if(urlIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(urlIntent);
                }

            }
        });

        // Check if the data is null or empty.
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        // Check if the data is null or empty.
        if (data == null || data.isEmpty()) {

            // TODO: update the TextView and inform the user about it.
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(R.string.no_results);

        } else { mAdapter = new NewsAdapter(this, data);
            mProgressBar.setVisibility(View.GONE);
            mListView.setAdapter(mAdapter);
            // TODO: You have successfully received the data, update the adapter.
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        }

    }
