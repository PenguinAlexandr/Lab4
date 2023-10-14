package stu.cn.ua.lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button askQuestionButton = findViewById(R.id.askQuestionButton);
        Button settingsButton = findViewById(R.id.settingsButton);
        Button exitButton = findViewById(R.id.exitButton);

        askQuestionButton.setOnClickListener(v -> {
            // Перехід до екрану для запитань
            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(v -> {
            // Перехід до екрану налаштувань
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        exitButton.setOnClickListener(v -> {
            // Виводимо користувача до домашнього екрану (головного меню)
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

    }
}
