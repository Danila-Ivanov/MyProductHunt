package ru.dtechnologies.myproducthunter.domainLayer;

import android.widget.ImageView;

import ru.dtechnologies.myproducthunter.dataLayer.models.Post;

interface IMainLogic {

    /* DEFAULT_NAME_POST дефолтное имя категории*/
    String DEFAULT_NAME_CATEGORIES = "tech";

    /* DEFAULT_POS_CATEGORIES позиция дефолтной категории*/
    int DEFAULT_POS_CATEGORIES = 0;

    /**
     * load data (categories and posts in default category) from server and load this data in screen elements
     */
    void loadCategoriesAndPosts();

    /**
     * load data (categories and posts in the category) from server and load this data in screen elements
     * @param category name category by which products we obtained
     * @param current_pos_spinner position is spinner this category
     */
    void loadCategoriesAndPosts(String category, int current_pos_spinner);

    /**
     * load posts(products) from server by getting category and load this data in screen elements
     * @param nameCategory name category by which products we obtained
     */
    void loadPosts(String nameCategory);

    /**
     * opened new activity with information about product which we got
     * @param post model post(product)
     */
    void showInfoProduct(Post post);

    /**
     * method loading thumbnail in element list
     * @param post model product
     * @param imageView view in which we loading image
     */
    void getThumbnail(Post post, ImageView imageView);


}
