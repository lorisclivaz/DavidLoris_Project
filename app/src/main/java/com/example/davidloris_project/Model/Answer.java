package com.example.davidloris_project.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "tabAnswer")
public class Answer {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int idAnswer;

    private String textAnswer;

    private String idAutor;

    private String idSubject;

    private String date;

    public int getIdAnswer() {
        return idAnswer;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public String getDate() {
        return date;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public void setDate(String date) {
        this.date = date;
    }
}