package com.example.davidloris_project.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.davidloris_project.Local.MyDatabase;
import com.example.davidloris_project.Local.SubjectDAO;
import com.example.davidloris_project.Model.Subject;

import java.util.List;

public class SubjectRepository {
    private SubjectDAO subjectDao;
    private LiveData<List<Subject>> allSubject;

    public SubjectRepository(Application application){
        MyDatabase database = MyDatabase.getInstance(application);
        subjectDao = database.subjectDAO();
        allSubject = subjectDao.getAllSubject();
    }

    public void insert(Subject subject){
        new InsertSubjectAsyncTask(subjectDao).execute(subject);
    }

    public LiveData<List<Subject>> getAllSubject(){
        return allSubject;
    }

    private static class InsertSubjectAsyncTask extends AsyncTask<Subject, Void, Void>{

        private SubjectDAO subjectDao;

        private InsertSubjectAsyncTask(SubjectDAO subjectDao){
            this.subjectDao = subjectDao;
        }

        @Override
        protected Void doInBackground(Subject... subjects) {
            subjectDao.insertSubject(subjects[0]);
            return null;
        }
    }

}
