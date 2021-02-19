package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private boolean isNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         *Get values from the questions.json in /assets
         * */
        ArrayList<Flashcard> easyFlashcards;
        ArrayList<Flashcard> mediumFlashcards;
        ArrayList<Flashcard> hardFlashcards;
        ArrayList<Flashcard> totalFlashcards = new ArrayList<Flashcard>();

        easyFlashcards = parsingFlashcardJSON.retrieveFromJSON("Facile", this);
        mediumFlashcards = parsingFlashcardJSON.retrieveFromJSON("Moyen", this);
        hardFlashcards = parsingFlashcardJSON.retrieveFromJSON("Difficile", this);

        easyFlashcards.addAll(mediumFlashcards);//Merge easy and medium difficulty arrays
        easyFlashcards.addAll(hardFlashcards); //Merge difficulty array
        totalFlashcards.addAll(easyFlashcards);

        /*
         *Sends the user to the "FlashcardActivity"
         * */
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                  alertDialog.setTitle("Choisir la difficulté");
                  String[] items = {"Facile","Moyen","Difficile"};
                  int checkedItem = 1;
                  alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(MainActivity.this, FlashcardActivity.class);
                          ArrayList<Flashcard> flashcards = parsingFlashcardJSON.retrieveFromJSON(items[which], MainActivity.this);
                          Collections.shuffle(flashcards);
                          intent.putParcelableArrayListExtra("flashcards", flashcards);
                          intent.putExtra("index", 0);
                          startActivity(intent);
                          dialog.dismiss();
                      }
                  });
                  AlertDialog alert = alertDialog.create();
                  alert.show();
                  alert.setCanceledOnTouchOutside(true);
              }
        });

        /*
         *Sends the user to the "ListQuestions" activity
         * */
        Button listOfQuestions = findViewById(R.id.listButton);
        listOfQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListQuestionsActivity.class);
                intent.putExtra("flashcards", totalFlashcards);
                startActivity(intent);
            }
        });

        /*
         *Sends the user to the "AboutActivity"
         * */
        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Access to the version of the application present in the build.gradle*/
                Context context = getApplicationContext();
                PackageManager packageManager = context.getPackageManager();
                String packageName = context.getPackageName();
                String myVersionName = "not available";
                try {
                    myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                // TODO This should take informations from manifest
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                intent.putExtra("appName", "Gun's Quizz");
                intent.putExtra("groupName", "Rainbow Warriors");
                intent.putExtra("versionName", myVersionName);
                startActivity(intent);
            }
        });
        /*
         *Modifies UI to either white or black mode for users.
         * */
        Button darkMode = findViewById(R.id.darkmodeButton);
        isNightMode = false;

        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNightMode = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    isNightMode = true;
                }
            }
        });
    }
}