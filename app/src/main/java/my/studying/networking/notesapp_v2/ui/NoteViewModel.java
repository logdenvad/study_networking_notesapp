package my.studying.networking.notesapp_v2.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import my.studying.networking.notesapp_v2.data.Note;

public class NoteViewModel extends AndroidViewModel {

    public NoteViewModel(@NonNull Application application) {
        super(application);
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
