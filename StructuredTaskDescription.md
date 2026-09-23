# Structured Task Description: Android Notes Application

## II. App Definition & Core Features

- **App Name:** NotesApp
- **Main Purpose:** A simple, reliable note-taking Android application designed for local note storage and as a practice project demonstrating core Android Jetpack components (Room, Navigation Component, ViewModel, and LiveData) in Java.
- **Brief Overview:** The application follows a Single Activity Architecture hosting Fragment destinations. Notes are stored in a local Room database with fields for ID, Name (title), Date (timestamp of creation or last update), and Note Text. Users can browse their saved notes in a list view, inspect note details, add new notes, edit existing note content (with title immutability enforced), and delete notes with automatic UI updates via LiveData observation.

- **Core Features:**
  1. **Note Persistence & Storage:** SQLite local storage powered by Room ORM with entity data mapping.
  2. **Notes List Dashboard:** RecyclerView displaying saved notes showing Name (title) and Date, automatically updated in real-time via `LiveData<List<Note>>`.
  3. **Note Creation:** Input screen to add a new note with a title, body text, and automatically generated creation timestamp.
  4. **Note Details View:** Dedicated screen presenting complete note information (Name, Date, and full Note Text) with action triggers for editing and deletion.
  5. **Note Content Editing:** Feature to modify note text while enforcing read-only constraints on the Note Name/Title. Automatically updates the database timestamp.
  6. **Note Deletion:** Removal of notes from the Room database with immediate back-stack navigation and automated main list dynamic refresh.

---

## III. Layout Draft, Behavior, and Navigation

The app implements **Single Activity Architecture** (`MainActivity`) with a `NavHostFragment` managing three core Fragment destinations.

### 1. Screen 1: "Notes List" (`NotesListFragment`)
- **Purpose:** Serves as the home screen displaying all stored notes and providing entry points for adding or viewing notes.
- **Layout & Components:**
  - `Toolbar` / `ActionBar` titled "My Notes".
  - `RecyclerView` with `CardView` items displaying:
    - `TextView` (`tv_note_name`): Displays Note Name.
    - `TextView` (`tv_note_date`): Displays creation/last update timestamp (formatted date string).
  - `FloatingActionButton` (`fab_add_note`): Located at bottom-right for creating a new note.
  - `TextView` / `View` (`tv_empty_state`): Displayed when no notes exist in the database.
- **User Actions:**
  - **Tap on Note Item:** Navigates to Screen 2 (`NoteDetailsFragment`), passing `noteId` via Safe Args.
  - **Tap FAB (`fab_add_note`):** Navigates to Screen 3 (`AddEditNoteFragment`) in "Add Mode".
- **Behavior & Transitions:**
  - Observes `LiveData<List<Note>>` from `NoteViewModel`.
  - Automatically updates list when database records are added, modified, or deleted.

### 2. Screen 2: "Note Details" (`NoteDetailsFragment`)
- **Purpose:** Displays full details of a specific note and provides options to edit or delete it.
- **Layout & Components:**
  - `Toolbar` / `ActionBar` with Back arrow navigation button.
  - `TextView` (`tv_detail_name`): Note Name (large/bold font).
  - `TextView` (`tv_detail_date`): Last update date/time string.
  - `TextView` / `ScrollView` (`tv_detail_text`): Full note body text inside a scrollable view.
  - `Button` (`btn_edit_note`): "Edit Note" button.
  - `Button` (`btn_delete_note`): "Delete Note" button (styled with red/destructive theme).
- **User Actions:**
  - **Tap "Edit Note":** Navigates to Screen 3 (`AddEditNoteFragment`) in "Edit Mode", passing `noteId` via Safe Args.
  - **Tap "Delete Note":** Displays confirmation dialog. Upon confirmation, invokes `viewModel.deleteNote(note)` and pops back stack to `NotesListFragment`.
  - **Tap Top Back Arrow:** Navigates back to `NotesListFragment`.
- **Behavior & Transitions:**
  - Loads Note entity by ID via `LiveData<Note>` from `NoteViewModel`.
  - Seamless back-stack pop to `NotesListFragment` after deletion.

