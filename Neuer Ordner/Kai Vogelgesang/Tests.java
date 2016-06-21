package uri.tests;

import org.junit.Test;

import uri.IPv4Address;
import uri.Uri;
import uri.UriParserFactory;

import static org.junit.Assert.*;

public class Tests {

    @Test
    public void testNonNull() {
        assertNotNull(UriParserFactory.create("scheme://").parse());
    }

    @Test
    public void testNegativeSimple() {
        assertNull(UriParserFactory.create("").parse());
    }

    @Test
    public void testInvalidScheme() {
        assertNull(UriParserFactory.create("1337h4x0r://").parse());
        assertNull(UriParserFactory.create("μαιλτο://ωωω.γοογλε.δε").parse());
    }

    @Test
    public void testEmptyQuery() {
        Uri u = UriParserFactory.create("http://addr.tld/awesomepage?").parse();
        assertNotNull(u);
        assertNotNull(u.getQuery());
        assertEquals("", u.getQuery());
        assertNotNull(u.getPath());
        assertEquals(u.getPath(), "/awesomepage");
    }

    @Test
    public void testIP() {
        Uri u = UriParserFactory.create("sftp://256.666.069.21/").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertEquals(u.getHost().toString(), "256.666.069.21");

        u = UriParserFactory.create("sftp://192.168.178.42/").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertTrue(u.getHost() instanceof IPv4Address);
        byte[] oct = ((IPv4Address) u.getHost()).getOctets();
        assertTrue((oct[0] & 0xFF) == 192);
        assertTrue((oct[1] & 0xFF) == 168);
        assertTrue((oct[2] & 0xFF) == 178);
        assertTrue((oct[3] & 0xFF) == 42);
    }

    @Test
    public void testRidiculousButCorrectBySpecification() {
        assertNotNull(UriParserFactory.create("cthulhu://::::::::::::::::@////////////////////////////?&&&&&&&&&&&&&&&&====").parse());
    }

