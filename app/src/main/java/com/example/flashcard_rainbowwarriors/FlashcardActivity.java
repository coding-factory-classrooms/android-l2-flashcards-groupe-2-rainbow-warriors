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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        Intent intent = getIntent();
        Flashcard flashcard = intent.getParcelableExtra("flashcard");

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

                alertDialog.setNeutralButton("Test", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FlashcardActivity.this, "test", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });

    }
}