package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class FlashcardActivity extends AppCompatActivity {
    private static Class<?> classToPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        Intent intent = getIntent();

        int index = intent.getIntExtra("index", -1);
        Flashcard flashcard = null;
        ArrayList<Flashcard> flashcards = null;

        if (index >= 0) {
            flashcards = intent.getParcelableArrayListExtra("flashcards");
            flashcard = flashcards.get(index);
            index++;
            TextView indexOnTotalTextView = findViewById(R.id.indexOnTotalTextView);
            indexOnTotalTextView.setText(index + " / " + flashcards.size());
        } else {
            flashcard = intent.getParcelableExtra("flashcard");
        }

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

        ArrayList<Answer> randomizedAnswers = flashcard.answers;
        Collections.shuffle(randomizedAnswers);
        RadioGroup radioGroup = findViewById(R.id.answerRadioGroup);

        for (Answer answer: flashcard.answers) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answer.value);
            radioGroup.addView(radioButton);
        }
        final int[] goodAnswers = {intent.getIntExtra("goodAnswers", 0)};
        int finalIndex = index;
        Flashcard finalFlashcard = flashcard;
        ArrayList<Flashcard> finalFlashcards = flashcards;
        findViewById(R.id.validateAnswerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
                Answer rightAnswer = null;
                Answer selectedAnswer = null;
                for (Answer answer: finalFlashcard.answers) {
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
                    goodAnswers[0]++;
                } else {
                    alertDialog.setTitle("Mauvaise réponse.");
                    alertDialog.setMessage("La bonne réponse était: \n\n" + rightAnswer.value);
                }

                String buttonMessage;
                //todo add third if for statistic, and rework condition
                if (finalFlashcards == null) {
                    buttonMessage = "Retour à la liste";
                    FlashcardActivity.classToPass = ListQuestionsActivity.class;
                } else if (finalIndex == finalFlashcards.size()) {
                    Intent intent = new Intent(FlashcardActivity.this, StatisticsActivity.class);
                    intent.putExtra("difficulty", finalFlashcard.difficulty);
                    intent.putExtra("goodAnswers", goodAnswers[0]);
                    intent.putExtra("nbrQuestions", 2);
                    startActivity(intent);
                    buttonMessage = "Suivant";
                    FlashcardActivity.classToPass = MainActivity.class;
                } else {
                    buttonMessage = "Question suivante";
                    FlashcardActivity.classToPass = FlashcardActivity.class;
                }
                alertDialog.setNeutralButton(buttonMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (classToPass == ListQuestionsActivity.class) {
                            finish();
                        }
                        Intent intent = new Intent(FlashcardActivity.this, classToPass);
                        intent.putExtra("index", finalIndex);
                        intent.putExtra("goodAnswers", goodAnswers[0]);
                        if (finalFlashcards != null) {
                            intent.putParcelableArrayListExtra("flashcards", finalFlashcards);
                        }
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = alertDialog.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
            }
        });

    }
}