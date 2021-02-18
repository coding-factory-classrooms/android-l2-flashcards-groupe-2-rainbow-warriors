package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ListQuestionsActivity extends AppCompatActivity {
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        //TODO recevoir toutes les flashcards



//        Intent intent = getIntent();
//        Flashcard flashcard = intent.getParcelableExtra("flashcard");

        ArrayList<Flashcard> flashcards = new ArrayList<>();
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(new Answer(true, "Réponse 1"));
        answers.add(new Answer(false, "Réponse 2"));
        for (int i = 0; i < 30; i++) {
            flashcards.add(new Flashcard("Question " + i, "SourceType", "SourceName", answers));
        }

        adapter = new ListAdapter(flashcards);

        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}