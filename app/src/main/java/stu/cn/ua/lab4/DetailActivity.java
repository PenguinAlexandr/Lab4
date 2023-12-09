package stu.cn.ua.lab4;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Отримання посилання на ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Встановлення стрілки повернення
        }

        TextView missionNameTextView = findViewById(R.id.missionNameTextView);
        TextView launchDateTextView = findViewById(R.id.launchDateTextView);
        TextView detailsTextView = findViewById(R.id.detailsTextView);
        ImageView missionPatchImageView = findViewById(R.id.missionPatchImageView);

        // Отриматння даних з інтенту, які були передані з попередньої активності
        if (getIntent().hasExtra("missionName") &&
                getIntent().hasExtra("launchDate") &&
                getIntent().hasExtra("details") &&
                getIntent().hasExtra("missionPatchUrl")) {

            String missionName = getIntent().getStringExtra("missionName");
            String launchDate = getIntent().getStringExtra("launchDate");
            String details = getIntent().getStringExtra("details");
            String missionPatchUrl = getIntent().getStringExtra("missionPatchUrl");

            // Встановлення даних на відповідних елементах UI
            missionNameTextView.setText(missionName);
            launchDateTextView.setText(launchDate);
            detailsTextView.setText(details);

            // Використання бібліотеки Picasso для завантаження зображення
            Picasso.get().load(missionPatchUrl).into(missionPatchImageView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Обробка кліку на стрілку повернення
        if (item.getItemId() == android.R.id.home) {
            finish(); // Завершення activity, щоб повернутися на головне активіті
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
