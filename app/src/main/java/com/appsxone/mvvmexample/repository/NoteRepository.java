package com.appsxone.mvvmexample.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.appsxone.mvvmexample.dao.NoteDao;
import com.appsxone.mvvmexample.model.Note;
import com.appsxone.mvvmexample.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new OperationsAsyncTask(noteDao, 1).execute(note);
    }

    public void update(Note note) {
        new OperationsAsyncTask(noteDao, 2).execute(note);
    }

    public void delete(Note note) {
        new OperationsAsyncTask(noteDao, 3).execute(note);
    }

    public void deleteAllNotes() {
        new OperationsAsyncTask(noteDao, 4).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class OperationsAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;
        private int perform;

        private OperationsAsyncTask(NoteDao noteDao, int perform) {
            this.noteDao = noteDao;
            this.perform = perform;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            if (perform == 1) {
                noteDao.insert(notes[0]);
            } else if (perform == 2) {
                noteDao.update(notes[0]);
            } else if (perform == 3) {
                noteDao.delete(notes[0]);
            } else if (perform == 4) {
                noteDao.deleteAllNotes();
            }
            return null;
        }
    }
}