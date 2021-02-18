package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Intent intent = getIntent();
        int goodAnswers = intent.getIntExtra("goodAnswers", 0);
        int nbrQuestions = 5;
        String difficulty = intent.getStringExtra("difficulty");

        establishStatistics("Moyen", 4, 5);
        establishStatistics(difficulty, goodAnswers, nbrQuestions);


    }

    public void establishStatistics(String difficulty, int goodAnswers, int nbrQuestions){
        TextView difficultyLevel = findViewById(R.id.difficultyTextView);
        TextView successPercentage = findViewById(R.id.successPercentageTextView);
        TextView goodAnswerRate = findViewById(R.id.goodAnswerRateTextView);
        Button backHome = findViewById(R.id.backHome);

        difficultyLevel.setText("Difficulty level : " + difficulty);
        goodAnswerRate.setText(goodAnswers + " réponses bonnes sur " + nbrQuestions + " questions");
        successPercentage.setText("Pourcentage de bonnes réponses : " + 100 * goodAnswers/nbrQuestions + "%");

        backHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}