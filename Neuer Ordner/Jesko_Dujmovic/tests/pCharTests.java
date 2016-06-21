package uri.tests;

import org.junit.Test;
import uri.Uri;
import uri.UriParserFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * tests elements of type pchar
 */
public class pCharTests {
    @Test
    public void wrongHexDigit() {
        assertNull(UriParserFactory.create("some://%es@%11%11?%11").parse());
        assertNull(UriParserFactory.create("some://%11@%11%ex?%11").parse());
        assertNull(UriParserFactory.create("some://%11@%11%11?%kz").parse());
    }

    @Test
    public void shortPct() {
        assertNull(UriParserFactory.create("some://%e%12%e@%11%e6%01?%91%30%11").parse());
        assertNull(UriParserFactory.create("some://%e1%12%e1@%1%e6%0?%91%30%11").parse());
        assertNull(UriParserFactory.create("some://%e1%12%e1@%11%e6%01?%9%30%1").parse());
        assertNull(UriParserFactory.create("some://%e1%12%e1@%11%e6%01/%9%30%1").parse());
    }

    @Test
    public void correctPct() {
        assertNotNull(UriParserFactory.create("some://%A1%12%e1@%11%E6%01/%E1%a0%11?%E1%a0%11").parse());
    }
    @Test
    public void  dots() {
        Uri uri = UriParserFactory.create("some://....@.../...?...").parse();
        assertNotNull(uri);
        assertEquals(uri.getHost().toString(),"...");
        assertEquals(uri.getPath(),"/...");
        assertEquals(uri.getUserInfo(),"....");
        assertEquals(uri.getQuery(),"...");
    }
}
