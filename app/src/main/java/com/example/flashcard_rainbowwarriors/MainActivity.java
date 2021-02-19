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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         *Sends the user to the "FlashcardActivity"
         * */
        findViewById(R.id.startButton).setOnClickListener(this);

        /*
         *Sends the user to the "ListQuestions" activity
         * */
        findViewById(R.id.listButton).setOnClickListener(this);

        /*
         *Sends the user to the "AboutActivity"
         * */
        findViewById(R.id.aboutButton).setOnClickListener(this);

        /*
         *Modifies UI to either white or black mode for users.
         * */
        findViewById(R.id.darkmodeButton).setOnClickListener(this);

        isNightMode = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton: {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Choisir la difficulté");
                String[] items = {"Facile", "Moyen", "Difficile"};
                int checkedItem = 1;
                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, FlashcardActivity.class);
                        ArrayList<Flashcard> flashcards = parsingFlashcardJSON.retrieveFromJSON(items[which], MainActivity.this);
                        Collections.shuffle(flashcards);
                        flashcards = splitList(flashcards);
                        intent.putParcelableArrayListExtra("flashcards", flashcards);
                        intent.putExtra("index", 0);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
                alert.setCanceledOnTouchOutside(true);
                break;
            }

            case R.id.listButton: {
                /*
                 *Get values from the questions.json in /assets
                 * */
                ArrayList<Flashcard> totalFlashcards = new ArrayList<Flashcard>();

                for (String difficulty : new String[]{"Facile", "Moyen", "Difficile"}) {
                    totalFlashcards.addAll(parsingFlashcardJSON.retrieveFromJSON(difficulty, MainActivity.this));
                }
                Intent intent = new Intent(MainActivity.this, ListQuestionsActivity.class);
                intent.putExtra("flashcards", totalFlashcards);
                startActivity(intent);
                break;
            }

            case R.id.aboutButton: {
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

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                intent.putExtra("appName", "Gun's Quizz");
                intent.putExtra("groupName", "Rainbow Warriors");
                intent.putExtra("versionName", myVersionName);
                startActivity(intent);
                break;
            }

            case R.id.darkmodeButton: {
                if (isNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNightMode = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    isNightMode = true;
                }
                break;
            }
        }
    }
    /*
     *Split the ArrayList of flashcards so that there are only five left
     * */
    public ArrayList<Flashcard> splitList(ArrayList<Flashcard> flashcards) {
        ArrayList<Flashcard> result = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            result.add(flashcards.get(i));
        }
        return result;
    }
}