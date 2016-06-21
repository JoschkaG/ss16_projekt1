package uri.tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import uri.Uri;
import uri.UriParserFactory;

/**
 * tests paths
 */
public class pathTests {
    @Test
    public void strangePath() {
        Uri uri = UriParserFactory.create("s:////n////////////////%23o.w//////").parse();
        assertNotNull(uri);
        assertEquals(uri.getPath(),"//n////////////////%23o.w//////");
    }

    @Test
    public void easyPath() {
        Uri uri = UriParserFactory.create("s://n/").parse();
        assertNotNull(uri);
        assertEquals(uri.getPath(),"/");
    }
}
