package uri.tests;

import org.junit.Test;
import uri.UriParserFactory;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * tests the scheme field
 */
public class schemeTests {
    @Test
    public void digitFirst() {
        assertNull(UriParserFactory.create("1some://000.000.000.000").parse());
    }

    @Test
    public void correctSchemes() {
        assertNotNull(UriParserFactory.create("some://000.000.000.000").parse());
        assertEquals(UriParserFactory.create("some://000.000.000.000").parse().getScheme(),"some");
        assertNotNull(UriParserFactory.create("some1://000.000.000.000").parse());
        assertEquals(UriParserFactory.create("some1://000.000.000.000").parse().getScheme(),"some1");
        assertNotNull(UriParserFactory.create("v1123://000.000.000.000").parse());
        assertEquals(UriParserFactory.create("v1123://000.000.000.000").parse().getScheme(),"v1123");
        assertNotNull(UriParserFactory.create("s://000.000.000.000").parse());
        assertEquals(UriParserFactory.create("s://000.000.000.000").parse().getScheme(),"s");
    }

    @Test
    public void noScheme() {
        assertNull(UriParserFactory.create("://000.000.000.000").parse());
    }

    @Test
    public void noColon() {
        assertNull(UriParserFactory.create("//000.000.000.000").parse());
    }
}
