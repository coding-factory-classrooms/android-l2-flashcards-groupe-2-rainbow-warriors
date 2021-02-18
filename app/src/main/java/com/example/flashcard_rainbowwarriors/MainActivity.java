package com.example.flashcard_rainbowwarriors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Answer> list = new ArrayList<Answer>();
        list.add(new Answer(true, "Réponse 1"));
        list.add(new Answer(false, "Réponse 2"));
        Flashcard flashcard = new Flashcard("Question", "SourceType", "SourceName", list);

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
                          intent.putExtra("flashcard", flashcard);
                          startActivity(intent);
                          dialog.dismiss();
                      }
                  });
                  AlertDialog alert = alertDialog.create();
                  alert.show();
                  alert.setCanceledOnTouchOutside(true);
              }
        });

        Button listOfQuestions = findViewById(R.id.listButton);
        listOfQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListQuestionsActivity.class);
                //TODO retrieve all flashcards and send it to the ListQuestionsActivity
                //intent.putExtra("flashcard", flashcard);
                startActivity(intent);
            }
        });

        /*
         *Sends the user to the "AboutActivity" activity
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
    }
}