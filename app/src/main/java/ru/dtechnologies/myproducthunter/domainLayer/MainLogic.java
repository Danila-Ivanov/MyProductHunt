package ru.dtechnologies.myproducthunter.domainLayer;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import ru.dtechnologies.myproducthunter.dataLayer.models.Post;
import ru.dtechnologies.myproducthunter.domainLayer.asyncTask.LoadCategories;
import ru.dtechnologies.myproducthunter.domainLayer.asyncTask.LoadPosts;
import ru.dtechnologies.myproducthunter.domainLayer.asyncTask.LoadScreenshotAndGoToPost;
import ru.dtechnologies.myproducthunter.domainLayer.asyncTask.LoadThumbnail;

public class MainLogic implements IMainLogic{

    private Spinner spinner;
    private Context context;
    private ProgressBar pBar;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayout llNoPosts;
    private ListView listView;

    /*current_pos_spinner - для запоминания позиции категории что выбрали в spinner */
    public int current_category_pos = DEFAULT_POS_CATEGORIES;

    /*current_category - для запоминания имени категории что выбрали в spinner */
    public String current_category = DEFAULT_NAME_CATEGORIES;

    public MainLogic(Spinner spinner,
                     Context context,
                     ProgressBar pBar,
                     SwipeRefreshLayout swipeContainer,
                     LinearLayout llNoPosts,
                     ListView listView) {
        this.spinner = spinner;
        this.context = context;
        this.pBar = pBar;
        this.swipeContainer = swipeContainer;
        this.llNoPosts = llNoPosts;
        this.listView = listView;
    }

    public MainLogic(){}

    @Override
    public void loadCategoriesAndPosts() {
        new LoadCategories(
                DEFAULT_NAME_CATEGORIES,
                DEFAULT_POS_CATEGORIES,
                spinner,
                context,
                pBar,
                swipeContainer,
                llNoPosts,
                listView
        ).execute();
    }

    @Override
    public void loadCategoriesAndPosts(String category, int current_pos_spinner) {
        new LoadCategories(
                category,
                current_pos_spinner,
                spinner,
                context,
                pBar,
                swipeContainer,
                llNoPosts,
                listView
        ).execute();
    }




    @Override
    public void loadPosts(String nameCategory) {
        new LoadPosts(llNoPosts, context, listView, pBar, swipeContainer).execute(nameCategory);
    }

    @Override
    public void showInfoProduct(Post post) {
        new LoadScreenshotAndGoToPost(post, context).execute();
    }

    @Override
    public void getThumbnail(Post post, ImageView imageView) {
        new LoadThumbnail(imageView, post).execute(post.getThumbnail_image());
    }
}
