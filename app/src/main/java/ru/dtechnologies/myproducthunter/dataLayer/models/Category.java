package ru.dtechnologies.myproducthunter.dataLayer.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {

    private int id;
    private String name;
    private String slug;
    private String color;
    private String item_name;


    public Category(JSONObject obj){
        try {
            id = obj.getInt("id");
            name = obj.getString("name");
            slug = obj.getString("slug");
            color = obj.getString("color");
            item_name = obj.getString("item_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Category(int id, String name, String slug, String color, String item_name) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.color = color;
        this.item_name = item_name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getColor() {
        return color;
    }

    public String getItem_name() {
        return item_name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", color='" + color + '\'' +
                ", item_name='" + item_name + '\'' +
                '}';
    }
}
