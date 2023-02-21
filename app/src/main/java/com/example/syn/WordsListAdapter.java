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

public class WordsListAdapter extends RecyclerView.Adapter<WordsListAdapter.ViewHolder> {
    private ArrayList<MatchWords> listMatchWords;

    public ArrayList<MatchWords> getListMatchWords() {
        return listMatchWords;
    }

    public void setListMatchWords(ArrayList<MatchWords> listMatchWords) {
        this.listMatchWords = listMatchWords;
    }

    public WordsListAdapter(ArrayList<MatchWords> matchWords) {
        this.listMatchWords = matchWords;
    }
    public WordsListAdapter(int length) {
        listMatchWords = new ArrayList<>();
        for (int i =0; i < length; i ++) {
            listMatchWords.add(new MatchWords("",""));
        }
    }

    public WordsListAdapter(Vocabulary vocabulary) {
        listMatchWords = new ArrayList<>();
        ListWord listWord = new ListWord(vocabulary.words);
        ListWord listMean = new ListWord(vocabulary.means);
        for (int i =0; i < vocabulary.length; i ++) {
            listMatchWords.add(new MatchWords(listWord.getArrayWord()[i],listMean.getArrayWord()[i]));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listView = layoutInflater.inflate(R.layout.item_create_vocabulary, parent, false);
        final ViewHolder viewHolder = new WordsListAdapter.ViewHolder(listView, new EditTextUpdateList(), new EditTextUpdateList());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordsListAdapter.ViewHolder holder, int position) {
        MatchWords matchWords = listMatchWords.get(position);
        holder.tvEnglishWord.setText(matchWords.englishWord);
        holder.tvVietnameseWord.setText(matchWords.vietnameseWord);

        holder.mEditTextEnglishListener.updatePosition(position, true, false);
        holder.mEditTextVietnameseListener.updatePosition(position, false, true);

        holder.tvEnglishWord.addTextChangedListener(holder.mEditTextEnglishListener);
        holder.tvVietnameseWord.addTextChangedListener(holder.mEditTextVietnameseListener);
    }

    @Override
    public void onViewDetachedFromWindow(WordsListAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.tvEnglishWord.removeTextChangedListener(holder.mEditTextEnglishListener);
        holder.tvVietnameseWord.removeTextChangedListener(holder.mEditTextVietnameseListener);
    }

    @Override
    public int getItemCount() {
        return listMatchWords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private EditText tvEnglishWord;
        private EditText tvVietnameseWord;

        public EditTextUpdateList mEditTextEnglishListener;
        public EditTextUpdateList mEditTextVietnameseListener;

        public ViewHolder(View itemView, EditTextUpdateList editTextEnglishListener , EditTextUpdateList editTextVietnameseListener) {
           super(itemView);
            this.tvEnglishWord = (EditText) itemView.findViewById(R.id.edit_word);
            this.tvVietnameseWord = (EditText) itemView.findViewById(R.id.edit_mean);

            this.mEditTextEnglishListener = editTextEnglishListener;
            this.mEditTextVietnameseListener = editTextVietnameseListener;
        }
    }

    private class EditTextUpdateList implements TextWatcher {
        private int position;
        private boolean updateEnglish = false;
        private boolean updateVietnamese = false;
        public void updatePosition(int position, boolean updateEnglish, boolean updateVietnamese) {
            this.position = position;
            this.updateEnglish = updateEnglish;
            this.updateVietnamese = updateVietnamese;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (updateEnglish) {
               listMatchWords.get(position).setEnglishWord(s.toString());
            }
            if (updateVietnamese) {
                listMatchWords.get(position).setVietnameseWord(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