### 3. Screen 3: "Add / Edit Note" (`AddEditNoteFragment`)
- **Purpose:** Screen for creating a new note or updating an existing note's body text.
- **Layout & Components:**
  - `Toolbar` / `ActionBar` titled "Add Note" or "Edit Note" depending on mode.
  - `TextInputLayout` + `TextInputEditText` (`et_note_name`): Note Name/Title.
    - *Behavior in Add Mode:* Enabled for text entry.
    - *Behavior in Edit Mode:* Disabled (`android:enabled="false"` / read-only) to enforce name immutability.
  - `TextInputLayout` + `TextInputEditText` (`et_note_text`): Multi-line input for Note Body Text.
  - `Button` (`btn_save_note`): "Save" action button.
- **User Actions:**
  - **Tap "Save":**
    - *Add Mode:* Validates input, creates new `Note` entity with current timestamp, inserts via `viewModel.insertNote(note)`, navigates back to `NotesListFragment`.
    - *Edit Mode:* Validates input, updates existing `Note` entity's text and date, updates DB via `viewModel.updateNote(note)`, navigates back to `NotesListFragment`.
  - **Tap Back / Cancel:** Discards changes and pops destination.
- **Behavior & Transitions:**
  - Checks incoming Safe Args for `noteId`. If `noteId == -1` (or default), operates in **Add Mode**; otherwise, operates in **Edit Mode**.

---

## IV. Non-Functional Requirements

- **Performance:**
  - Database queries and write operations MUST run on a background thread using `ExecutorService` or Room asynchronous execution patterns. Main thread database access (`allowMainThreadQueries`) is strictly prohibited.
  - Smooth 60 FPS scrolling in `RecyclerView` using `DiffUtil` / `ListAdapter`.
- **Offline Support:**
  - 100% offline capability. All data persisted locally via SQLite/Room with zero external network dependency.
- **Configuration Changes:**
  - Screen rotation and configuration changes handled seamlessly via Jetpack `ViewModel` architecture to retain UI state without re-querying or losing uncommitted form state.
  - Form field contents preserved during orientation changes.
- **Orientation Support:**
  - Full responsive layout support for both Portrait and Landscape orientations utilizing XML constraints and `ScrollView` elements to avoid content truncation.
- **Error Handling & Validation:**
  - Input validation: Prevent saving empty note names or empty body text with clear `TextInputLayout` error messages.
  - Null safety handling when parsing Safe Args and observing LiveData outputs.
  - Graceful empty state display in `NotesListFragment` when no database records exist.

---

## V. Grading Criteria Compliance Checklist

1. **Room Database Setup:**
   - [x] `@Entity` class `Note` created with fields: `id` (PrimaryKey, autoGenerate), `name` (String), `date` (long/String timestamp), `text` (String).
   - [x] `@Dao` interface created with methods for `insert`, `update`, `delete`, `getAllNotes()` (returning `LiveData<List<Note>>`), and `getNoteById(int id)` (returning `LiveData<Note>`).
   - [x] `@Database` class defined extending `RoomDatabase` with singleton pattern implementation.
2. **Architecture & State Management (ViewModel & LiveData):**
   - [x] Repository pattern implemented to abstract Room DAO operations from ViewModel.
   - [x] `NoteViewModel` extends `AndroidViewModel` to manage UI data and execute async DAO calls.
   - [x] Note list screen observes `LiveData<List<Note>>` to reactively render list updates.
3. **Jetpack Navigation Component:**
   - [x] Navigation graph (`nav_graph.xml`) defines all three destinations (`NotesListFragment`, `NoteDetailsFragment`, `AddEditNoteFragment`).
   - [x] Navigation safe arguments (`Safe Args`) utilized for type-safe argument passing between fragments.
4. **Note Details Screen & Display:**
   - [x] Details screen fetches and displays full note data (Name, Date, Text) based on passed `noteId`.
5. **Editing Functionality & Immutability Rule:**
   - [x] Editing feature implemented allowing body text modifications.
   - [x] Strict constraint enforced: Note Name field is read-only/disabled during edit mode.
6. **Deletion Functionality:**
   - [x] Note deletion logic removes selected entity from database via ViewModel.
   - [x] Redirection/pop-back stack automatically returns user to main list.
7. **Database Persistence & Data Synchronization:**
   - [x] Room database state correctly updated following insert, edit, and delete operations.
   - [x] Main notes list automatically refreshes upon database updates via LiveData reactive streams.
