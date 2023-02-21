package com.example.syn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchWordsListAdapter extends RecyclerView.Adapter<MatchWordsListAdapter.ViewHolder> {
    private ArrayList<MatchWords> listMatchWords;

    public ArrayList<MatchWords> getListMatchWords() {
        return listMatchWords;
    }

    public MatchWordsListAdapter(ArrayList<MatchWords> matchWords) {
        this.listMatchWords = matchWords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listView = layoutInflater.inflate(R.layout.item_game_vocabulary, parent, false);
        final ViewHolder viewHolder = new MatchWordsListAdapter.ViewHolder(listView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchWordsListAdapter.ViewHolder holder, int position) {
        MatchWords matchWords = listMatchWords.get(position);
        char letter = (char)(65 + position);
        String strWords =  letter + ". " + matchWords.englishWord;
        holder.tvEnglishWord.setText(strWords);
        holder.edAnswer.setText(letter + "- ");
        String strMeans = Integer.toString(position + 1) + ". " + matchWords.vietnameseWord;
        holder.tvVietnameseWord.setText(strMeans);
    }

    @Override
    public int getItemCount() {
        return listMatchWords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVietnameseWord;
        private TextView tvEnglishWord;
        private EditText edAnswer;

        public TextView getEdEnglishWord() {
            return tvEnglishWord;
        }
        public TextView getTvVietnameseWord() {
            return tvVietnameseWord;
        }

        public ViewHolder(View itemView) {
           super(itemView);
            this.tvEnglishWord = (TextView) itemView.findViewById(R.id.tv_english);
            this.tvVietnameseWord = (TextView) itemView.findViewById(R.id.tv_vietnamese);

            this.edAnswer = (EditText) itemView.findViewById(R.id.ed_answer);

        }
    }
}
