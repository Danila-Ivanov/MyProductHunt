package ru.dtechnologies.myproducthunter.dataLayer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ru.dtechnologies.myproducthunter.dataLayer.models.Category;
import ru.dtechnologies.myproducthunter.dataLayer.models.Post;

interface ICore {

    /**
     * {@value} timeout for connect to url
     */
    int CONNECTION_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(10);

    /**
     * {@value} time for read in milliseconds
     */
    int READING_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(20);

    /**
     * {@value} host ProductHunt
     */
    String BASE_URL = "https://api.producthunt.com";

    /**
     * {@value} your access token for getting data
     */
    String ACCESS_TOKEN = "591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff";

    /**
     * {@value} path for get data to categories
     */
    String CATEGORIES_URL = "/v1/categories";

    /**
     * {@value} path for get data to notifications
     */
    String NOTIFICATION_URL = "/v1/notifications";


    /**
     * Method for getting data about exist categories
     * @return array with model Category
     */
    ArrayList<Category> getCategories();

    /**
     * Method for getting posts today
     * @param slug nickname category
     * @return array with model Post
     */
    ArrayList<Post> getPostsToday(String slug);

    /**
     * Method for getting notification about new posts
     * @return array with model Post
     */
    ArrayList<Post> getNotification();


}
