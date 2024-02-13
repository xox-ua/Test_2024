package com.example.mornhouse_test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mornhouse_test.db.AppDataBase;
import com.example.mornhouse_test.db.Data;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private DataListAdapter dataListAdapter;
    private final String httpNumber = "http://numbersapi.com/";
    private final String httpNumberRandom = "http://numbersapi.com/random/math";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.company_name);
            getSupportActionBar().setSubtitle(R.string.company_task);
        }

        Button btnGetFact = findViewById(R.id.btn_get_fact);
        btnGetFact.setOnClickListener(v -> {
            EditText editText = findViewById(R.id.edit_text);
            if (TextUtils.isEmpty(editText.getText().toString())) {
                Toast.makeText(this, "Please enter a number.", Toast.LENGTH_SHORT).show();
                Log.d("BUTTON_GetFact", "EditText is empty");
            } else {
                simpleAsyncAPICalls("manual", httpNumber + editText.getText().toString()); //blue
            }
        });

        Button btnGetFactRandom = findViewById(R.id.btn_get_fact_random);
        btnGetFactRandom.setOnClickListener(v -> simpleAsyncAPICalls("random", httpNumberRandom)); // green

        initRecyclerView();
        loadList();
    }


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        dataListAdapter = new DataListAdapter(this);
        recyclerView.setAdapter(dataListAdapter);
    }


    private void loadList(){
        AppDataBase db = AppDataBase.getDbInstance(this.getApplicationContext());
        List<Data> dataList = db.userDao().getAllData();
        dataListAdapter.setDataList(dataList);
    }


    @SuppressLint("NotifyDataSetChanged")
    private void saveData(String method, String number, String description){
        AppDataBase db = AppDataBase.getDbInstance(this.getApplicationContext());

        Data data = new Data();
        data.method = method;
        data.number = number;
        data.description = description;
        db.userDao().insertData(data);

        runOnUiThread(() -> {
            List<Data> dataList = db.userDao().getAllData();
            dataListAdapter.setDataList(dataList);
            dataListAdapter.notifyDataSetChanged();
        });
    }

    public void simpleAsyncAPICalls(String method, String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("simpleAsyncAPICalls", "Failure: " + e);
                Toast.makeText(MainActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String str = response.body().string();
                    String[] splitString = str.split(" ", 2);
                    saveData(method, splitString[0], splitString[1]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
