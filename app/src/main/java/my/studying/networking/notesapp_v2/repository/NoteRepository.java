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

    private final NoteDao noteDao;
    private final ExecutorService executorService;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        this.noteDao = database.noteDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public NoteRepository(NoteDao noteDao, ExecutorService executorService) {
        this.noteDao = noteDao;
        this.executorService = executorService;
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAllNotes();
    }

    public LiveData<Note> getNoteById(int id) {
        return noteDao.getNoteById(id);
    }

    public void insert(Note note) {
        executorService.execute(() -> noteDao.insert(note));
    }

    public void update(Note note) {
        executorService.execute(() -> noteDao.update(note));
    }

    public void delete(Note note) {
        executorService.execute(() -> noteDao.delete(note));
    }
}
