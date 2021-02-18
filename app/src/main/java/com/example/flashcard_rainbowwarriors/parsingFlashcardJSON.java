package com.example.flashcard_rainbowwarriors;

import android.content.Context;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class parsingFlashcardJSON {
    static public ArrayList<Flashcard> retrieveFromJSON(String difficulty, Context context) {
        ArrayList<Flashcard> flashcards = new ArrayList<>();

        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();

        try (BufferedReader bfr = new BufferedReader(new InputStreamReader(context.getAssets().open("questions.json"))))
        {
            Object obj = jsonParser.parse(bfr);
            JSONObject difficulties = (JSONObject) obj;
            JSONArray list = (JSONArray) difficulties.get(difficulty);

            for (int i = 0; i < list.size(); i++) {
                Flashcard flashcard;
                JSONObject flashObj = (JSONObject) list.get(i);
                ArrayList<Answer> answers = new ArrayList<>();
                JSONArray answersArray =  (JSONArray) flashObj.get("answers");
                for (int j = 0; j < answersArray.size(); j++) {
                    JSONObject answerObj = (JSONObject) answersArray.get(j);
                    answers.add(new Answer(
                            (boolean) answerObj.get("isAnswer"),
                            (String) answerObj.get("value")
                    ));
                }

                flashcard = new Flashcard(
                        (String) flashObj.get("questionText"),
                        (String) flashObj.get("sourceType"),
                        (String) flashObj.get("sourceName"),
                        answers
                );
                flashcards.add(flashcard);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return flashcards;
    }
}
