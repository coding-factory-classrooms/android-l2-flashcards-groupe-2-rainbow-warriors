package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.location.GnssAntennaInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class FlashcardActivity extends AppCompatActivity {
    private static Class<?> classToPass;
    public static float screen_width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        Intent intent = getIntent();

        int index = intent.getIntExtra("index", -1);
        Flashcard flashcard = null;
        ArrayList<Flashcard> flashcards = null;
        flashcards = intent.getParcelableArrayListExtra("flashcards");

        if (index >= 0) {
            flashcard = flashcards.get(index);
            index++;
            TextView indexOnTotalTextView = findViewById(R.id.indexOnTotalTextView);
            indexOnTotalTextView.setText(index + " / " + flashcards.size());
        } else {
            flashcard = intent.getParcelableExtra("flashcard");
        }

        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(flashcard.questionText);

        ImageView questionPictureView = findViewById(R.id.questionPictureView);
        Button playSoundButton = findViewById(R.id.playSoundButton);


        /*
         *We check if the source is of audio or picture type and display it
         * */
        if (flashcard.sourceType.equals("picture")){
            playSoundButton.setVisibility(View.GONE);
            int picId = getResources().getIdentifier(flashcard.sourceName, "drawable", getPackageName());
            questionPictureView.setImageResource(picId);
        }
        if (flashcard.sourceType.equals("audio")){
            questionPictureView.setVisibility(View.GONE);
            int soundid = getResources().getIdentifier(flashcard.sourceName, "raw", getPackageName());
            final MediaPlayer mp = MediaPlayer.create(FlashcardActivity.this, soundid);
            playSoundButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    mp.start();
                } //Playing the sound
            });
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_width = metrics.widthPixels;
        questionPictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float scale =  FlashcardActivity.screen_width / v.getWidth();
                if(v.getScaleX() == 1) {
                    v.setScaleY(scale);
                    v.setScaleX(scale);
                }else{
                    v.setScaleY(1);
                    v.setScaleX(1);
                }
            }
        });

        ArrayList<Answer> randomizedAnswers = flashcard.answers;
        Collections.shuffle(randomizedAnswers); //Shuffling so that the answers are randomly placed
        RadioGroup radioGroup = findViewById(R.id.answerRadioGroup);

        /*
         *Creating a radioButton for each answer
         * */
        for (Answer answer: flashcard.answers) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answer.value);
            radioGroup.addView(radioButton);
        }
        final int[] goodAnswers = {intent.getIntExtra("goodAnswers", 0)};//number of good answers
        int finalIndex = index;
        Flashcard finalFlashcard = flashcard;
        ArrayList<Flashcard> finalFlashcards = flashcards;
        findViewById(R.id.validateAnswerButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RadioButton checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
                /*
                 *Checks if the user checked a radioButton
                 * */
                if (checkedButton == null) {
                    Toast toast = Toast.makeText(FlashcardActivity.this, "Vous devez sélectionner une réponse pour continuer", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                /*
                 *Checks the good answer and the selected answer
                 * */
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
                /*
                 *Checks if the answer is good and and allow user to pass to either the next question, the statistics or going back to the list of questions depending the cases
                 * */
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FlashcardActivity.this);
                if (selectedAnswer == rightAnswer) {
                    alertDialog.setTitle("Bonne réponse !");
                    goodAnswers[0]++;
                } else {
                    alertDialog.setTitle("Mauvaise réponse.");
                    alertDialog.setMessage("La bonne réponse était: \n\n" + rightAnswer.value);
                }

                String buttonMessage;
                if (finalIndex < 0) {
                    buttonMessage = "Retour à la liste";
                    FlashcardActivity.classToPass = ListQuestionsActivity.class;
                } else if (finalIndex == finalFlashcards.size()) {
                    buttonMessage = "Suivant";
                    FlashcardActivity.classToPass = StatisticsActivity.class;
                } else {
                    buttonMessage = "Question suivante";
                    FlashcardActivity.classToPass = FlashcardActivity.class;
                }

                alertDialog.setNeutralButton(buttonMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                         *To go back to the list of questions
                         * */
                        Intent intent = new Intent(FlashcardActivity.this, classToPass);
                        if (classToPass == ListQuestionsActivity.class) {
                            intent.putParcelableArrayListExtra("flashcards", finalFlashcards);
                            /*
                             *To go to the statistics
                             * */
                        } else if (classToPass == StatisticsActivity.class) {
                            intent.putExtra("difficulty", finalFlashcard.difficulty);
                            intent.putExtra("goodAnswers", goodAnswers[0]);
                            intent.putExtra("nbrQuestions", finalFlashcards.size());
                            /*
                             *To go to the next question
                             * */
                        } else {
                            intent.putExtra("index", finalIndex);
                            intent.putExtra("goodAnswers", goodAnswers[0]);
                            intent.putParcelableArrayListExtra("flashcards", finalFlashcards);
                            dialog.dismiss();
                        }
                        startActivity(intent);
                        finish();
                    }
                });

                AlertDialog alert = alertDialog.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
            }
        });

    }
}