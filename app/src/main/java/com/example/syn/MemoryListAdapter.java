package com.example.syn;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoryListAdapter extends RecyclerView.Adapter<MemoryListAdapter.ViewHolder> {
    private ArrayList<String> listMatchWords;

    public ArrayList<String> getListMatchWords() {
        return listMatchWords;
    }

    public MemoryListAdapter(int length) {
        listMatchWords = new ArrayList<>();
        for(int i = 0; i < length; i ++) {
            listMatchWords.add("");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listView = layoutInflater.inflate(R.layout.item_memory_vocabulary_2, parent, false);
        final ViewHolder viewHolder = new MemoryListAdapter.ViewHolder(listView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listMatchWords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private EditText edEnglishWord;

        public ViewHolder(View itemView) {
           super(itemView);
            this.edEnglishWord = (EditText) itemView.findViewById(R.id.ed_english);
        }
    }
}
