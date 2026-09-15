package my.studying.networking.notesapp_v2.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static volatile NoteDatabase instance;

    public abstract NoteDao noteDao();

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                if (instance != null) {
                    NoteDao dao = instance.noteDao();
                    long now = System.currentTimeMillis();
                    dao.insert(new Note("Welcome Note", now, "This is your first note in NotesApp."));
                    dao.insert(new Note("Project Specs", now - 3600000, "Room + ViewModel + LiveData + Navigation"));
                    dao.insert(new Note("Grocery List", now - 86400000, "Apples, Milk, Bread, Coffee"));
                }
            });
        }
    };

    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NoteDatabase.class,
                            "note_database"
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
                }
            }
        }
        return instance;
    }
}
