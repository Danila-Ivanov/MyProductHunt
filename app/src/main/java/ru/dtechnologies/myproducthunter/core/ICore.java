package ru.dtechnologies.myproducthunter.core;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ru.dtechnologies.myproducthunter.core.models.Category;
import ru.dtechnologies.myproducthunter.core.models.Post;

/**
 * Created by Danila on 15.04.2017.
 */
public interface ICore {

    int CONNECTION_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(5);

    String BASE_URL = "https://api.producthunt.com";

    String ACCESS_TOKEN = "591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff";

    String CATEGORIES_URL = "/v1/categories";

    String NOTIFICATION_URL = "/v1/notifications";

    ArrayList<Category> getCategories();

    ArrayList<Post> getPostsToday(String slug);

    ArrayList<Post> getNotification();


}
