package com.example.mornhouse_test;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.company_name);
            getSupportActionBar().setSubtitle(R.string.company_task);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ((LinearLayout) findViewById(R.id.llBackground)).setBackgroundColor(Color.parseColor((getIntent().getStringExtra("color"))));
        ((TextView) findViewById(R.id.tvNumber)).setText(getIntent().getStringExtra("number"));
        ((TextView) findViewById(R.id.tvDescription)).setText(getIntent().getStringExtra("description"));

    }

}
