package my.studying.networking.notesapp_v2.ui;

import org.junit.Test;

import my.studying.networking.notesapp_v2.data.Note;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NoteAdapterTest {

    @Test
    public void diffUtil_areItemsTheSame_comparesIds() {
        Note note1 = new Note("Title 1", 1000L, "Text 1");
        note1.setId(1);
        Note note2 = new Note("Title 2", 2000L, "Text 2");
        note2.setId(1);
        Note note3 = new Note("Title 1", 1000L, "Text 1");
        note3.setId(2);

        assertTrue(NoteAdapter.DIFF_CALLBACK.areItemsTheSame(note1, note2));
        assertFalse(NoteAdapter.DIFF_CALLBACK.areItemsTheSame(note1, note3));
    }

    @Test
    public void diffUtil_areContentsTheSame_comparesAllFields() {
        Note note1 = new Note("Title", 1000L, "Text");
        note1.setId(1);
        Note note2 = new Note("Title", 1000L, "Text");
        note2.setId(1);
        Note noteDifferentText = new Note("Title", 1000L, "Different");
        noteDifferentText.setId(1);
        Note noteDifferentDate = new Note("Title", 2000L, "Text");
        noteDifferentDate.setId(1);
        Note noteDifferentName = new Note("Different", 1000L, "Text");
        noteDifferentName.setId(1);

        assertTrue(NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, note2));
        assertFalse(NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, noteDifferentText));
        assertFalse(NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, noteDifferentDate));
        assertFalse(NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, noteDifferentName));
    }
}
