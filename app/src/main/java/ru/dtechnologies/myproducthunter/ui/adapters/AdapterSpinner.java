package ru.dtechnologies.myproducthunter.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.dtechnologies.myproducthunter.R;
import ru.dtechnologies.myproducthunter.dataLayer.models.Category;

public class AdapterSpinner extends ArrayAdapter<Category> {
    private ArrayList<Category> categories;
    private Context context;

    // layouts для элементов списка spinner
    private int resource;
    private int resourceDropdown;

    public AdapterSpinner(@NonNull Context context, @LayoutRes int resource, @NonNull List<Category> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);

        this.categories = (ArrayList<Category>) objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resource, null);

        TextView tv = (TextView) view.findViewById(R.id.tvItem);
        tv.setText(categories.get(position).getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resourceDropdown, null);

        TextView tv = (TextView) view.findViewById(R.id.tvItem);
        tv.setText(categories.get(position).getName());

        return view;
    }

    @Override
    public void setDropDownViewResource(@LayoutRes int resource) {
        super.setDropDownViewResource(resource);
        this.resourceDropdown = resource;
    }

    public String getSlugCategory(int position){
        return categories.get(position).getSlug();
    }
}
