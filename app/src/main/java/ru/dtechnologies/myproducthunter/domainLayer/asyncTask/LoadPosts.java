package ru.dtechnologies.myproducthunter.domainLayer.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import ru.dtechnologies.myproducthunter.R;
import ru.dtechnologies.myproducthunter.dataLayer.Core;
import ru.dtechnologies.myproducthunter.dataLayer.models.Post;
import ru.dtechnologies.myproducthunter.ui.adapters.AdapterList;

/**
 * Async Task for getting posts today
 */
public class LoadPosts extends AsyncTask<String, String, ArrayList<Post>> {

    private LinearLayout llNoPosts;
    private Context context;
    private ListView listView;
    private ProgressBar pBar;
    private SwipeRefreshLayout swipeContainer;

    public LoadPosts(LinearLayout llNoPosts, Context context, ListView listView, ProgressBar pBar, SwipeRefreshLayout swipeContainer) {
        this.llNoPosts = llNoPosts;
        this.context = context;
        this.listView = listView;
        this.pBar = pBar;
        this.swipeContainer = swipeContainer;
    }

    @Override
    protected ArrayList<Post> doInBackground(String... strings) {
        String category = strings[0];
        return new Core().getPostsToday(category);
    }

    @Override
    protected void onPostExecute(ArrayList<Post> posts) {
        super.onPostExecute(posts);

        if (posts != null) {
            // если нет данных то показываем что нет записей на сегодня
            if (posts.size() == 0) {
                llNoPosts.setVisibility(View.VISIBLE);
            } else {
                llNoPosts.setVisibility(View.INVISIBLE);
            }

            // создаём адаптер для списка posts
            AdapterList adapter = new AdapterList(context, R.layout.item_list, posts);
            listView.setAdapter(adapter);
        } else {
            // иначе сообщение что нет интернета
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }

        pBar.setVisibility(View.INVISIBLE);
        swipeContainer.setRefreshing(false);
    }
}
