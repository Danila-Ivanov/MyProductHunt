package ru.dtechnologies.myproducthunter.core;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import ru.dtechnologies.myproducthunter.core.models.Category;
import ru.dtechnologies.myproducthunter.core.models.Post;

/**
 * Created by Danila on 15.04.2017.
 */
public class Core implements ICore{

    @Override
    public ArrayList<Category> getCategories() {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));

        ArrayList<Category> arr = new ArrayList<Category>();
        JSONObject answer = null;
        try {
            answer = GET(params, CATEGORIES_URL);
            if (answer != null){
                JSONArray categories = answer.getJSONArray("categories");
                for (int i = 0; i < categories.length(); i++){
                    arr.add(new Category(categories.getJSONObject(i)));
                }
                return arr;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Post> getPostsToday(String slug) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));


        try {
            ArrayList<Post> posts = new ArrayList<Post>();
            JSONObject answer = GET(params, CATEGORIES_URL + "/" + slug + "/posts");
            if (answer != null){
                JSONArray arr = answer.getJSONArray("posts");
                for(int i = 0; i < arr.length(); i++){
                    posts.add(new Post(arr.getJSONObject(i)));
                }
                return posts;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Post> getNotification() {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));

        try {
            ArrayList<Post> posts = new ArrayList<Post>();
            JSONObject answer = GET(params, NOTIFICATION_URL);
            if (answer != null){
                JSONArray arr = answer.getJSONArray("notifications");
                if (arr.length() == 1) {
                    for (int i = 0; i < arr.length(); i++) {
                        posts.add(new Post(arr.getJSONObject(i).getJSONObject("reference")));
                    }
                    return posts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public JSONObject GET(List<NameValuePair> params, String url_s) throws IOException, JSONException {
        String createUrl = BASE_URL + url_s + "?" +getQuery(params);
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(createUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(CONNECTION_TIMEOUT);

            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
            connection.addRequestProperty("Host", "api.producthunt.com");
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            //System.out.println(sb.toString());
            reader.close();
        } catch (SocketTimeoutException e) {
            System.err.println(e.getMessage());
        }

        return new JSONObject(sb.toString());
    }
}
