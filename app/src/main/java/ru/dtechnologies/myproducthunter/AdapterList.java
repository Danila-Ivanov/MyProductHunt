package ru.dtechnologies.myproducthunter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import ru.dtechnologies.myproducthunter.core.models.Post;

/**
 * Created by Danila on 16.04.2017.
 */

public class AdapterList extends ArrayAdapter<Post> {

    private ArrayList<Post> posts;
    private Context context;
    private int resource;

    public AdapterList(@NonNull Context context, @LayoutRes int resource, ArrayList<Post> posts) {
        super(context, resource, posts);
        this.posts = posts;
        this.context = context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resource, null);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        TextView tvVoites = (TextView) view.findViewById(R.id.tvVoites);
        ImageView iv = (ImageView) view.findViewById(R.id.ivThumbnail);

        Post post = posts.get(position);
        tvName.setText(post.getName());
        tvDescription.setText(post.getTagline());
        tvVoites.setText(String.valueOf(post.getVotes_count()));

        new DownloadImageTask(iv).execute(post.getThumbnail_image());

        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
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
        }
    }

    @Nullable
    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }
}
