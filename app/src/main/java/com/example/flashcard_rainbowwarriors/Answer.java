package com.example.flashcard_rainbowwarriors;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    boolean isAnswer;
    String value;

    public Answer(boolean isAnswer, String value) {
        this.isAnswer = isAnswer;
        this.value = value;
    }

    protected Answer(Parcel in) {
        isAnswer = in.readByte() != 0;
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isAnswer ? 1 : 0));
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
