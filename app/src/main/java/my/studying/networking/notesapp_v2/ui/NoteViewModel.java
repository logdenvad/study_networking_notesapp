package my.studying.networking.notesapp_v2.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import my.studying.networking.notesapp_v2.data.Note;
import my.studying.networking.notesapp_v2.repository.NoteRepository;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository repository;
    private final LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        this(application, new NoteRepository(application));
    }

    public NoteViewModel(@NonNull Application application, @NonNull NoteRepository repository) {
        super(application);
        this.repository = repository;
        this.allNotes = repository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<Note> getNoteById(int id) {
        return repository.getNoteById(id);
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void insertNote(Note note) {
        insert(note);
    }

    public void updateNote(Note note) {
        update(note);
    }

    public void deleteNote(Note note) {
        delete(note);
    }
}
