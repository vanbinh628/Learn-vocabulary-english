package com.example.syn;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateVocabulary extends AppCompatActivity {
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_list_vocabulary);

        editLength.setText("5");

        // Init recyclerview
        InitRecyclerview(recyclerView, 5);

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
                    View view=recyclerView.getChildAt(i); // This will give you entire row(child) from RecyclerView
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
                    Vocabulary vocabulary = new Vocabulary(name,listWords, listMeans, listWords.size());
                    final DatabaseHelper databaseHelper = new DatabaseHelper(CreateVocabulary.this);
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





        /*Button btnLearn = findViewById(R.id.btn_learn);
        Button btnGame = findViewById(R.id.btn_game);


        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                Vocabulary vocabulary = new Vocabulary(0,"Day 2", "My, love, give","cua, yêu, cho", 3);
                databaseHelper.addOne(vocabulary);
            }
        });
        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                List<Vocabulary> listVocabulary = databaseHelper.getAllVocabulary();
                String notify = "";
                for (int i =0; i<listVocabulary.size(); i++) {
                    notify += listVocabulary.get(i).toString() + "\n";
                }
                Toast.makeText(getApplicationContext(), notify,Toast.LENGTH_SHORT).show();
            }
        });*/
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