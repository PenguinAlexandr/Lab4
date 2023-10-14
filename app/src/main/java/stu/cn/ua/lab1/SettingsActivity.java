package stu.cn.ua.lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

public class SettingsActivity extends Activity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private TextView birthDateTextView;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;

    private SharedPreferences sharedPreferences;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        birthDateTextView = findViewById(R.id.birthDateTextView);
        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button backToMainButton = findViewById(R.id.backToMainButton);
        Button selectDateButton = findViewById(R.id.selectDateButton);

        // Ініціалізація SharedPreferences
        sharedPreferences = getSharedPreferences("user_settings", MODE_PRIVATE);

        // Відновлення збережених даних користувача
        firstNameEditText.setText(sharedPreferences.getString("first_name", ""));
        lastNameEditText.setText(sharedPreferences.getString("last_name", ""));
        setBirthDate(sharedPreferences.getString("birth_date", ""));
        maleRadioButton.setChecked(sharedPreferences.getBoolean("is_male", false));
        femaleRadioButton.setChecked(sharedPreferences.getBoolean("is_female", false));

        genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.maleRadioButton) {
                femaleRadioButton.setChecked(false);
            } else if (checkedId == R.id.femaleRadioButton) {
                maleRadioButton.setChecked(false);
            }
        });

        saveButton.setOnClickListener(v -> {
            // Отримання і збереження даних користувача
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String birthDate = birthDateTextView.getText().toString();
            boolean isMale = maleRadioButton.isChecked();
            boolean isFemale = femaleRadioButton.isChecked();

            // Збереження даних в SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first_name", firstName);
            editor.putString("last_name", lastName);
            editor.putString("birth_date", birthDate);
            editor.putBoolean("is_male", isMale);
            editor.putBoolean("is_female", isFemale);
            editor.apply();

            finish();
        });

        backToMainButton.setOnClickListener(v -> {
            // Перехід на головний екран
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        selectDateButton.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, monthOfYear, dayOfMonth) -> {
                    calendar.set(selectedYear, monthOfYear, dayOfMonth);
                    setBirthDate(formatDate(selectedYear, monthOfYear, dayOfMonth));
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    @SuppressLint("DefaultLocale")
    private String formatDate(int year, int month, int day) {
        return String.format("%04d-%02d-%02d", year, month + 1, day);
    }

    private void setBirthDate(String date) {
        birthDateTextView.setText(date);
    }
}
