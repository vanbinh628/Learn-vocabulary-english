package com.example.syn;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityMemoryVocabulary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_memory_vocabulary);


        RecyclerView recyclerView = findViewById(R.id.recycler_list_vocabulary);
        Button btnCheck = findViewById(R.id.btn_submit);
        TextView tvNotify = findViewById(R.id.tv_notify);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        int id = intent.getIntExtra(VocabularyListAdapter.ID_VOCABULARY, -1);
        DatabaseHelper databaseHelper = new DatabaseHelper(ActivityMemoryVocabulary.this);
        Vocabulary vocabulary = databaseHelper.getVocabulary(id);

        if (id == -1 || vocabulary == null) {
            Toast.makeText(this, "get vocabulary is error", Toast.LENGTH_LONG).show();
            return;
        }

        ListWord listEnglish = new ListWord(vocabulary.words);
        ListWord listVietnamese = new ListWord(vocabulary.means);

        tvNotify.setText("Hãy viết các lại các từ, có nghĩa sau đây: " + listVietnamese.ConvertArrayWordToString());

        MemoryListAdapter adapter = new MemoryListAdapter(vocabulary.length);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList = new ArrayList<>();
                // get item in adapter input for arrayList
                for (int i = 0; i < adapter.getListMatchWords().size(); i++) {
                    View viewLayout = recyclerView.getChildAt(i);
                    EditText edResult = viewLayout.findViewById(R.id.ed_english);
                    arrayList.add(edResult.getText().toString());
                }
                HashMap<String, String> hashMapVocabulary = new HashMap<>();
                for (int i = 0; i < vocabulary.length; i++) {
                    MatchWords matchWords = new MatchWords(listEnglish.getArrayWord()[i],listVietnamese.getArrayWord()[i]);
                    hashMapVocabulary.put(listEnglish.getArrayWord()[i],listVietnamese.getArrayWord()[i]);
                }
                for (int i =0 ;i < arrayList.size(); i++) {
                    hashMapVocabulary.remove(arrayList.get(i));
                }
                if (hashMapVocabulary.size() > 0) {
                    ListWord listWordIsNotTrue = new ListWord((hashMapVocabulary.values().toArray(new String[0]))) ;
                    String strWordIsNotTrue = listWordIsNotTrue.ConvertArrayWordToString();
                    tvNotify.setText("Cố lên, bạn chỉ còn các từ: " + strWordIsNotTrue);
                } else {
                    tvNotify.setText( "Chúc mừng bạn");
                }
            }
        });
    }
}