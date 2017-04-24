package ru.dtechnologies.myproducthunter.domainLayer.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ru.dtechnologies.myproducthunter.R;
import ru.dtechnologies.myproducthunter.dataLayer.Core;
import ru.dtechnologies.myproducthunter.dataLayer.models.Category;
import ru.dtechnologies.myproducthunter.ui.adapters.AdapterSpinner;

/**
 * Async Task for getting categories and posts today
 */
public class LoadCategories extends AsyncTask<Void, Void, ArrayList<Category>> {

    // slug категории для получения posts
    private String category;

    private Spinner spinner;
    private Context context;
    private int current_pos_spinner;
    private ProgressBar pBar;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayout llNoPosts;
    private ListView listView;

    public LoadCategories(String category,
                          int current_pos_spinner,
                          Spinner spinner,
                          Context context,
                          ProgressBar pBar,
                          SwipeRefreshLayout swipeContainer,
                          LinearLayout llNoPosts,
                          ListView listView){
        this.category = category;
        this.current_pos_spinner = current_pos_spinner;
        this.spinner = spinner;
        this.context = context;
        this.pBar = pBar;
        this.swipeContainer = swipeContainer;
        this.llNoPosts = llNoPosts;
        this.listView = listView;
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... voids) {
        return new Core().getCategories();
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        super.onPostExecute(categories);
        if (categories != null){
            // создаём адаптер для списка spinner
            AdapterSpinner adapter = new AdapterSpinner(context, R.layout.dropdown_spinner, categories);
            adapter.setDropDownViewResource(R.layout.item_spinner);
            spinner.setAdapter(adapter);

            spinner.setSelection(current_pos_spinner);

            // загружаем posts данной категории
            new LoadPosts(llNoPosts, context, listView, pBar, swipeContainer).execute(category);
        } else {
            // иначе сообщение что нет интернета
            pBar.setVisibility(View.INVISIBLE);
            swipeContainer.setRefreshing(false);
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }
}
