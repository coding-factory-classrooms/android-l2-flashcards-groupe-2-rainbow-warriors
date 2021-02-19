package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ListQuestionsActivity extends AppCompatActivity {
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
         *Displays the list of all questions
         * */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        Intent intent = getIntent();
        ArrayList<Flashcard> flashcards = intent.getParcelableArrayListExtra("flashcards");

        adapter = new ListAdapter(flashcards);

        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}