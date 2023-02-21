package com.example.syn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListVocabulary extends AppCompatActivity {

    RecyclerView recyclerVocabulary;
    VocabularyListAdapter adapter;
    int status = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lesson);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(MainActivity.BUNDLE_STATUS);
        recyclerVocabulary = findViewById(R.id.rv_lesson);
        TextView tvCount = findViewById(R.id.tv_count);

        status = bundle.getInt(MainActivity.STATUS_SCREEN_LIST_VOCABULARY);

        Button btnAdd = findViewById(R.id.btn_add);

        DatabaseHelper databaseHelper = new DatabaseHelper(ListVocabulary.this);
        ArrayList<Vocabulary> list = databaseHelper.getAllVocabulary();

        tvCount.setText(getResources().getString(R.string.text_count_list) + list.size());

        VocabularyListAdapter adapter = new VocabularyListAdapter(list, getApplicationContext(),status);
        recyclerVocabulary.setHasFixedSize(true);
        recyclerVocabulary.setLayoutManager(new LinearLayoutManager(this));
        recyclerVocabulary.setAdapter(adapter);
        if (status != 1) {
            btnAdd.setVisibility(View.INVISIBLE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListVocabulary.this, CreateVocabulary.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHelper databaseHelper = new DatabaseHelper(ListVocabulary.this);
        ArrayList<Vocabulary> list = databaseHelper.getAllVocabulary();

        adapter = new VocabularyListAdapter(list, getApplicationContext(),status);
        recyclerVocabulary.setAdapter(adapter);
    }
}
