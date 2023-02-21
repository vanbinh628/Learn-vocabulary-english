package com.example.syn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityMatchWords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_match_vocabulary);

        RecyclerView recyclerView = findViewById(R.id.recycler_list_vocabulary);
        Button btnCheck = findViewById(R.id.btn_submit);
        TextView tvName = findViewById(R.id.tv_name);

        Intent intent = getIntent();
        if(null == intent) {
            return;
        }
        int id =intent.getIntExtra(VocabularyListAdapter.ID_VOCABULARY, -1);

        DatabaseHelper databaseHelper = new DatabaseHelper(ActivityMatchWords.this);
        Vocabulary vocabulary = databaseHelper.getVocabulary(id);

        tvName.setText(vocabulary.name);
        Toast.makeText(getApplicationContext(), "id " + String.valueOf(vocabulary.id) ,Toast.LENGTH_SHORT).show();

        ListWord listEnglish = new ListWord(vocabulary.words);
        ListWord listVietnamese = new ListWord(vocabulary.means);
        ListWord listVietnameseFirst = new ListWord(vocabulary.means);

        listVietnamese.ShuffleArray();

        ArrayList<MatchWords> wordsArrayList = new ArrayList<>();
        for (int i =0; i< listEnglish.getArrayWord().length; i++) {
            wordsArrayList.add(new MatchWords(listEnglish.getArrayWord()[i], listVietnamese.getArrayWord()[i]));
        }
        MatchWordsListAdapter adapter = new MatchWordsListAdapter(wordsArrayList);

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
                    EditText edResult = viewLayout.findViewById(R.id.ed_answer);
                    String stringResult = edResult.getText().toString();
                    arrayList.add(stringResult.substring(stringResult.length() -1));
                }

                int [] inputArrayResult = new int[arrayList.size()];
                if (isArrayConvertString(arrayList)) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        inputArrayResult[i] = Integer.parseInt(arrayList.get(i));
                    }
                    if (isSameTowArray(inputArrayResult, ArrayResultMatchWords(listVietnameseFirst.getArrayWord(),listVietnamese.getArrayWord()))) {
                        Toast.makeText(getApplicationContext(), "You win", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "You lose", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Item isn't empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean isArrayConvertString(ArrayList<String> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (!isInteger(arrayList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int [] ArrayResultMatchWords(String[] wordsFist, String [] wordsShuffle) {
        // Save position wordsFist when after shuffle

        HashMap<String,Integer> saveWordsPosition = new HashMap<>();
        for (int i =0; i < wordsFist.length; i++) {
            saveWordsPosition.put(wordsShuffle[i],i);
        }

        // Put hashmap of saveWordsPosition in resultArray and increase 1
        int[] resultArray = new int[wordsFist.length];
        for (int i = 0; i < wordsShuffle.length; i ++) {
            if (saveWordsPosition.containsKey(wordsFist[i])) {
                resultArray[i] = saveWordsPosition.get(wordsFist[i]) + 1;
            }
        }
        return resultArray;
    }

    boolean isSameTowArray(int [] array1, int [] array2) {
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != (array2[i])) {
                return false;
            }
        }
        return true;
    }
}