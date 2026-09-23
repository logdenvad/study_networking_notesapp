//package my.studying.networking.notesapp_v2.ui;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//
//import java.util.Collections;
//
//import my.studying.networking.notesapp_v2.R;
//import my.studying.networking.notesapp_v2.data.Note;
//import my.studying.networking.notesapp_v2.util.DateFormatter;
//
//    @RunWith(RobolectricTestRunner.class)
//    public class NoteAdapterRobolectricTest {
//
//        private NoteAdapter adapter;
//        private FrameLayout parent;
//
//        @Before
//        public void setUp() {
//            // Создаем и запускаем минимальную Activity с вашей темой
//            AppCompatActivity activity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
//            parent = new FrameLayout(activity);
//            adapter = new NoteAdapter();
//        }
//
//        @Test
//        public void onCreateViewHolder_inflatesLayoutAndFindsViews() {
//            NoteAdapter.NoteViewHolder viewHolder = adapter.onCreateViewHolder(parent, 0);
//
//            assertNotNull("ViewHolder должен успешно создаваться", viewHolder);
//            assertNotNull("tvName должен присутствовать в макете", viewHolder.itemView.findViewById(R.id.tv_note_name));
//            assertNotNull("tvDate должен присутствовать в макете", viewHolder.itemView.findViewById(R.id.tv_note_date));
//        }
//
//        @Test
//        public void onBindViewHolder_setsCorrectTextAndFormattedDate() {
//            NoteAdapter.NoteViewHolder viewHolder = adapter.onCreateViewHolder(parent, 0);
//
//            long timestamp = 1600000000000L;
//            Note note = new Note(1, "Покупки", timestamp, "Купить молоко");
//
//            adapter.submitList(Collections.singletonList(note));
//            adapter.onBindViewHolder(viewHolder, 0);
//
//            TextView tvName = viewHolder.itemView.findViewById(R.id.tv_note_name);
//            TextView tvDate = viewHolder.itemView.findViewById(R.id.tv_note_date);
//
//            assertEquals("Покупки", tvName.getText().toString());
//            assertEquals(DateFormatter.format(timestamp), tvDate.getText().toString());
//        }
//
//        @Test
//        public void performClick_triggersOnItemClickListenerWithNote() {
//            NoteAdapter.OnItemClickListener listener = mock(NoteAdapter.OnItemClickListener.class);
//            adapter.setOnItemClickListener(listener);
//
//            NoteAdapter.NoteViewHolder viewHolder = adapter.onCreateViewHolder(parent, 0);
//
//            Note note = new Note(42, "Важная заметка", 1000L, "Детали");
//            adapter.submitList(Collections.singletonList(note));
//            adapter.onBindViewHolder(viewHolder, 0);
//
//            // Имитируем клик по элементу
//            viewHolder.itemView.performClick();
//
//            // Проверяем, что слушатель был вызван с нужным объектом Note
//            verify(listener).onItemClick(note);
//        }
//
//        @Test
//        public void performClick_withoutListener_doesNotCrash() {
//            NoteAdapter.NoteViewHolder viewHolder = adapter.onCreateViewHolder(parent, 0);
//
//            Note note = new Note(1, "Тест", 1000L, "Текст");
//            adapter.submitList(Collections.singletonList(note));
//            adapter.onBindViewHolder(viewHolder, 0);
//
//            // Клик без установленного listener не должен вызывать NullPointerException
//            viewHolder.itemView.performClick();
//        }
//}
