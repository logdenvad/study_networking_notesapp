package my.studying.networking.notesapp_v2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import my.studying.networking.notesapp_v2.data.Note;
import my.studying.networking.notesapp_v2.data.NoteDao;
import my.studying.networking.notesapp_v2.data.NoteDatabase;

public class NoteRepository {

    private NoteDao noteDao;
    private ExecutorService executorService;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        this.noteDao = database.noteDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Note>> getAllNotes() {
        return null;
    }

    public LiveData<Note> getNoteById(int id) {
        return null;
    }

    public void insert(Note note) {
    }

    public void update(Note note) {
    }

    public void delete(Note note) {
    }
}
