package ru.dtechnologies.myproducthunter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import ru.dtechnologies.myproducthunter.core.Core;
import ru.dtechnologies.myproducthunter.core.models.Category;
import ru.dtechnologies.myproducthunter.core.models.Post;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private ListView listView;
    private ProgressBar pBar;
    private Spinner spinner;
    private SwipeRefreshLayout swipeContainer;

    private static final String DEFAULT_NAME_POST = "tech";
    private String current_category = DEFAULT_NAME_POST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadPosts().execute(current_category);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        pBar = (ProgressBar) findViewById(R.id.pBar);
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        new LoadCategories().execute();

        Intent serviceIntent = new Intent(MainActivity.this, ServiceNotifications.class);
        startService(serviceIntent);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new LoadScreenshotAndGoToPost(((AdapterList) adapterView.getAdapter()).getItem(i)).execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        pBar.setVisibility(View.VISIBLE);
        current_category = ((AdapterSpinner)adapterView.getAdapter()).getSlugCategory(i);
        new LoadPosts().execute(current_category);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class LoadCategories extends AsyncTask<Void, Void, ArrayList<Category>>{

        @Override
        protected ArrayList<Category> doInBackground(Void... voids) {
            return new Core().getCategories();
        }

        @Override
        protected void onPostExecute(ArrayList<Category> categories) {
            super.onPostExecute(categories);
            AdapterSpinner adapter = new AdapterSpinner(MainActivity.this, R.layout.dropdown_spinner, categories);
            adapter.setDropDownViewResource(R.layout.item_spinner);
            spinner.setAdapter(adapter);

            new LoadPosts().execute(DEFAULT_NAME_POST);
        }
    }

    private class LoadPosts extends AsyncTask<String, String, ArrayList<Post>>{

        @Override
        protected ArrayList<Post> doInBackground(String... strings) {
            String category = strings[0];
            return new Core().getPostsToday(category);
        }

        @Override
        protected void onPostExecute(ArrayList<Post> posts) {
            super.onPostExecute(posts);

            if (posts.size() == 0){
                ((LinearLayout) findViewById(R.id.llNoPosts)).setVisibility(View.VISIBLE);
            } else {
                ((LinearLayout) findViewById(R.id.llNoPosts)).setVisibility(View.INVISIBLE);
            }

            AdapterList adapter = new AdapterList(MainActivity.this, R.layout.item_list, posts);
            listView.setAdapter(adapter);
            pBar.setVisibility(View.INVISIBLE);

            swipeContainer.setRefreshing(false);
        }
    }

    private class LoadScreenshotAndGoToPost extends AsyncTask<String, Void, Bitmap>{
        private Post post;

        LoadScreenshotAndGoToPost(Post post){
            this.post = post;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urldisplay = post.getScreenshot_urls();
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byte[] byteArray = stream.toByteArray();


            Intent intent = new Intent(MainActivity.this, PostActivity.class);
            intent.putExtra("Name", post.getName());
            intent.putExtra("Description", post.getTagline());
            intent.putExtra("Votes", String.valueOf(post.getVotes_count()));
            intent.putExtra("Screenshot", byteArray);
            intent.putExtra("url", post.getUrl());

            startActivity(intent);
        }
    }
}
