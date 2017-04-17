package ru.dtechnologies.myproducthunter.core.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Danila on 15.04.2017.
 */
public class Post {

    private int id;
    private Date day;
    private String name;
    private int votes_count;
    private String screenshot_urls;
    private String tagline;
    private Thumbnail thumbnail;
    private String url;

    public Post(JSONObject obj){
        try {
            id = obj.getInt("id");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            day = sdf.parse(obj.getString("day"));
            name = obj.getString("name");
            votes_count = obj.getInt("votes_count");
            screenshot_urls = obj.getJSONObject("screenshot_url").getString("300px");
            tagline = obj.getString("tagline");
            thumbnail = new Thumbnail(obj.getJSONObject("thumbnail"));
            url = obj.getString("redirect_url");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Post(int id, Date day, String name, int votes_count, String screenshot_urls, String tagline, Thumbnail thumbnail, String url) {
        this.id = id;

        this.day = day;
        this.name = name;
        this.votes_count = votes_count;
        this.screenshot_urls = screenshot_urls;
        this.tagline = tagline;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public Date getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public int getVotes_count() {
        return votes_count;
    }

    public String getScreenshot_urls() {
        return screenshot_urls;
    }

    public String getTagline() {
        return tagline;
    }

    public int getThumbnail_id() {
        return thumbnail.getId();
    }

    public String getThumbnail_image(){
        return thumbnail.getImage_url();
    }

    public String getUrl(){
        return url;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", day=" + day +
                ", name='" + name + '\'' +
                ", votes_count=" + votes_count +
                ", screenshot_urls=" + screenshot_urls +
                ", tagline='" + tagline + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }

    private class Thumbnail{
        private int id;
        private String image_url;

        Thumbnail(JSONObject obj){
            try {
                id = obj.getInt("id");
                image_url = obj.getString("image_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public Thumbnail(int id, String image_url) {
            this.id = id;
            this.image_url = image_url;
        }

        public int getId() {
            return id;
        }

        public String getImage_url() {
            return image_url;
        }

        @Override
        public String toString() {
            return "Thumbnail{" +
                    "id=" + id +
                    ", image_url='" + image_url + '\'' +
                    '}';
        }
    }
}
