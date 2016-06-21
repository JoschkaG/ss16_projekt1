package uri.tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import uri.Uri;
import uri.UriParserFactory;
/**
 * tests query
 */
public class queryTests {
    @Test
    public void emptyQuery() {
        Uri uri = UriParserFactory.create("s://s?").parse();
        assertNotNull(uri);
        assertEquals(uri.getQuery(),"");
    }

    @Test
    public void simpleQuery() {
        Uri uri = UriParserFactory.create("s://s?1=&").parse();
        assertNotNull(uri);
        assertEquals(uri.getQuery(),"1=&");
    }

    @Test
    public void noQuery() {
        Uri uri = UriParserFactory.create("s://s").parse();
        assertNotNull(uri);
        assertNull(uri.getQuery());
    }

    @Test
    public void noQMark() {
        assertNull(UriParserFactory.create("s://=").parse());
        assertNull(UriParserFactory.create("s://&").parse());
        assertNull(UriParserFactory.create("s://=5").parse());
    }
}