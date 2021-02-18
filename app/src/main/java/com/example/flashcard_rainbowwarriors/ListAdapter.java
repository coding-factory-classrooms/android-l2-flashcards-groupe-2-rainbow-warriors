package com.example.flashcard_rainbowwarriors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Flashcard> flashcards;

    public ListAdapter(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_question, parent, false);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);
        holder.question.setText(flashcard.getQuestionText());
        holder.difficulty.setText((flashcard.getDifficulty()));

        holder.itemView.setTag(flashcard);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rootItem:
                Context context = v.getContext();
                Flashcard flashcard = (Flashcard) v.getTag();
                Intent intent = new Intent(context, FlashcardActivity.class);
                intent.putExtra("flashcard", flashcard);
                context.startActivity(intent);
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView question;
        final TextView difficulty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.questionItemTextView);
            difficulty = itemView.findViewById(R.id.difficultyTextView);
        }
    }
}
