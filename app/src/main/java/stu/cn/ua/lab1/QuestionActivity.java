package stu.cn.ua.lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuestionActivity extends Activity {

    private EditText questionEditText;
    private TextView answerTextView;

    private SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionEditText = findViewById(R.id.questionEditText);
        Button askButton = findViewById(R.id.askButton);
        answerTextView = findViewById(R.id.answerTextView);
        Button backToMainButton = findViewById(R.id.backToMainButton);

        // Ініціалізація SharedPreferences
        sharedPreferences = getSharedPreferences("user_settings", MODE_PRIVATE);

        askButton.setOnClickListener(v -> {
            // Отримання запитання введеного користувачем
            String userQuestion = questionEditText.getText().toString();

            // Перевірка на пусте запитання
            if (userQuestion.isEmpty()) {
                answerTextView.setText("Будь ласка, введіть запитання.");
                return;
            }

            // Отримання даних користувача з SharedPreferences
            String firstName = sharedPreferences.getString("first_name", "");
            String lastName = sharedPreferences.getString("last_name", "");
            String birthDate = sharedPreferences.getString("birth_date", "");
            boolean isMale = sharedPreferences.getBoolean("is_male", false);
            sharedPreferences.getBoolean("is_female", false);

            // Розрахунок числових значень
            int numericValue = calculateNumericValue(firstName, lastName, birthDate, isMale, userQuestion);

            // Генерація відповіді на базі числового значення
            String answer = generateAnswer(numericValue);

            // Відображення відповіді
            answerTextView.setText("Ваше запитання: " + userQuestion + "\nВідповідь: " + answer);
        });

        backToMainButton.setOnClickListener(v -> {
            // Перехід на головний екран
            Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private int calculateNumericValue(String firstName, String lastName, String birthDate, boolean isMale, String userQuestion) {
        String combinedData = firstName + lastName + birthDate + (isMale ? "male" : "female") + userQuestion;
        int numericValue = 0;
        for (char c : combinedData.toCharArray()) {
            numericValue += c;
        }
        return numericValue;
    }

    private String generateAnswer(int numericValue) {
        String[] answers = {"Так", "Ні", "Можливо", "Цілком вірогідно", "Малоймовірно", "Не знаю"};
        int index = numericValue % answers.length;
        return answers[index];
    }
}
