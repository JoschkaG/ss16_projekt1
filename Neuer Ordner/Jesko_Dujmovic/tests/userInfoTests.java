package uri.tests;

import org.junit.Test;
import uri.Uri;
import uri.UriParserFactory;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * tests user info field
 */
public class userInfoTests {
    @Test
    public void emptyUser() {
        assertNotNull(UriParserFactory.create("s://@hostname").parse());
        assertEquals(UriParserFactory.create("s://@hostname").parse().getUserInfo(),"");
    }

    @Test
    public void strangeInfo() {
        assertNotNull(UriParserFactory.create("s://::1:.::%aE::e::@hostname").parse());
        assertEquals(UriParserFactory.create("s://::1:.::%aE::e::@hostname").parse().getUserInfo(),"::1:.::%aE::e::");
    }
    @Test
    public void noUserInfo() {
        Uri uri = UriParserFactory.create("s://s").parse();
        assertNotNull(uri);
        assertNull(uri.getUserInfo());
    }
}
