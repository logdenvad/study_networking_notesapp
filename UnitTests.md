
##. Sample Data & Emulator Testing

### Pre-populating Database for Testing
To populate initial test data on clean app launches (e.g., on Android Emulator), a Room database callback can be attached in `NoteDatabase.java`:

```java
private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        Executors.newSingleThreadExecutor().execute(() -> {
            NoteDao dao = INSTANCE.noteDao();
            long now = System.currentTimeMillis();
            dao.insert(new Note("Welcome Note", now, "This is your first note in NotesApp."));
            dao.insert(new Note("Project Specs", now - 3600000, "Room + ViewModel + LiveData + Navigation"));
            dao.insert(new Note("Grocery List", now - 86400000, "Apples, Milk, Bread, Coffee"));
        });
    }
};
```

## 9. Unit & Integration Tests

###Room DAO Integration Tests (NoteDaoTest)

*   Insert & Fetch All: Verifies that inserting a note saves it to SQLite and getAllNotes() emits the updated list ordered by date descending via LiveData.

*   Fetch by ID: Confirms that getNoteById() retrieves the exact note entity matching the specified primary key.

* Update & Delete Operations: Validates that updating note content modifies the target database record and deleting removes it completely from query results.

###ViewModel Unit Tests (NoteViewModelTest)

*   Repository Delegation: Uses mocks to verify that calling insert(), update(), or delete() on NoteViewModel directly triggers the corresponding method on NoteRepository.

*   Data Flow Preservation: Confirms that data emitted by the repository is correctly exposed through ViewModel LiveData streams to UI observers.

###Repository Async Execution Tests

*   Background Thread Offloading: Ensures that NoteRepository write operations execute asynchronously off the main thread using an executor service to prevent main thread exceptions.

###RecyclerView & DiffUtil Tests

*   Item Identity Check: Validates that areItemsTheSame() correctly compares unique note primary key IDs.

*   Content Equality Check: Validates that areContentsTheSame() checks equality across note name, date, and body text fields to trigger minimal list redraws.

###UI & Navigation Logic Tests

*   Title Immutability Enforcer: Tests that AddEditNoteFragment leaves the title input field enabled when noteId == -1 (Add Mode) and explicitly disables it when noteId > 0 (Edit Mode).

*   Deletion & Navigation Flow: Verifies that deleting a note from NoteDetailsFragment issues the deletion request to the ViewModel and pops the navigation back stack to return to the main list.

