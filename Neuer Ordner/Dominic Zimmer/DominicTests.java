import org.junit.Test;

import uri.IPv4Address;
import uri.Uri;
import uri.UriParserFactory;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class DominicTests {

    //
    // Scheme
    //

    @Test
    public void testScheme1() {
        Uri uri = UriParserFactory.create("a://").parse();
        assertNotNull(uri);
        assertEquals("a", uri.getScheme());
        assertEquals("", uri.getPath());
        assertEquals(null, uri.getQuery());
        assertEquals(null, uri.getUserInfo());
        assertEquals("", uri.getHost().toString());
    }

    @Test
    public void testScheme2() {
        Uri uri = UriParserFactory.create("a7a://").parse();
        assertNotNull(uri);
        assertEquals("a7a", uri.getScheme());
        assertEquals("", uri.getPath());
        assertEquals(null, uri.getQuery());
        assertEquals(null, uri.getUserInfo());
        assertEquals("", uri.getHost().toString());
    }

    @Test
    public void testScheme3() {
        Uri uri = UriParserFactory.create("12://").parse();
        assertNull(uri);
    }

    @Test
    public void testScheme6() {
        Uri uri = UriParserFactory.create("scheme://::@000.256.145.000").parse();
        assertNotNull(uri);
        assertFalse(uri.getHost() instanceof IPv4Address);
        assertEquals(uri.getHost().toString(), "000.256.145.000");
    }

    @Test
    public void testScheme7() {
        Uri uri = UriParserFactory.create("scheme//::@000.256.145.000").parse();
        assertNull(uri);
    }

    //
    // Hierarch
    //

    @Test
    public void testHierarch1() {
        Uri uri = UriParserFactory.create("scheme://?query").parse();
        assertNotNull(uri);
        assertEquals("scheme", uri.getScheme());
        assertEquals("", uri.getPath());
        assertEquals("query", uri.getQuery());
        assertEquals(null, uri.getUserInfo());
        assertEquals("", uri.getHost().toString());
    }

    @Test
    public void testHierarch2() {
        Uri uri = UriParserFactory.create("scheme://@").parse();
        assertNotNull(uri);
        assertEquals("scheme", uri.getScheme());
        assertEquals("", uri.getPath());
        assertEquals(null, uri.getQuery());
        assertEquals("", uri.getUserInfo());
        assertEquals("", uri.getHost().toString());
    }

    @Test
    public void testHierarch3() {
        Uri uri = UriParserFactory.create("scheme:///p/e/n/i/s?query").parse();
        assertNotNull(uri);
        assertEquals("scheme", uri.getScheme());
        assertEquals("/p/e/n/i/s", uri.getPath());
        assertEquals("query", uri.getQuery());
        assertEquals(null, uri.getUserInfo());
        assertEquals("", uri.getHost().toString());
    }

    @Test
    public void testHierarch4() {
        Uri uri = UriParserFactory.create("scheme://a/a/a?").parse();
        assertNotNull(uri);
        assertEquals("scheme", uri.getScheme());
        assertEquals("/a/a", uri.getPath());
        assertEquals("", uri.getQuery());
        assertEquals(null, uri.getUserInfo());
        assertEquals("a", uri.getHost().toString());
    }

    //@Test
    //public void testHierarch5() {
    //    Uri uri = UriParserFactory.create("scheme://a/").parse();
    //    assertNull(uri);
    //}

    //@Test
    // public void testScheme7() {
    //     Uri uri = UriParserFactory.create("scheme://::@2.3.4.a").parse();
    ///     assertNull(uri);
    //  }

    //@Test
    //public void testScheme8() {
    //    Uri uri = UriParserFactory.create("scheme://%99%gG%5z%Z3@angafe/n?ai").parse();
    //    assertNull(uri);
    //}

    //
    // Host
    //

    @Test
    public void testHost1() {
        Uri uri = UriParserFactory.create(
                "scheme://a:b:%eF%44%e4%8FFL.FEAU::@a%99%2a%e0%FeEAIUVX/elg/%45%aF%4a%99aebSUI/aegl%ea%F9?%d9%34%F8&3&&==3%eauiaeHa").parse();
        assertNotNull(uri);
        assertEquals("scheme", uri.getScheme());
        assertEquals("a:b:%eF%44%e4%8FFL.FEAU::", uri.getUserInfo());
        assertEquals("/elg/%45%aF%4a%99aebSUI/aegl%ea%F9", uri.getPath());
        assertFalse(uri.getHost() instanceof IPv4Address);
        assertEquals("a%99%2a%e0%FeEAIUVX", uri.getHost().toString());
        assertEquals("%d9%34%F8&3&&==3%eauiaeHa", uri.getQuery());
    }

    @Test
    public void testHost2() {
        Uri uri = UriParserFactory.create("scheme://penis@000.255.07.069/a/b?fEg=%Feg&==").parse();
        assertNotNull(uri);
        assertEquals("scheme", uri.getScheme());
        assertEquals("penis", uri.getUserInfo());
        assertEquals("/a/b", uri.getPath());
        assertEquals("fEg=%Feg&==", uri.getQuery());
        assertTrue(uri.getHost() instanceof IPv4Address);
        IPv4Address address = (IPv4Address) uri.getHost();
        assertArrayEquals(Arrays.toString(address.getOctets()), address.getOctets(), new byte[]{0, (byte) 255, (byte) 7, 69});
        assertEquals("0.255.7.69", address.toString());
    }

    @Test
    public void testHost3() {
        Uri uri = UriParserFactory.create("some://1.01.001.001").parse();
        assertNotNull(uri);
        assertEquals(uri.getHost().toString(), "1.1.1.1");
        assertTrue(uri.getHost() instanceof IPv4Address);
        assertArrayEquals(((IPv4Address)uri.getHost()).getOctets(), new byte[]{1,1,1,1});
    }
    @Test
    public void testHost4() {
        Uri uri = UriParserFactory.create("some://10.68.99.111").parse();
        assertNotNull(uri);
        assertEquals(uri.getHost().toString(), "10.68.99.111");
        assertTrue(uri.getHost() instanceof IPv4Address);
        assertArrayEquals(((IPv4Address)uri.getHost()).getOctets(), new byte[]{10,68,99,111});
    }
    @Test
    public void testHost5() {
        Uri uri = UriParserFactory.create("some://256.256.256.256").parse();
        assertNotNull(uri);
        assertFalse(uri.getHost() instanceof IPv4Address);
    }

    //
    // Path
    //

    @Test
    public void testPath1() {
        Uri uri = UriParserFactory.create("scheme://penis@8.8.8.8/a//a?").parse();
        assertNotNull(uri);
        assertEquals("scheme", uri.getScheme());
        assertEquals("penis", uri.getUserInfo());
        assertEquals("/a//a", uri.getPath());
        assertEquals("", uri.getQuery());
        assertTrue(uri.getHost() instanceof IPv4Address);
    }

    @Test
    public void testPath2() {
        Uri uri = UriParserFactory.create("nenene://uiae@8.8.8.8//.././/.//././%69%420?").parse();
        assertNotNull(uri);
        assertEquals("nenene", uri.getScheme());
        assertEquals("uiae", uri.getUserInfo());
        assertEquals("//.././/.//././%69%420", uri.getPath());
        assertEquals("", uri.getQuery());
        assertTrue(uri.getHost() instanceof IPv4Address);
    }

    @Test
    public void testPath3() {
        Uri uri = UriParserFactory.create("scheme://?").parse();
        assertNotNull(uri);
        assertEquals("scheme", uri.getScheme());
        assertEquals(null, uri.getUserInfo());
        assertEquals("", uri.getPath());
        assertEquals("", uri.getQuery());
    }

    //
    // Userinfo
    //

    @Test
    public void testUserInfo1() {
        Uri uri = UriParserFactory.create("scheme://::").parse();
        assertNotNull(false);
    }

    @Test
    public void testUserInfo2() {
        Uri uri = UriParserFactory.create("scheme://::@@").parse();
        assertNotNull(false);
    }

    @Test
    public void testUserInfo3() {
        Uri uri = UriParserFactory.create("ööäü://").parse();
        assertNull(uri);
    }


    @Test
    public void testUserInfo4() {
        Uri uri = UriParserFactory.create("ööäü://").parse();
        assertNull(uri);
    }

    @Test
    public void testUserInfo5() {
        Uri uri = UriParserFactory.create("aaa://ö").parse();
        assertNull(uri);
    }

    @Test
    public void testUserInfo6() {
        Uri uri = UriParserFactory.create("aaa://ael/ö").parse();
        assertNull(uri);
    }

    @Test
    public void testUserInfo7() {
        Uri uri = UriParserFactory.create("aaa://l/l?ö").parse();
        assertNull(uri);
    }

    @Test
    public void testUserInfo8() {
        Uri uri = UriParserFactory.create("aaa://ä@e").parse();
        assertNull(uri);
    }

    @Test
    public void testUserInfo9() {
        Uri uri = UriParserFactory.create("aaa://%F0Af.:7@aea").parse();
        assertEquals(uri.getUserInfo(), "%F0Af.:7");
    }

    @Test
    public void testUserInfo10() {
        Uri uri = UriParserFactory.create("aaa://@l/l?ö").parse();
        assertNull(uri);
    }

    @Test
    public void testAdditional1() {
        Uri uri = UriParserFactory.create("s://@horst").parse();
        assertNotNull(uri);
        assertEquals(uri.getUserInfo(),"");
    }

    @Test
    public void testAdditional2() {
        Uri uri = UriParserFactory.create("s://::69:.::%FF::e::@horst").parse();
        assertNotNull(uri);
        assertEquals(uri.getUserInfo(),"::69:.::%FF::e::");
    }
    @Test
    public void testAdditional3() {
        Uri uri = UriParserFactory.create("q://x").parse();
        assertNotNull(uri);
        assertNull(uri.getUserInfo());
    }

    @Test
    public void testAdditional4() {
        Uri uri = UriParserFactory.create("q://engfa:gaefn/ne").parse();
        assertNull(uri);
    }
}
