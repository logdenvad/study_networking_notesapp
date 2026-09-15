package my.studying.networking.notesapp_v2.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class DateFormatterTest {

    @Test
    public void format_validTimestamp_returnsFormattedString() {
        long timestamp = 1700000000000L;
        String formatted = DateFormatter.format(timestamp);
        assertNotNull(formatted);
        assertFalse(formatted.isEmpty());
    }

    @Test
    public void format_invalidTimestamp_returnsEmptyString() {
        assertEquals("", DateFormatter.format(0));
        assertEquals("", DateFormatter.format(-1));
    }
}
