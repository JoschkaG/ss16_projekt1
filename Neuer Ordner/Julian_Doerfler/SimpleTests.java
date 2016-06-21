package uri.tests;

import org.junit.Test;
import uri.Host;
import uri.IPv4Address;
import uri.Uri;
import uri.UriParserFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class SimpleTests {

    @Test
    public void testNonNull() {
        assertNotNull(UriParserFactory.create("scheme://").parse());
    }

    @Test
    public void testNegativeSimple() {
        assertNull(UriParserFactory.create("").parse());
        assertNull(UriParserFactory.create(null));
    }

    @Test
    public void positive1() {
        String s = "a1sDF12";
        String u = "hugo";
        String h = "0.0.0.0";
        String p = "/%FF%00Uadfuaibfbeudefbgu12123tb1n45t3%12/a%aa";
        String q = "&&&=hi=ho";
        Uri uri = UriParserFactory.create(s + "://" + u + "@" + h + p + "?" + q).parse();
        assertTrue(uri.getHost() instanceof IPv4Address);
        assertEquals(s, uri.getScheme());
        assertEquals(u, uri.getUserInfo());
        assertEquals(h, uri.getHost().toString());
        assertEquals(p, uri.getPath());
        assertEquals(q, uri.getQuery());
        IPv4Address address = (IPv4Address) uri.getHost();
        byte[] octets = address.getOctets();
        assertEquals(0, octets[0]);
        assertEquals(0, octets[1]);
        assertEquals(0, octets[2]);
        assertEquals(0, octets[3]);
        assertEquals("0.0.0.0", address.toString());
    }
    @Test
    public void positive2() {
        Uri uri = UriParserFactory.create("a://").parse();
        assertFalse(uri.getHost() instanceof IPv4Address);
        assertEquals("a", uri.getScheme());
        assertNull(uri.getUserInfo());
        assertEquals("", uri.getHost().toString());
        assertEquals("", uri.getPath());
        assertNull(uri.getQuery());
    }

    @Test
    public void positive3() {
        Uri u;
        assertNotNull(UriParserFactory.create("a://").parse());
        assertNotNull(UriParserFactory.create("afdsg://").parse());
        assertNotNull(UriParserFactory.create("an321N://").parse());
        assertNotNull(u = UriParserFactory.create("http://horst").parse());
        assertNull(u.getUserInfo());
        assertNotNull(u = UriParserFactory.create("http://@horst").parse());
        assertEquals("", u.getUserInfo());
        assertNotNull(u = UriParserFactory.create("http://:@horst").parse());
        assertEquals(":", u.getUserInfo());
        assertNotNull(UriParserFactory.create("http://a:b@horst").parse());
        assertNotNull(UriParserFactory.create("http://a:b@horSt").parse());
        assertNotNull(u = UriParserFactory.create("http://0.0.0.0").parse());
        assertEquals("0.0.0.0", u.getHost().toString());
        assertTrue(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://00.0.0.0").parse());
        assertEquals("0.0.0.0", u.getHost().toString());
        assertTrue(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://000.0.0.0").parse());
        assertEquals("0.0.0.0", u.getHost().toString());
        assertTrue(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://0.06.0.0").parse());
        assertEquals("0.6.0.0", u.getHost().toString());
        assertTrue(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://0.006.0.0").parse());
        assertEquals("0.6.0.0", u.getHost().toString());
        assertTrue(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://0.0006.0.0").parse());
        assertEquals("0.0006.0.0", u.getHost().toString());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://0.a.0.0").parse());
        assertEquals("0.a.0.0", u.getHost().toString());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://0.256.0.0").parse());
        assertEquals("0.256.0.0", u.getHost().toString());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://0.260.0.0").parse());
        assertEquals("0.260.0.0", u.getHost().toString());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://0.300.0.0").parse());
        assertEquals("0.300.0.0", u.getHost().toString());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://0.255.0.0").parse());
        assertTrue(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://255.255.255.255").parse());
        assertTrue(u.getHost() instanceof IPv4Address);
        assertArrayEquals(new byte[]{-1,-1,-1,-1},((IPv4Address) u.getHost()).getOctets());
        assertNotNull(u = UriParserFactory.create("http://").parse());
        assertEquals("", u.getHost().toString());

        assertNotNull(u = UriParserFactory.create("http://TDA%aeia%54.de%20013a").parse());
        assertEquals("TDA%aeia%54.de%20013a", u.getHost().toString());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertNotNull(u = UriParserFactory.create("http://:u%00iae:A2I:a%0f.e%ffi:ia45e:@").parse());
        assertEquals(":u%00iae:A2I:a%0f.e%ffi:ia45e:",u.getUserInfo());
        assertEquals("",u.getHost().toString());
        assertNotNull(u = UriParserFactory.create("http://0.0.0").parse());
        assertFalse(u.getHost() instanceof IPv4Address);
        assertEquals("0.0.0",u.getHost().toString());

        assertNotNull(u = UriParserFactory.create("http://").parse());
        assertEquals("", u.getHost().toString());
        assertEquals("",u.getPath());
        assertNotNull(u = UriParserFactory.create("http:///").parse());
        assertEquals("", u.getHost().toString());
        assertEquals("/",u.getPath());
        assertNotNull(u = UriParserFactory.create("http:///////").parse());
        assertEquals("/////",u.getPath());
        assertNotNull(u = UriParserFactory.create("http://host/////").parse());
        assertEquals("host", u.getHost().toString());
        assertEquals("/////",u.getPath());
        assertNotNull(u = UriParserFactory.create("http:///p///P/").parse());
        assertEquals("/p///P/",u.getPath());
        assertNotNull(u = UriParserFactory.create("http:///p//./P/").parse());
        assertEquals("/p//./P/",u.getPath());
        assertNotNull(u = UriParserFactory.create("http:///p/01xiua0%af%02hoi/./P/").parse());
        assertEquals("/p/01xiua0%af%02hoi/./P/",u.getPath());
        assertNotNull(u = UriParserFactory.create("http://?").parse());
        assertEquals("",u.getPath());
        assertEquals("",u.getHost().toString());
        assertEquals("",u.getQuery());
        assertNotNull(u = UriParserFactory.create("http://?&=&=gi=ia=ia&ai&a=a").parse());
        assertEquals("&=&=gi=ia=ia&ai&a=a",u.getQuery());
        assertNotNull(u = UriParserFactory.create("http:///this/is/a/path?&=&=gi=ia=ia&ai&a=a%ff&a01dShhzea19x73%0e").parse());
        assertEquals("/this/is/a/path",u.getPath());
        assertEquals("&=&=gi=ia=ia&ai&a=a%ff&a01dShhzea19x73%0e",u.getQuery());
    }

    @Test
    public void negative1() {
        assertNull(UriParserFactory.create("://").parse());
        assertNull(UriParserFactory.create("1://").parse());
        assertNull(UriParserFactory.create("1iu://").parse());
        assertNull(UriParserFactory.create("_://").parse());
        assertNull(UriParserFactory.create("http;//").parse());
        assertNull(UriParserFactory.create("http//").parse());
        assertNull(UriParserFactory.create("http/hi").parse());
        assertNull(UriParserFactory.create("http:/hi").parse());
        assertNull(UriParserFactory.create("http://hi:").parse());
        assertNull(UriParserFactory.create("http:/@@hi").parse());
        assertNull(UriParserFactory.create("http:/,@hi").parse());
        assertNull(UriParserFactory.create("http:/!@hi").parse());
        assertNull(UriParserFactory.create("http:/?@hi").parse());
        assertNull(UriParserFactory.create("http:/%@hi").parse());
        assertNull(UriParserFactory.create("http:/%0@hi").parse());
        assertNull(UriParserFactory.create("http://%0g").parse());
        assertNull(UriParserFactory.create("http://%g0").parse());
        assertNull(UriParserFactory.create("http://%0").parse());
        assertNull(UriParserFactory.create("http://0,0,0,0").parse());
        assertNull(UriParserFactory.create("http://hi:0.0.0.0").parse());
        assertNull(UriParserFactory.create("http://??").parse());
        assertNull(UriParserFactory.create("http://?test=t?toast=t").parse());
        assertNull(UriParserFactory.create("http://?test=t&t2=3#").parse());
        assertNull(UriParserFactory.create("http://?test=t&t2=3#hi").parse());
    }

    @Test
    public void regname() {
        Uri u;
        List<String> validHosts = new ArrayList<>();
        for(int i = 0; i < 26; ++i) {
            validHosts.add("" + (char)('a' + i));
            validHosts.add("" + (char)('A' + i));
            if(i < 10)
                validHosts.add("" + (char)('0' + i));
        }
        for(int i = 0; i < 16; ++i)
            for(int j = 0; j < 16; ++j)
                for(int u1 = 0; u1 < 2; ++u1)
                    for(int u2 = 0; u2 < 2; ++u2) {
                        String a = Integer.toHexString(i);
                        String b = Integer.toHexString(j);
                        if(u1 == 1)
                            a = a.toUpperCase();
                        if(u2 == 1)
                            b = b.toUpperCase();
                        validHosts.add("%" + a + b);
                    }
        for (String host : validHosts) {
            assertNotNull(u = UriParserFactory.create("http://" + host).parse());
            assertFalse(u.getHost() instanceof IPv4Address);
            assertEquals(host, u.getHost().toString());
        }
        assertNull(UriParserFactory.create("http://@@hi").parse());
        assertNull(UriParserFactory.create("http://&hi").parse());
        assertNull(UriParserFactory.create("http://=hi").parse());
        assertNull(UriParserFactory.create("http://hä").parse());
        assertNull(UriParserFactory.create("http://hö").parse());
        assertNull(UriParserFactory.create("http://hü").parse());
        assertNull(UriParserFactory.create("http://hß").parse());
        assertNull(UriParserFactory.create("http://%").parse());
        assertNull(UriParserFactory.create("http://%@").parse());
        assertNull(UriParserFactory.create("http://%/").parse());
        assertNull(UriParserFactory.create("http://@a@hi").parse());
        assertNull(UriParserFactory.create("http://a&hi").parse());
        assertNull(UriParserFactory.create("http://a=hi").parse());
        assertNull(UriParserFactory.create("http://häa").parse());
        assertNull(UriParserFactory.create("http://höa").parse());
        assertNull(UriParserFactory.create("http://hüa").parse());
        assertNull(UriParserFactory.create("http://hßa").parse());
        assertNull(UriParserFactory.create("http://a%").parse());
        assertNull(UriParserFactory.create("http://a%@").parse());
        assertNull(UriParserFactory.create("http://a%/").parse());
    }
}