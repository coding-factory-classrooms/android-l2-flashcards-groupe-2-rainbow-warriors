package com.example.flashcard_rainbowwarriors;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Flashcard implements Parcelable {
    String questionText;
    String sourceType;
    String sourceName;
    ArrayList<Answer> answers;
    int indexRightAnswer; //useless, might delete

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public int getIndexRightAnswer() {
        return indexRightAnswer;
    }

    public void setIndexRightAnswer(int indexRightAnswer) {
        this.indexRightAnswer = indexRightAnswer;
    }

    public Flashcard(String questionText, String sourceType, String sourceName, ArrayList<Answer> answers, int indexRightAnswer) {
        this.questionText = questionText;
        this.sourceType = sourceType;
        this.sourceName = sourceName;
        this.answers = answers;
        this.indexRightAnswer = indexRightAnswer;
    }

    protected Flashcard(Parcel in) {
        questionText = in.readString();
        sourceType = in.readString();
        sourceName = in.readString();
        answers = in.createTypedArrayList(Answer.CREATOR);
        indexRightAnswer = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionText);
        dest.writeString(sourceType);
        dest.writeString(sourceName);
        dest.writeTypedList(answers);
        dest.writeInt(indexRightAnswer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Flashcard> CREATOR = new Creator<Flashcard>() {
        @Override
        public Flashcard createFromParcel(Parcel in) {
            return new Flashcard(in);
        }

        @Override
        public Flashcard[] newArray(int size) {
            return new Flashcard[size];
        }
    };
}
