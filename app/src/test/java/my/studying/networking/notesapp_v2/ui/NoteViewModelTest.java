package my.studying.networking.notesapp_v2.ui;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import my.studying.networking.notesapp_v2.data.Note;
import my.studying.networking.notesapp_v2.repository.NoteRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class NoteViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FakeNoteRepository fakeRepository;
    private NoteViewModel viewModel;

    private static class FakeNoteRepository extends NoteRepository {
        boolean insertCalled = false;
        boolean updateCalled = false;
        boolean deleteCalled = false;
        Note lastInsertedNote = null;
        Note lastUpdatedNote = null;
        Note lastDeletedNote = null;

        private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
        private final MutableLiveData<Note> noteLiveData = new MutableLiveData<>();

        public FakeNoteRepository() {
            super(null, null);
        }

        @Override
        public LiveData<List<Note>> getAllNotes() {
            return notesLiveData;
        }

        @Override
        public LiveData<Note> getNoteById(int id) {
            return noteLiveData;
        }

        @Override
        public void insert(Note note) {
            insertCalled = true;
            lastInsertedNote = note;
        }

        @Override
        public void update(Note note) {
            updateCalled = true;
            lastUpdatedNote = note;
        }

        @Override
        public void delete(Note note) {
            deleteCalled = true;
            lastDeletedNote = note;
        }
    }

    @Before
    public void setUp() {
        fakeRepository = new FakeNoteRepository();
        Application mockApp = new Application();
        viewModel = new NoteViewModel(mockApp, fakeRepository);
    }

    @Test
    public void insert_delegatesToRepository() {
        Note note = new Note("Test Note", 1000L, "Test Body");
        viewModel.insert(note);
        assertTrue(fakeRepository.insertCalled);
        assertEquals(note, fakeRepository.lastInsertedNote);
    }

    @Test
    public void update_delegatesToRepository() {
        Note note = new Note("Test Note", 1000L, "Test Body");
        viewModel.update(note);
        assertTrue(fakeRepository.updateCalled);
        assertEquals(note, fakeRepository.lastUpdatedNote);
    }

    @Test
    public void delete_delegatesToRepository() {
        Note note = new Note("Test Note", 1000L, "Test Body");
        viewModel.delete(note);
        assertTrue(fakeRepository.deleteCalled);
        assertEquals(note, fakeRepository.lastDeletedNote);
    }

    @Test
    public void getAllNotes_exposesRepositoryData() {
        List<Note> sampleNotes = Collections.singletonList(new Note("Test", 1000L, "Content"));
        fakeRepository.notesLiveData.setValue(sampleNotes);

        LiveData<List<Note>> result = viewModel.getAllNotes();
        assertNotNull(result);
        assertEquals(sampleNotes, result.getValue());
    }

    @Test
    public void getNoteById_exposesRepositoryData() {
        Note sampleNote = new Note("Test", 1000L, "Content");
        sampleNote.setId(42);
        fakeRepository.noteLiveData.setValue(sampleNote);

        LiveData<Note> result = viewModel.getNoteById(42);
        assertNotNull(result);
        assertEquals(sampleNote, result.getValue());
    }
}
