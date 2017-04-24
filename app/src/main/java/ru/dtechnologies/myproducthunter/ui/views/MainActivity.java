package ru.dtechnologies.myproducthunter.ui.views;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import ru.dtechnologies.myproducthunter.R;
import ru.dtechnologies.myproducthunter.ServiceNotifications;
import ru.dtechnologies.myproducthunter.dataLayer.models.Post;
import ru.dtechnologies.myproducthunter.domainLayer.MainLogic;
import ru.dtechnologies.myproducthunter.ui.adapters.AdapterList;
import ru.dtechnologies.myproducthunter.ui.adapters.AdapterSpinner;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener{

    private ProgressBar pBar;
    private MainLogic mainLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*находим элементы экрана*/
        SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        ListView listView = (ListView) findViewById(R.id.listview);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        LinearLayout llNoPosts = (LinearLayout) findViewById(R.id.llNoPosts);

        /*прикрепляемся к классу логики*/
        mainLogic = new MainLogic(spinner, this, pBar, swipeContainer, llNoPosts, listView);

        /*определяем обработчики для элементов*/
        listView.setOnItemClickListener(this);
        spinner.setOnItemSelectedListener(this);
        swipeContainer.setOnRefreshListener(this);

        /*определяем цветовую схему для pull to refresh*/
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        /* загружаем данные в элементы*/
        mainLogic.loadCategoriesAndPosts();

        /* запускаем сервис уведомлений если не запущен*/
        if (!isMyServiceRunning(ServiceNotifications.class)) {
            Intent serviceIntent = new Intent(MainActivity.this, ServiceNotifications.class);
            startService(serviceIntent);
        }
    }

    /**
     * Method for check that service is running
     * @param serviceClass our service class
     * @return true - if service is running, and false otherwise
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /*обработчик нажатия для списка*/
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // при нажатии на элемент списка с posts загружаем screenshot и запускаем PostActivity
        mainLogic.showInfoProduct(((AdapterList) adapterView.getAdapter()).getItem(i));
    }

    /*обработчики выбора для выпадающего списка*/
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // при выборе нужной категории из списка (spinner) запоминаем slug категории и позицию и загружаем данные с posts на сегодня
        pBar.setVisibility(View.VISIBLE);
        mainLogic.current_category = ((AdapterSpinner)adapterView.getAdapter()).getSlugCategory(i);
        mainLogic.current_category_pos = i;
        mainLogic.loadPosts(mainLogic.current_category);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /*обработчик pull to refresh*/
    @Override
    public void onRefresh() {
        mainLogic.loadCategoriesAndPosts(mainLogic.current_category, mainLogic.current_category_pos);
    }
}
