package my.studying.networking.notesapp_v2.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import my.studying.networking.notesapp_v2.R;
import my.studying.networking.notesapp_v2.data.Note;
import my.studying.networking.notesapp_v2.util.DateFormatter;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    private OnItemClickListener listener;

    public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return Objects.equals(oldItem.getName(), newItem.getName())
                    && oldItem.getDate() == newItem.getDate()
                    && Objects.equals(oldItem.getText(), newItem.getText());
        }
    };

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    public NoteAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = getItem(position);
        holder.bind(note, listener);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_note_name);
            tvDate = itemView.findViewById(R.id.tv_note_date);
        }

        public void bind(Note note, OnItemClickListener listener) {
            if (note != null) {
                if (tvName != null) {
                    tvName.setText(note.getName());
                }
                if (tvDate != null) {
                    tvDate.setText(DateFormatter.format(note.getDate()));
                }
                itemView.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onItemClick(note);
                    }
                });
            }
        }
    }
}
