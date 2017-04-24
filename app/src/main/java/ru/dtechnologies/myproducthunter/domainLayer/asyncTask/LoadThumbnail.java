package ru.dtechnologies.myproducthunter.domainLayer.asyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import ru.dtechnologies.myproducthunter.dataLayer.models.Post;

/**
 * Async Task for getting image thumbnail
 */
public class LoadThumbnail extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Post post;

    public LoadThumbnail(ImageView bmImage, Post post) {
        this.bmImage = bmImage;
        this.post = post;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
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

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        post.setThumbnail_bitmap(result);
    }
}
