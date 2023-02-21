package com.example.syn;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VocabularyListAdapter extends RecyclerView.Adapter<VocabularyListAdapter.ViewHolder> {
    public static final String ID_VOCABULARY = "id_Vocabulary";
    private ArrayList<Vocabulary> listVocabulary;
    private Context context;
    private int status;

    public VocabularyListAdapter(ArrayList<Vocabulary> listVocabulary, Context context) {
        this.listVocabulary = listVocabulary;
        this.status = 1;
        this.context = context;
    }

    public VocabularyListAdapter(ArrayList<Vocabulary> listVocabulary, Context context, int status) {
        this.listVocabulary = listVocabulary;
        this.status = status;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listView = layoutInflater.inflate(R.layout.item_learn_lession, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(listView, new OnClickButton(), new OnClickButton());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyListAdapter.ViewHolder viewHolder, int position) {
        final Vocabulary vocabulary = listVocabulary.get(position);
        viewHolder.tvName.setText(vocabulary.name);
        viewHolder.tvCount.setText(String.valueOf(vocabulary.length));
        if (status == 1) {
            viewHolder.tvStt.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvStt.setVisibility(View.VISIBLE);
            viewHolder.tvStt.setText(String.valueOf(position + 1));
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.tvAction.setText("Play");
        }

        viewHolder.onClickButtonAdd.SetIntent(position, context, CreateVocabulary.class);
        switch (status) {
            case 1:
                break;
            case 2:  viewHolder.onClickButtonAction.SetIntent(position, context, ActivityMemoryVocabulary.class);
                break;
            default:  viewHolder.onClickButtonAction.SetIntent(position, context, ActivityMatchWords.class);
        }
    }

    @Override
    public int getItemCount() {
        return listVocabulary.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tvName;
        public TextView tvCount;
        public TextView tvStt;
        public TextView tvAction;

        public OnClickButton onClickButtonAdd;
        public OnClickButton onClickButtonAction;

        public ViewHolder(View itemView, OnClickButton onClickButtonAdd, OnClickButton onClickButtonAction) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_edit);
            this.tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.tvStt = (TextView) itemView.findViewById(R.id.tv_stt);
            this.tvAction = (TextView) itemView.findViewById(R.id.tv_learn);

            this.onClickButtonAdd = onClickButtonAdd;
            this.onClickButtonAction = onClickButtonAction;

            this.imageView.setOnClickListener(this.onClickButtonAdd);
            this.tvAction.setOnClickListener(this.onClickButtonAction);
        }
    }

    private class OnClickButton implements View.OnClickListener {
        private Context context;
        private  Intent intent;

        public void SetIntent(int position, Context context, Class<?> cls) {
            this.context = context;
            intent = new Intent(context, cls);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ID_VOCABULARY, listVocabulary.get(position).getId());
        }

        @Override
        public void onClick(View v) {
            context.startActivity(intent);
        }
    }
}

