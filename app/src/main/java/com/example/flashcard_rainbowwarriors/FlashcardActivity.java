package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class FlashcardActivity extends AppCompatActivity {
    private static Class<?> classToPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        Intent intent = getIntent();
        ArrayList<Flashcard> flashcards = intent.getParcelableArrayListExtra("flashcards");
        int index = intent.getIntExtra("index", 0);
        Flashcard flashcard = flashcards.get(index);
        index++;

        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(flashcard.questionText);

        ImageView questionPictureView = findViewById(R.id.questionPictureView);
        //Button playSoundButton = findViewById(R.id.playSoundButton);
        // TODO look for sound or image resource when needed

        questionPictureView.setImageDrawable(getResources().getDrawable(R.drawable.home_gun));

        RadioGroup radioGroup = findViewById(R.id.answerRadioGroup);

        ArrayList<Answer> randomizedAnswers = flashcard.answers;
        Collections.shuffle(randomizedAnswers);
        for (Answer answer: randomizedAnswers) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answer.value);
            radioGroup.addView(radioButton);
        }

        int finalIndex = index;
        findViewById(R.id.validateAnswerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
                Answer rightAnswer = null;
                Answer selectedAnswer = null;
                for (Answer answer: flashcard.answers) {
                    if (answer.isAnswer) {
                        rightAnswer = answer;
                    }
                    if (answer.value == checkedButton.getText()) {
                        selectedAnswer = answer;
                    }
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FlashcardActivity.this);
                if (selectedAnswer == rightAnswer) {
                    alertDialog.setTitle("Bonne réponse !");
                } else {
                    alertDialog.setTitle("Mauvaise réponse.");
                    alertDialog.setMessage("La bonne réponse était: \n\n" + rightAnswer.value);
                }

                String buttonMessage;
                if (finalIndex == flashcards.size()) {
                    buttonMessage = "Suivant";
                    FlashcardActivity.classToPass = MainActivity.class;
                } else {
                    buttonMessage = "Question suivante";
                    FlashcardActivity.classToPass = FlashcardActivity.class;
                }
                alertDialog.setNeutralButton(buttonMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(FlashcardActivity.this, classToPass);
                        intent.putExtra("index", finalIndex);
                        intent.putParcelableArrayListExtra("flashcards", flashcards);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });

    }
}