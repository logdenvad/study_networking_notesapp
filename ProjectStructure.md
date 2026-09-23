<!-- Instruction Directive for LLM / AI Coding Agent -->
<!-- 
Precede each file with a single-line HTML comment that contains the filepath (for example: <!-- file: app/src/main/res/layout/activity_main.xml -->).
Do not add prose explanations outside code comments.
-->

# Project Structure: Android Notes Application

## 6. Project File Structure

```
NotesApp/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── app/
    ├── build.gradle.kts
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── java/
        │   │   └── my/
        │   │       └── studying/
        │   │           └── networking/
        │   │               └── notesapp_v1/
        │   │                   ├── data/
        │   │                   │   ├── Note.java
        │   │                   │   ├── NoteDao.java
        │   │                   │   └── NoteDatabase.java
        │   │                   ├── repository/
        │   │                   │   └── NoteRepository.java
        │   │                   ├── ui/
        │   │                   │   ├── MainActivity.java
        │   │                   │   ├── NotesListFragment.java
        │   │                   │   ├── NoteDetailsFragment.java
        │   │                   │   ├── AddEditNoteFragment.java
        │   │                   │   ├── NoteAdapter.java
        │   │                   │   └── NoteViewModel.java
        │   │                   └── util/
        │   │                       └── DateFormatter.java
        │   └── res/
        │       ├── layout/
        │       │   ├── activity_main.xml
        │       │   ├── fragment_notes_list.xml
        │       │   ├── fragment_note_details.xml
        │       │   ├── fragment_add_edit_note.xml
        │       │   └── item_note.xml
        │       ├── navigation/
        │       │   └── nav_graph.xml
        │       └── values/
        │           ├── strings.xml
        │           ├── colors.xml
        │           └── themes.xml
        ├── test/
        │   └── java/
        │       └── my/
        │           └── studying/
        │               └── networking/
        │                   └── notesapp_v1/
        │                       └── ui/
        │                           └── NoteViewModelTest.java
        └── androidTest/
            └── java/
                └── my/
                    └── studying/
                        └── networking/
                            └── notesapp_v1/
                                └── data/
                                    └── NoteDaoTest.java
```

---

## 7. Explanation of Project Structure

The project follows the **MVVM (Model-View-ViewModel)** architectural pattern adhering to official Android Jetpack recommendations and Single Activity Architecture principles.

*   **`data/` (Data Layer):**
    *   `Note.java`: Room `@Entity` representing the SQLite table structure with primary key `id`, `name`, `date` (timestamp/formatted string), and `text`.
    *   `NoteDao.java`: Data Access Object interface containing Room SQL queries (`@Query`, `@Insert`, `@Update`, `@Delete`) for executing CRUD operations.
    *   `NoteDatabase.java`: Abstract `@Database` class extending `RoomDatabase` configured as a thread-safe Singleton to manage the local database connection.
*   **`repository/` (Data Access Abstraction):**
    *   `NoteRepository.java`: Single source of truth unifying local data access. Handles background thread execution via `ExecutorService` for async database inserts, updates, and deletes, while exposing `LiveData` streams directly from the DAO.
*   **`ui/` (Presentation Layer):**
    *   `MainActivity.java`: Host activity containing the `FragmentContainerView` for Jetpack Navigation Component.
    *   `NotesListFragment.java`: Displays the note collection in a `RecyclerView`. Observes `LiveData<List<Note>>` to reactively update UI upon data changes.
    *   `NoteDetailsFragment.java`: Displays complete details of a single note. Retrieves `noteId` via Safe Args and presents actions to edit or delete the note.
    *   `AddEditNoteFragment.java`: Handles note creation and text editing. Toggles title immutability based on whether the destination is invoked in Add Mode or Edit Mode.
    *   `NoteAdapter.java`: `RecyclerView.Adapter` utilizing `DiffUtil` for optimized list updates and item click handling.
    *   `NoteViewModel.java`: `AndroidViewModel` subclass bridging the UI and Repository. Maintains state across configuration changes (e.g., screen rotations).
*   **`util/` (Utilities):**
    *   `DateFormatter.java`: Helper class providing localized date formatting utilities for consistent timestamp presentation across list items and detail views.
*   **`res/navigation/` (Navigation Routing):**
    *   `nav_graph.xml`: Centralized navigation graph managing destinations (`NotesListFragment`, `NoteDetailsFragment`, `AddEditNoteFragment`), transition actions, and Safe Args definitions.

---

## 8. Explanation of Key Methods & Logic

### 1. Room DAO Query & Persistence Logic (`NoteDao`)
*   `getAllNotes()`: Annotated with `@Query("SELECT * FROM notes ORDER BY date DESC")`. Returns `LiveData<List<Note>>` which automatically registers observer streams with SQLite for real-time reactivity.
*   `getNoteById(int noteId)`: Annotated with `@Query("SELECT * FROM notes WHERE id = :noteId LIMIT 1")`. Returns `LiveData<Note>` for loading note details dynamically.
*   `insert(Note note)`, `update(Note note)`, `delete(Note note)`: Standard Room annotations handling object-relational mapping without raw SQL writing.

### 2. Repository Async Operations (`NoteRepository`)
*   Encapsulates DAO execution using a fixed thread pool (`Executors.newSingleThreadExecutor()`).
*   `insert(Note note)` / `update(Note note)` / `delete(Note note)` run off the UI thread to prevent Main Thread Queries exceptions:
    ```java
    public void insert(Note note) {
        executorService.execute(() -> noteDao.insert(note));
    }
    ```

### 3. RecyclerView Adapter with DiffUtil (`NoteAdapter`)
*   Implements `ListAdapter<Note, NoteViewHolder>` with a custom `DiffUtil.ItemCallback<Note>` to calculate minimal list diffs on a background thread during list updates.
*   `areItemsTheSame(Note oldItem, Note newItem)`: Compares `oldItem.getId() == newItem.getId()`.
*   `areContentsTheSame(Note oldItem, Note newItem)`: Checks equality of `name`, `date`, and `text` fields.

### 4. Note Title Immutability Logic (`AddEditNoteFragment`)
*   During `onViewCreated()`, Safe Args parameter `noteId` is evaluated:
    *   If `noteId == -1`: Operating in **Add Mode**. The title input field (`etNoteName`) remains enabled for user input (`etNoteName.setEnabled(true)`).
    *   If `noteId > 0`: Operating in **Edit Mode**. The title input field is explicitly disabled (`etNoteName.setEnabled(false)`) to enforce title immutability, while the note body text input (`etNoteText`) remains fully editable.

### 5. Navigation & Back Stack Management (`NoteDetailsFragment`)
*   When deleting a note, `viewModel.delete(currentNote)` is called, followed immediately by `Navigation.findNavController(view).popBackStack()`.
*   This automatically redirects the user to `NotesListFragment`, where the observed `LiveData<List<Note>>` triggers an automated UI refresh reflecting the deletion.

---
