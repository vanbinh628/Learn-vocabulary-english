package com.example.syn;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityEditVocabulary extends AppCompatActivity {
    private WordsListAdapter adapter = null;
    @Override
    protected void onStart(){
        super.onStart();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_vocabulary);

        Button btnReload = findViewById(R.id.btn_reload_recyclerview);
        Button btnSubmit = findViewById(R.id.btn_submit);
        EditText editName = findViewById(R.id.edit_name);
        EditText editLength = findViewById(R.id.edit_length);
        ImageView imageDelete = findViewById(R.id.image_delete);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_list_vocabulary);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        int id = intent.getIntExtra(VocabularyListAdapter.ID_VOCABULARY, -1);
        DatabaseHelper databaseHelper = new DatabaseHelper(ActivityEditVocabulary.this);
        Vocabulary vocabulary = databaseHelper.getVocabulary(id);

        if (id == -1 || vocabulary == null) {
            Toast.makeText(this, "get vocabulary is error", Toast.LENGTH_LONG).show();
            return;
        }

        imageDelete.setVisibility(View.VISIBLE);
        // Init recyclerview
        InitRecyclerview(recyclerView, vocabulary);

        editName.setText(vocabulary.name);
        editLength.setText(String.valueOf(vocabulary.length));

        ArrayList<String> listWords = new ArrayList<>();
        ArrayList<String> listMeans = new ArrayList<>();

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strLength = editLength.getText().toString();
                if (isInteger(strLength)) {
                    InitRecyclerview(recyclerView, Integer.parseInt(strLength));
                } else {
                    Toast.makeText(getApplicationContext(), "Input is number", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMeans.clear();
                listWords.clear();
                for(int i=0;i<adapter.getListMatchWords().size();i++)
                {
                    View view = recyclerView.getChildAt(i); // This will give you entire row(child) from RecyclerView
                    if(view!=null)
                    {
                        EditText editWord= (EditText) view.findViewById(R.id.edit_word);
                        EditText editMean= (EditText) view.findViewById(R.id.edit_mean);
                        listWords.add(editWord.getText().toString());
                        listMeans.add(editMean.getText().toString());
                    }

                }
                String name = editName.getText().toString().trim();
                if (isItemEmptyListString(listMeans) && isItemEmptyListString(listWords) && !name.equals("")) {
                    Vocabulary vocabulary = new Vocabulary(name,listWords, listMeans, listMeans.size());
                    boolean isSuccess = databaseHelper.addOne(vocabulary);
                    if (isSuccess) {
                        Toast.makeText(getApplicationContext(), "success " + vocabulary.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "is don't edit text is empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = databaseHelper.deleteVocabulary(vocabulary);
                String strNotify = isSuccess ? "success" : "failed";
                Toast.makeText(getApplicationContext(), "Delete " + strNotify+ " vocabulary : " + vocabulary.name, Toast.LENGTH_SHORT).show();
            }
        });
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

    private void InitRecyclerview (RecyclerView recyclerView, Vocabulary vocabulary) {
        adapter = new WordsListAdapter(vocabulary);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void InitRecyclerview (RecyclerView recyclerView, int length) {
        adapter = new WordsListAdapter(length);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private boolean isItemEmptyListString(ArrayList <String> list) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).trim().equals("")) {
                return false;
            }
        }
        return true;
    }
}