package my.studying.networking.notesapp_v2.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import my.studying.networking.notesapp_v2.R;
import my.studying.networking.notesapp_v2.data.Note;
import my.studying.networking.notesapp_v2.model.NoteViewModel;

public class AddEditNoteFragment extends Fragment {

    private static final String KEY_NOTE_LOADED = "key_note_loaded";

    private NoteViewModel noteViewModel;
    private int noteId = -1;
    private boolean isEditMode = false;
    private boolean isNoteLoaded = false;
    private Note currentNote;

    private TextInputLayout tilName;
    private TextInputEditText etName;
    private TextInputLayout tilText;
    private TextInputEditText etText;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tilName = view.findViewById(R.id.til_note_name);
        etName = view.findViewById(R.id.et_note_name);
        tilText = view.findViewById(R.id.til_note_text);
        etText = view.findViewById(R.id.et_note_text);
        btnSave = view.findViewById(R.id.btn_save_note);

        if (savedInstanceState != null) {
            isNoteLoaded = savedInstanceState.getBoolean(KEY_NOTE_LOADED, false);
        }

        if (getArguments() != null) {
            noteId = getArguments().getInt("noteId", -1);
        }

        isEditMode = (noteId != -1);

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(isEditMode ? "Edit Note" : "Add Note");
            }
        }

        if (isEditMode) {
            etName.setEnabled(false);
            tilName.setEnabled(false);
        } else {
            etName.setEnabled(true);
            tilName.setEnabled(true);
        }

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        if (isEditMode) {
            noteViewModel.getNoteById(noteId).observe(getViewLifecycleOwner(), note -> {
                if (note != null) {
                    currentNote = note;
                    if (!isNoteLoaded) {
                        etName.setText(note.getName());
                        etText.setText(note.getText());
                        isNoteLoaded = true;
                    }
                }
            });
        }

        btnSave.setOnClickListener(this::saveNote);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_NOTE_LOADED, isNoteLoaded);
    }

    private void saveNote(View view) {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String text = etText.getText() != null ? etText.getText().toString().trim() : "";

        boolean isValid = true;
        if (TextUtils.isEmpty(name)) {
            tilName.setError("Note title cannot be empty");
            isValid = false;
        } else {
            tilName.setError(null);
        }

        if (TextUtils.isEmpty(text)) {
            tilText.setError("Note text cannot be empty");
            isValid = false;
        } else {
            tilText.setError(null);
        }

        if (!isValid) {
            return;
        }

        NavController navController = Navigation.findNavController(view);

        if (isEditMode) {
            if (currentNote != null) {
                currentNote.setText(text);
                currentNote.setDate(System.currentTimeMillis());
                noteViewModel.update(currentNote);
            }
        } else {
            Note newNote = new Note(name, System.currentTimeMillis(), text);
            noteViewModel.insert(newNote);
        }

        navController.popBackStack(R.id.notesListFragment, false);
    }
}
