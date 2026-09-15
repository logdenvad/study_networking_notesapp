package my.studying.networking.notesapp_v2.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import my.studying.networking.notesapp_v2.data.Note;
import my.studying.networking.notesapp_v2.data.NoteDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NoteRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FakeNoteDao fakeNoteDao;
    private NoteRepository repository;

    private static class FakeNoteDao implements NoteDao {
        boolean insertCalled = false;
        boolean updateCalled = false;
        boolean deleteCalled = false;
        Note insertedNote = null;
        Note updatedNote = null;
        Note deletedNote = null;

        MutableLiveData<List<Note>> allNotesLiveData = new MutableLiveData<>();
        MutableLiveData<Note> noteLiveData = new MutableLiveData<>();

        @Override
        public void insert(Note note) {
            insertCalled = true;
            insertedNote = note;
        }

        @Override
        public void update(Note note) {
            updateCalled = true;
            updatedNote = note;
        }

        @Override
        public void delete(Note note) {
            deleteCalled = true;
            deletedNote = note;
        }

        @Override
        public LiveData<List<Note>> getAllNotes() {
            return allNotesLiveData;
        }

        @Override
        public LiveData<Note> getNoteById(int id) {
            return noteLiveData;
        }
    }

    @Before
    public void setUp() {
        fakeNoteDao = new FakeNoteDao();
        ExecutorService directExecutor = Executors.newSingleThreadExecutor();
        repository = new NoteRepository(fakeNoteDao, directExecutor);
    }

    @Test
    public void getAllNotes_returnsDaoData() {
        List<Note> list = Collections.singletonList(new Note("Title", 100L, "Text"));
        fakeNoteDao.allNotesLiveData.setValue(list);
        assertEquals(list, repository.getAllNotes().getValue());
    }

    @Test
    public void getNoteById_returnsDaoData() {
        Note note = new Note("Title", 100L, "Text");
        fakeNoteDao.noteLiveData.setValue(note);
        assertEquals(note, repository.getNoteById(1).getValue());
    }

    @Test
    public void insert_executesDaoInsert() throws InterruptedException {
        Note note = new Note("New Note", 200L, "Body");
        repository.insert(note);
        Thread.sleep(100);
        assertTrue(fakeNoteDao.insertCalled);
        assertEquals(note, fakeNoteDao.insertedNote);
    }

    @Test
    public void update_executesDaoUpdate() throws InterruptedException {
        Note note = new Note("Updated Note", 300L, "Body");
        repository.update(note);
        Thread.sleep(100);
        assertTrue(fakeNoteDao.updateCalled);
        assertEquals(note, fakeNoteDao.updatedNote);
    }

    @Test
    public void delete_executesDaoDelete() throws InterruptedException {
        Note note = new Note("Delete Note", 400L, "Body");
        repository.delete(note);
        Thread.sleep(100);
        assertTrue(fakeNoteDao.deleteCalled);
        assertEquals(note, fakeNoteDao.deletedNote);
    }
}
