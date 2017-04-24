package ru.dtechnologies.myproducthunter.ui.adapters;

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

import ru.dtechnologies.myproducthunter.R;
import ru.dtechnologies.myproducthunter.dataLayer.models.Post;
import ru.dtechnologies.myproducthunter.domainLayer.MainLogic;

public class AdapterList extends ArrayAdapter<Post> {

    private ArrayList<Post> posts;
    private Context context;

    // layout элемента списка posts
    private int resource;
    private MainLogic mainLogic;

    public AdapterList(@NonNull Context context, @LayoutRes int resource, ArrayList<Post> posts) {
        super(context, resource, posts);
        this.posts = posts;
        this.context = context;
        this.resource = resource;
        mainLogic = new MainLogic();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resource, null);

        // находим элементы представления элемента списка posts
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        TextView tvVoites = (TextView) view.findViewById(R.id.tvVoites);
        ImageView iv = (ImageView) view.findViewById(R.id.ivThumbnail);

        // вставляем данные
        Post post = posts.get(position);
        tvName.setText(post.getName());
        tvDescription.setText(post.getTagline());
        tvVoites.setText(String.valueOf(post.getVotes_count()));

        // загружаем thumbnail
        if (post.getThumbnail_bitmap() != null){
            iv.setImageBitmap(post.getThumbnail_bitmap());
        } else {
            mainLogic.getThumbnail(post, iv);
        }

        return view;
    }

    @Nullable
    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }
}
