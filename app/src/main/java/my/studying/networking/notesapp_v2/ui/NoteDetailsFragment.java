package my.studying.networking.notesapp_v2.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import my.studying.networking.notesapp_v2.R;
import my.studying.networking.notesapp_v2.data.Note;
import my.studying.networking.notesapp_v2.util.DateFormatter;

public class NoteDetailsFragment extends Fragment {

    private NoteViewModel noteViewModel;
    private Note currentNote;
    private int noteId;

    private TextView tvName;
    private TextView tvDate;
    private TextView tvText;
    private Button btnEdit;
    private Button btnDelete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tv_detail_name);
        tvDate = view.findViewById(R.id.tv_detail_date);
        tvText = view.findViewById(R.id.tv_detail_text);
        btnEdit = view.findViewById(R.id.btn_edit_note);
        btnDelete = view.findViewById(R.id.btn_delete_note);

        if (getArguments() != null) {
            NoteDetailsFragmentArgs args = NoteDetailsFragmentArgs.fromBundle(getArguments());
            noteId = args.getNoteId();
        }

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getNoteById(noteId).observe(getViewLifecycleOwner(), note -> {
            if (note != null) {
                currentNote = note;
                tvName.setText(note.getName());
                tvDate.setText(DateFormatter.format(note.getDate()));
                tvText.setText(note.getText());
            }
        });

        btnEdit.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            NoteDetailsFragmentDirections.ActionNoteDetailsFragmentToAddEditNoteFragment action =
                    NoteDetailsFragmentDirections.actionNoteDetailsFragmentToAddEditNoteFragment();
            action.setNoteId(noteId);
            navController.navigate(action);
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        if (currentNote != null) {
                            noteViewModel.delete(currentNote);
                            Navigation.findNavController(v).popBackStack();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}