    @Test
    public void testCorrectReading() {
        Uri u = UriParserFactory.create("http://www.instantcena.com").parse();
        assertNotNull(u);
        assertNotNull(u.getScheme());
        assertNotNull(u.getHost());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertNotNull(u.getHost().toString());
        assertEquals("http", u.getScheme());
        assertEquals("www.instantcena.com", u.getHost().toString());
        assertNull(u.getUserInfo());
        assertNull(u.getQuery());

        u = UriParserFactory.create("scheme://and/his/name/is?johncena").parse();
        assertNotNull(u);
        assertNotNull(u.getQuery());
        assertEquals("johncena", u.getQuery());

        u = UriParserFactory.create("a://%42@%de%ad%c0%de").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertNull(u.getQuery());
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "%42");
        assertEquals(u.getHost().toString(), "%de%ad%c0%de");
    }

    @Test
    public void testSmallInvalidThings() {
        assertNull(UriParserFactory.create("http://john@cena@www.google.de/").parse());
        assertNull(UriParserFactory.create("http://johncena@www.google.de?q=doyouseeme?anotherquestionmark").parse());
    }

    @Test
    public void testSmallValidThings() {
        assertNotNull(UriParserFactory.create("scheme://@").parse());
        assertNotNull(UriParserFactory.create("scheme://?").parse());
    }

    @Test
    public void testEmptyHierarchical() {
        Uri u = UriParserFactory.create("scm://?q").parse();
        assertNotNull(u);
        assertNotNull(u.getQuery());
        assertEquals(u.getQuery(), "q");
    }

    @Test
    public void test_pchar(){
        assertNull(UriParserFactory.create("scm://%42%69%fg").parse());

        Uri u = UriParserFactory.create("scm://abcdefghijklmnopqrstuvwxyz").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertEquals(u.getHost().toString(), "abcdefghijklmnopqrstuvwxyz");

        u = UriParserFactory.create("scm://ABCDEFGHIJKLMNOPQRSTUVWXYZ").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertEquals(u.getHost().toString(), "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        u = UriParserFactory.create("scm://0123456789").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertEquals(u.getHost().toString(), "0123456789");

        u = UriParserFactory.create("scm://%01%23%45%67%89%0a%bc%de%ff").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertEquals(u.getHost().toString(), "%01%23%45%67%89%0a%bc%de%ff");

        u = UriParserFactory.create("scm://%01%23%45%67%89%0A%BC%DE%FF").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertEquals(u.getHost().toString(), "%01%23%45%67%89%0A%BC%DE%FF");
    }

    @Test
    public void testSomeSchemes(){
        Uri u = UriParserFactory.create("onlyalpha://").parse();
        assertNotNull(u);
        assertNotNull(u.getScheme());
        assertEquals(u.getScheme(), "onlyalpha");

        u = UriParserFactory.create("m0r3numb3r5://").parse();
        assertNotNull(u);
        assertNotNull(u.getScheme());
        assertEquals(u.getScheme(), "m0r3numb3r5");

        u = UriParserFactory.create("n1337://").parse();
        assertNotNull(u);
        assertNotNull(u.getScheme());
        assertEquals(u.getScheme(), "n1337");
    }

    @Test
    public void testSomeUserInfo() {
        Uri u = UriParserFactory.create("scm://user:pass@site.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "user:pass");

        u = UriParserFactory.create("scm://alpha:1337:%0f%69%13%37@site.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "alpha:1337:%0f%69%13%37");

        u = UriParserFactory.create("scm://::::::::::::@site.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "::::::::::::");

        u = UriParserFactory.create("scm://@site.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "");
        
		u = UriParserFactory.create("scm://0:0@site.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "0:0");

        u = UriParserFactory.create("scm://kek:@site.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "kek:");


        u = UriParserFactory.create("scm://%13%37:@site.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "%13%37:");

        u = UriParserFactory.create("scm://dots.are.okay.too@site.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getUserInfo());
        assertEquals(u.getUserInfo(), "dots.are.okay.too");
    }

    @Test
    public void testSomeQueries(){
        Uri u = UriParserFactory.create("scm://ayyLMAO?alphaonly=true").parse();
        assertNotNull(u);
        assertNotNull(u.getQuery());
        assertEquals(u.getQuery(), "alphaonly=true");

        u = UriParserFactory.create("scm://ayyLMAO?4mI1337=definitely.").parse();
        assertNotNull(u);
        assertNotNull(u.getQuery());
        assertEquals(u.getQuery(), "4mI1337=definitely.");

        u = UriParserFactory.create("scm://ayyLMAO?multiple&queries&are&no&problem&at&all").parse();
        assertNotNull(u);
        assertNotNull(u.getQuery());
        assertEquals(u.getQuery(), "multiple&queries&are&no&problem&at&all");

        u = UriParserFactory.create("scm://ayyLMAO?secretcode=%69%69%13%37%af%fe").parse();
        assertNotNull(u);
        assertNotNull(u.getQuery());
        assertEquals(u.getQuery(), "secretcode=%69%69%13%37%af%fe");

        assertNull(UriParserFactory.create("scheme://site.tld?q=λεροοοοοουθεεεεεεεεενκινσ").parse());
    }

    @Test
    public void testSomeIPStuff() {
        Uri u = UriParserFactory.create("scm://8.8.8.8").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertTrue(u.getHost() instanceof IPv4Address);
        byte[] oct = ((IPv4Address) u.getHost()).getOctets();
        assertTrue((oct[0] & 0xFF) == 8);
        assertTrue((oct[1] & 0xFF) == 8);
        assertTrue((oct[2] & 0xFF) == 8);
        assertTrue((oct[3] & 0xFF) == 8);

        u = UriParserFactory.create("scm://201.192.254.02").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertTrue(u.getHost() instanceof IPv4Address);
        oct = ((IPv4Address) u.getHost()).getOctets();
        assertTrue((oct[0] & 0xFF) == 201);
        assertTrue((oct[1] & 0xFF) == 192);
        assertTrue((oct[2] & 0xFF) == 254);
        assertTrue((oct[3] & 0xFF) == 2);

        u = UriParserFactory.create("scm://0.0.0.0").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertTrue(u.getHost() instanceof IPv4Address);
        oct = ((IPv4Address) u.getHost()).getOctets();
        assertTrue((oct[0] & 0xFF) == 0);
        assertTrue((oct[1] & 0xFF) == 0);
        assertTrue((oct[2] & 0xFF) == 0);
        assertTrue((oct[3] & 0xFF) == 0);

        u = UriParserFactory.create("scm://001.243.111.069").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertTrue(u.getHost() instanceof IPv4Address);
        oct = ((IPv4Address) u.getHost()).getOctets();
        assertTrue((oct[0] & 0xFF) == 1);
        assertTrue((oct[1] & 0xFF) == 243);
        assertTrue((oct[2] & 0xFF) == 111);
        assertTrue((oct[3] & 0xFF) == 69);

        u = UriParserFactory.create("scm://1.03.003.007").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertTrue(u.getHost() instanceof IPv4Address);
        oct = ((IPv4Address) u.getHost()).getOctets();
        assertTrue((oct[0] & 0xFF) == 1);
        assertTrue((oct[1] & 0xFF) == 3);
        assertTrue((oct[2] & 0xFF) == 3);
        assertTrue((oct[3] & 0xFF) == 7);

        u = UriParserFactory.create("scm://1.19.024.002").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertTrue(u.getHost() instanceof IPv4Address);
        assertEquals(u.getHost().toString(), "1.19.24.2");
    }

    @Test
    public void testEmptyHost() {
        Uri u = UriParserFactory.create("scheme://user@?query").parse();
        assertNotNull(u);
        assertNotNull(u.getHost());
        assertEquals(u.getHost().toString(), "");
    }

    @Test
    public void testSomePaths() {
        Uri u = UriParserFactory.create("scheme://addr.tld/stairway/to/heaven").parse();
        assertNotNull(u);
        assertNotNull(u.getPath());
        assertEquals(u.getPath(), "/stairway/to/heaven");

        u = UriParserFactory.create("scheme://addr.tld").parse();
        assertNotNull(u);
        assertNotNull(u.getPath());
        assertEquals(u.getPath(), "");

        u = UriParserFactory.create("scheme://addr.tld/.../").parse();
        assertNotNull(u);
        assertNotNull(u.getPath());
        assertEquals(u.getPath(), "/.../");

        u = UriParserFactory.create("scheme://addr.tld/%69%69%13%37").parse();
        assertNotNull(u);
        assertNotNull(u.getPath());
        assertEquals(u.getPath(), "/%69%69%13%37");

        u = UriParserFactory.create("scheme://addr.tld/666/1337/i%0am50aw%ees0me").parse();
        assertNotNull(u);
        assertNotNull(u.getPath());
        assertEquals(u.getPath(), "/666/1337/i%0am50aw%ees0me");
    }

}
