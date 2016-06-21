package uri.tests;

import org.junit.Test;
import uri.UriParserFactory;

import static org.junit.Assert.assertNull;

/**
 * tests for spaces
 */
public class spaceTests {
    @Test
    public void spaceChecker() {
        assertNull(UriParserFactory.create("ssw s://nraaea").parse());
        assertNull(UriParserFactory.create("ssws://nra aea").parse());
        assertNull(UriParserFactory.create("ssws://nra aea").parse());
        assertNull(UriParserFactory.create(" ssws://nraaea").parse());
        assertNull(UriParserFactory.create("ssws://nraaea ").parse());
    }
}
