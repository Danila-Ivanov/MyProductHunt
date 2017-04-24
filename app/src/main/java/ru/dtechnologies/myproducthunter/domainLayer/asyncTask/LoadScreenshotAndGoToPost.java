package ru.dtechnologies.myproducthunter.domainLayer.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import ru.dtechnologies.myproducthunter.dataLayer.models.Post;
import ru.dtechnologies.myproducthunter.ui.views.PostActivity;

/**
 * Async Task for getting screenshot post and go to PostActivity
 */
public class LoadScreenshotAndGoToPost extends AsyncTask<String, Void, Bitmap> {
    private Post post;
    private ProgressDialog progressDialog;
    private Context context;

    public LoadScreenshotAndGoToPost(Post post, Context context){
        this.post = post;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // показываем диалоговое окно загрузки
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urldisplay = post.getScreenshot_urls();
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

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        // преобразуем bitmap изображение для передачи по intent в PostActivity
        byte[] byteArray = null;
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteArray = stream.toByteArray();
        }


        // создаём интент для перехода в PostActivity
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra("Name", post.getName());
        intent.putExtra("Description", post.getTagline());
        intent.putExtra("Votes", String.valueOf(post.getVotes_count()));
        intent.putExtra("Screenshot", byteArray);
        intent.putExtra("url", post.getUrl());

        // закрываем диалоговое окно и переходим на другую активити
        progressDialog.dismiss();
        context.startActivity(intent);
    }
}