package uri.tests;

import org.junit.Test;
import uri.IPv4Address;
import uri.Uri;
import uri.UriParserFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * tests the IP field
 */
public class hostTests {
    @Test
    public void zeros() {
        Uri uri = UriParserFactory.create("some://000.000.000.000").parse();
        assertNotNull(uri);
        assertEquals(uri.getHost().toString(), "0.0.0.0");
        assertTrue(uri.getHost() instanceof IPv4Address);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[0]&0xff,0);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[1]&0xff,0);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[2]&0xff,0);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[3]&0xff,0);
    }

    @Test
    public void ones() {
        Uri uri = UriParserFactory.create("some://001.001.001.001").parse();
        assertNotNull(uri);
        assertEquals(uri.getHost().toString(), "1.1.1.1");
        assertTrue(uri.getHost() instanceof IPv4Address);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[0]&0xff,1);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[1]&0xff,1);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[2]&0xff,1);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[3]&0xff,1);
    }

    @Test
    public void tens() {
        Uri uri = UriParserFactory.create("some://010.010.010.010").parse();
        assertNotNull(uri);
        assertEquals(uri.getHost().toString(), "10.10.10.10");
        assertTrue(uri.getHost() instanceof IPv4Address);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[0]&0xff,10);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[1]&0xff,10);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[2]&0xff,10);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[3]&0xff,10);
    }

    @Test
    public void big() {
        Uri uri = UriParserFactory.create("some://110.110.110.110").parse();
        assertNotNull(uri);
        assertEquals(uri.getHost().toString(), "110.110.110.110");
        assertTrue(uri.getHost() instanceof IPv4Address);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[0]&0xff,110);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[1]&0xff,110);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[2]&0xff,110);
        assertEquals(((IPv4Address)uri.getHost()).getOctets()[3]&0xff,110);
    }

    @Test
    public void notIp() {
        assertFalse(UriParserFactory.create("some://256.256.256.256").parse().getHost() instanceof IPv4Address);
    }

    @Test
    public void allGood() {
        assertNotNull(UriParserFactory.create("s://1234567890xvlcwkhgfquiaeosnrtdypzbmj%01%23%45%67%89%ab%cd%ef%AB%CD%EF").parse());
    }
}
