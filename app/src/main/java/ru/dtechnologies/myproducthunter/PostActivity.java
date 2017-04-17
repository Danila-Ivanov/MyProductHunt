package ru.dtechnologies.myproducthunter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        byte[] byteArray = getIntent().getByteArrayExtra("Screenshot");

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ((ImageView) findViewById(R.id.screenshot)).setBackground(new BitmapDrawable(getResources(), bitmap));

        ((TextView) findViewById(R.id.tvName)).setText(getIntent().getStringExtra("Name"));
        ((TextView) findViewById(R.id.tvDescription)).setText(getIntent().getStringExtra("Description"));
        ((TextView) findViewById(R.id.tvVotes)).setText(getIntent().getStringExtra("Votes"));

        ((Button) findViewById(R.id.btnGetIt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, WebviewActivity.class);
                intent.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(intent);
            }
        });
    }
}
