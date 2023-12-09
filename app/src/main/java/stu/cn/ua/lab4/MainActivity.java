package stu.cn.ua.lab4;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static LaunchListAdapter launchListAdapter;
    private static LaunchDatabase launchDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        launchListAdapter = new LaunchListAdapter();
        recyclerView.setAdapter(launchListAdapter);

        launchDatabase = LaunchDatabase.getInstance(this);

        if (isNetworkAvailable()) {
            fetchDataFromRemote();
        } else {
            fetchDataFromLocal();
        }
    }

    private void fetchDataFromRemote() {
        RetrofitClient.getInstance()
                .getSpaceXApi()
                .getLaunches()
                .enqueue(new Callback<List<Launch>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Launch>> call, @NonNull Response<List<Launch>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Launch> launches = response.body();
                            launchListAdapter.setLaunches(launches);

                            // Збереження даних в локальну базу даних за допомогою AsyncTask
                            new InsertLaunchesAsyncTask().execute(launches);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Launch>> call, @NonNull Throwable t) {
                        Log.e(TAG, "Failed to fetch data from remote", t);
                    }
                });
    }

    private void fetchDataFromLocal() {
        // Отримання даних з локальної бази даних за допомогою AsyncTask
        new GetLocalLaunchesAsyncTask().execute();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    // AsyncTask для вставки запусків у базу даних
    private static class InsertLaunchesAsyncTask extends AsyncTask<List<Launch>, Void, Void> {

        @Override
        protected Void doInBackground(List<Launch>... lists) {
            launchDatabase.launchDao().insertAll(lists[0]);
            return null;
        }
    }

    // AsyncTask для отримання запусків з бази даних
    private static class GetLocalLaunchesAsyncTask extends AsyncTask<Void, Void, List<Launch>> {

        @Override
        protected List<Launch> doInBackground(Void... voids) {
            return launchDatabase.launchDao().getAllLaunches();
        }

        @Override
        protected void onPostExecute(List<Launch> launches) {
            if (!launches.isEmpty()) {
                launchListAdapter.setLaunches(launches);
            }
        }
    }
}
