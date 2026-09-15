package my.studying.networking.notesapp_v2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import my.studying.networking.notesapp_v2.R;

public class NotesListFragment extends Fragment {

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;
    private RecyclerView rvNotes;
    private TextView tvEmptyState;
    private FloatingActionButton fabAddNote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvNotes = view.findViewById(R.id.rv_notes);
        tvEmptyState = view.findViewById(R.id.tv_empty_state);
        fabAddNote = view.findViewById(R.id.fab_add_note);

        noteAdapter = new NoteAdapter(note -> {
            NavController navController = Navigation.findNavController(view);
            NotesListFragmentDirections.ActionNotesListFragmentToNoteDetailsFragment action =
                    NotesListFragmentDirections.actionNotesListFragmentToNoteDetailsFragment(note.getId());
            navController.navigate(action);
        });

        rvNotes.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvNotes.setAdapter(noteAdapter);

        fabAddNote.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(NotesListFragmentDirections.actionNotesListFragmentToAddEditNoteFragment());
        });

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
            noteAdapter.submitList(notes);
            if (notes == null || notes.isEmpty()) {
                tvEmptyState.setVisibility(View.VISIBLE);
                rvNotes.setVisibility(View.GONE);
            } else {
                tvEmptyState.setVisibility(View.GONE);
                rvNotes.setVisibility(View.VISIBLE);
            }
        });
    }
}
