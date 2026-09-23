package my.studying.networking.notesapp_v2.ui;

import org.junit.Test;

import my.studying.networking.notesapp_v2.data.Note;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NoteAdapterTest {

//    @Test
//    public void diffUtil_areItemsTheSame_comparesIds() {
//        Note note1 = new Note("Title 1", 1000L, "Text 1");
//        note1.setId(1);
//        Note note2 = new Note("Title 2", 2000L, "Text 2");
//        note2.setId(1);
//        Note note3 = new Note("Title 1", 1000L, "Text 1");
//        note3.setId(2);
//
//        assertTrue(NoteAdapter.DIFF_CALLBACK.areItemsTheSame(note1, note2));
//        assertFalse(NoteAdapter.DIFF_CALLBACK.areItemsTheSame(note1, note3));
//    }

    @Test
    public void areItemsTheSame_sameId_returnsTrue() {
        Note note1 = new Note(1, "Заметка A", 1000L, "Текст A");
        Note note2 = new Note(1, "Заметка B (изменена)", 2000L, "Текст B");

        boolean result = NoteAdapter.DIFF_CALLBACK.areItemsTheSame(note1, note2);

        assertTrue("Заметки с одинаковым ID должны считаться одним и тем же объектом", result);
    }

    @Test
    public void areItemsTheSame_differentId_returnsFalse() {
        Note note1 = new Note(1, "Заметка", 1000L, "Текст");
        Note note2 = new Note(2, "Заметка", 1000L, "Текст");

        boolean result = NoteAdapter.DIFF_CALLBACK.areItemsTheSame(note1, note2);

        assertFalse("Заметки с разными ID не должны считаться одинаковыми", result);
    }

    @Test
    public void areContentsTheSame_identicalFields_returnsTrue() {
        Note note1 = new Note(1, "Заметка", 1000L, "Текст");
        Note note2 = new Note(1, "Заметка", 1000L, "Текст");

        boolean result = NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, note2);

        assertTrue("Заметки с полностью совпадающими полями должны быть равны по содержимому", result);
    }

    @Test
    public void areContentsTheSame_differentName_returnsFalse() {
        Note note1 = new Note(1, "Имя 1", 1000L, "Текст");
        Note note2 = new Note(1, "Имя 2", 1000L, "Текст");

        boolean result = NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, note2);

        assertFalse("При разном name метод должен возвращать false", result);
    }

    @Test
    public void areContentsTheSame_differentDate_returnsFalse() {
        Note note1 = new Note(1, "Имя", 1000L, "Текст");
        Note note2 = new Note(1, "Имя", 2000L, "Текст");

        boolean result = NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, note2);

        assertFalse("При разной дате метод должен возвращать false", result);
    }

    @Test
    public void areContentsTheSame_differentText_returnsFalse() {
        Note note1 = new Note(1, "Имя", 1000L, "Текст 1");
        Note note2 = new Note(1, "Имя", 1000L, "Текст 2");

        boolean result = NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, note2);

        assertFalse("При разном текста заметки метод должен возвращать false", result);
    }
//
//    @Test
//    public void diffUtil_areContentsTheSame_comparesAllFields() {
//        Note note1 = new Note("Title", 1000L, "Text");
//        note1.setId(1);
//        Note note2 = new Note("Title", 1000L, "Text");
//        note2.setId(1);
//        Note noteDifferentText = new Note("Title", 1000L, "Different");
//        noteDifferentText.setId(1);
//        Note noteDifferentDate = new Note("Title", 2000L, "Text");
//        noteDifferentDate.setId(1);
//        Note noteDifferentName = new Note("Different", 1000L, "Text");
//        noteDifferentName.setId(1);
//
//        assertTrue(NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, note2));
//        assertFalse(NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, noteDifferentText));
//        assertFalse(NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, noteDifferentDate));
//        assertFalse(NoteAdapter.DIFF_CALLBACK.areContentsTheSame(note1, noteDifferentName));
//    }
}
