package com.example.syn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    public static final String BUNDLE_STATUS = "Bundle_status";
    public static String STATUS_SCREEN_LIST_VOCABULARY = "status_voca";
    public static int TO_VOCABULARY = 1;
    public static int TO_GAME_MATCH = 2;
    public static int TO_GAME_MEMORY = 3;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout relativeVocabulary = findViewById(R.id.layout_vocabulary);
        RelativeLayout relativeGame1 = findViewById(R.id.layout_game1);
        RelativeLayout relativeGame2 = findViewById(R.id.layout_game2);
        Intent intent = new Intent(MainActivity.this, ListVocabulary.class);


        relativeVocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent, TO_VOCABULARY);
            }
        });

        relativeGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent, TO_GAME_MATCH);
            }
        });

        relativeGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent, TO_GAME_MEMORY);
            }
        });
    }
    public void startActivity(Intent intent, int statusScreen) {
        Bundle bundle = new Bundle();
        bundle.putInt(STATUS_SCREEN_LIST_VOCABULARY, statusScreen);
        intent.putExtra(BUNDLE_STATUS,bundle);
        startActivity(intent);
    }
}