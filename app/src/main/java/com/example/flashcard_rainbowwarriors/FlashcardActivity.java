package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Map;

public class FlashcardActivity extends AppCompatActivity {

    private AnswerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        Intent intent = getIntent();
        Flashcard flashcard = intent.getParcelableExtra("flashcard");

        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(flashcard.questionText);

        // TODO look for sound or image resource when needed
        ImageView questionPictureView = findViewById(R.id.questionPictureView);
        questionPictureView.setImageDrawable(getResources().getDrawable(R.drawable.home_gun));
        questionPictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        RadioGroup radioGroup = findViewById(R.id.answerRadioGroup);

        for (Answer answer: flashcard.answers) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answer.value);
            radioGroup.addView(radioButton);
        }

        //Log.d("Flashcard Activity", flashcard.answers.get(0));
//        adapter = new AnswerAdapter(flashcard.answers);
//
//        RecyclerView recyclerView = findViewById(R.id.answerRecyclerView);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}