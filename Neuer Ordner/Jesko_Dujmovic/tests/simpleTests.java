package uri.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import uri.UriParserFactory;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class simpleTests {

	@Test
	public void testNonNull() {
		assertNotNull(UriParserFactory.create("scheme://").parse());
	}

	@Test
	public void testNegativeSimple() {
		assertNull(UriParserFactory.create("").parse());
	}

    @Test
    public void doubleSlashTest() {
        assertNull(UriParserFactory.create("scheme:1.1.1.1").parse());
        assertNull(UriParserFactory.create("scheme:/1.1.1.1").parse());
    }

    @Test
    public void testNull() {
        assertNull(UriParserFactory.create(null));
    }

    @Test
    public void doubleAt() {
        assertNull(UriParserFactory.create("scheme://@@1.1.1.1").parse());
    }

    @Test
    public void doubleQuestionMark() {
        assertNull(UriParserFactory.create("scheme://1.1.1.1??").parse());
    }
}