package ru.dtechnologies.myproducthunter.ui.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ru.dtechnologies.myproducthunter.R;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // получаем массив byte для преобразования изображения Screenshot в bitmap
        byte[] byteArray = getIntent().getByteArrayExtra("Screenshot");

        // преобразуем изображение Screenshot в bitmap и устанавливаем в соответствующую ImageView
        if (byteArray != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ((ImageView) findViewById(R.id.screenshot)).setBackground(new BitmapDrawable(getResources(), bitmap));
        } else {
            ((ImageView) findViewById(R.id.screenshot)).setBackground(getResources().getDrawable(R.drawable.gradient));
        }

        // находим остальные элементы экрана и вставляем соответствующие данные
        ((TextView) findViewById(R.id.tvName)).setText(getIntent().getStringExtra("Name"));
        ((TextView) findViewById(R.id.tvDescription)).setText(getIntent().getStringExtra("Description"));
        ((TextView) findViewById(R.id.tvVotes)).setText(getIntent().getStringExtra("Votes"));

        // кнопка GetIt для перехода на WebViewActivity
        ((Button) findViewById(R.id.btnGetIt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, WebviewActivity.class);
                intent.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(intent);
            }
        });

        // кнопка назад
        ((Button) findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
