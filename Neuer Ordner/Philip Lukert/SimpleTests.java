package uri.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import uri.Host;
import uri.IPv4Address;
import uri.Uri;
import uri.UriParserFactory;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class SimpleTests {


	@Test
	public void testNull() {
		assertNull(UriParserFactory.create(null));
	}
	
	@Test
	public void testNegativeSimple() {
		assertNull(UriParserFactory.create("").parse());
	}

	@Test
	public void schemeAOE() {
		assertNull(UriParserFactory.create("aö://").parse());
	}
	
	@Test
	public void schemeOE() {
		assertNull(UriParserFactory.create("Ö://").parse());
	}
	
	@Test
	public void schemePipe() {
		assertNull(UriParserFactory.create("a|://").parse());
	}
	
	@Test
	public void schemeStartNumber() {
		assertNull(UriParserFactory.create("42e://").parse());
	}
	
	@Test
	public void schemePercent() {
		assertNull(UriParserFactory.create("a%54://").parse());
	}

	@Test
	public void tooMuchSlashes() {
		assertNull(UriParserFactory.create("scheme:///@").parse());
	}
	
	@Test
	public void noSlashes() {
		assertNull(UriParserFactory.create("scheme:").parse());
	}

	@Test
	public void tooLessSlashes() {
		assertNull(UriParserFactory.create("scheme:/@").parse());
	}

	@Test
	public void noDoublepoint() {
		assertNull(UriParserFactory.create("scheme//@").parse());
	}

	@Test
	public void doublepointWrongPos() {
		assertNull(UriParserFactory.create("scheme/://").parse());
	}

	@Test
	public void userInfoPipe() {
		assertNull(UriParserFactory.create("scheme://|").parse());
	}
	
	@Test
	public void userInfoOE() {
		assertNull(UriParserFactory.create("scheme://ö@").parse());
	}

	@Test
	public void userInfoSlash() {
		assertNull(UriParserFactory.create("scheme://a/@").parse());
	}
	
	@Test
	public void userInfoTooLessHex() {
		assertNull(UriParserFactory.create("scheme://%4@").parse());
	}
	
	@Test
	public void userInfoTooManyHex() {
		assertNull(UriParserFactory.create("scheme://%%33@").parse());
	}
	
	@Test
	public void hostOE() {
		assertNull(UriParserFactory.create("scheme://ö").parse());
	}
	
	@Test
	public void hostTooLessHex() {
		assertNull(UriParserFactory.create("scheme://%3").parse());
	}

	@Test
	public void hostTooManyHex() {
		assertNull(UriParserFactory.create("scheme://%%44").parse());
	}
	
	@Test
	public void hostWrongHex() {
		assertNull(UriParserFactory.create("scheme://%4h").parse());
	}
	
	@Test
	public void hostEquals() {
		assertNull(UriParserFactory.create("scheme://=").parse());
	}
	
	@Test
	public void hostDoublepoint() {
		assertNull(UriParserFactory.create("scheme://:").parse());
	}

	@Test
	public void hostPipe() {
		assertNull(UriParserFactory.create("scheme://|").parse());
	}
	
	@Test
	public void hostAnd() {
		assertNull(UriParserFactory.create("scheme://=3").parse());
	}
	
	@Test
	public void hostOEPlus() {
		assertNull(UriParserFactory.create("scheme://@ö/a").parse());
	}
	
	@Test
	public void hostTooLessHexPlus() {
		assertNull(UriParserFactory.create("scheme://@%3/").parse());
	}
	
	@Test
	public void hostTooManyHexPlus() {
		assertNull(UriParserFactory.create("scheme://@%%44/").parse());
	}
	
	@Test
	public void hostEqualsPlus() {
		assertNull(UriParserFactory.create("scheme://@=/").parse());
	}
	
	@Test
	public void hostDoublepointPlus() {
		assertNull(UriParserFactory.create("scheme://@:/").parse());
	}
	
	@Test
	public void hostSpace() {
		assertNull(UriParserFactory.create("scheme://@ /").parse());
	}
	
	@Test
	public void pathDoublepoint() {
		assertNull(UriParserFactory.create("scheme:///:").parse());
	}
	
	@Test
	public void pathPipe() {
		assertNull(UriParserFactory.create("scheme:///|").parse());
	}
	
	@Test
	public void pathEquals() {
		assertNull(UriParserFactory.create("scheme:///=").parse());
	}
	
	@Test
	public void pathTooLessPercent() {
		assertNull(UriParserFactory.create("scheme:///%3").parse());
	}
	
	@Test
	public void pathTooManyPercent() {
		assertNull(UriParserFactory.create("scheme:///%%3").parse());
	}
	
	@Test
	public void pathOE() {
		assertNull(UriParserFactory.create("scheme:///ö").parse());
	}
	
	@Test
	public void queryOE() {
		assertNull(UriParserFactory.create("scheme://?ö").parse());
	}
	
	@Test
	public void queryTooMuchPercent() {
		assertNull(UriParserFactory.create("scheme://?%%3").parse());
	}
	
	@Test
	public void queryTooLessPercent() {
		assertNull(UriParserFactory.create("scheme://?%3").parse());
	}
	
	@Test
	public void queryDoublepoint() {
		assertNull(UriParserFactory.create("scheme://?:").parse());
	}

	@Test
	public void queryPipe() {
		assertNull(UriParserFactory.create("scheme://?|").parse());
	}

	@Test
	public void querySlash() {
		assertNull(UriParserFactory.create("scheme://?/").parse());
	}
	
	@Test
	public void queryQuestion() {
		assertNull(UriParserFactory.create("scheme://??").parse());
	}

	@Test
	public void testNonNull() {
		assertNotNull(UriParserFactory.create("scheme://userinfo@host/path1/path2?query").parse());
	}
	

	
	@Test
	public void generalOld() {
		String scheme = "scheme0";
		String user = "%ff:..user3%3f%AF";
		String host = "..host3%3f%AF";
		String path = "/hallo/%3f%AF/3";
		String query = "..query3%3f%AF=&";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + user + "@" + host + path + "?" + query).parse());
		//System.out.println(scheme + "://" + user + "@" + host + path + "?" + query);
		//Uri uri = UriParserFactory.create("scheme0://:..user3%3f%AF@..host3%3f%AF/hallo/%3f%AF/3?..query3%3f%AF=&").parse();
		assertNotNull(uri);
		
		assertFalse(uri.getHost()instanceof IPv4Address);
		assertEquals(scheme, uri.getScheme());
		assertEquals(user, uri.getUserInfo());
		assertEquals(host, uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());
	}

	
	@Test
	public void general() {
		String scheme = "scheme0";
		String user = "%ff:..user3%3f%aF";
		String host = "%dF..host3%3f%aF";
		String path = "/hallo/%3f%AF/3";
		String query = "..query3%3f%AF=&";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + user + "@" + host + path + "?" + query).parse());
		//System.out.println(scheme + "://" + user + "@" + host + path + "?" + query);
		//Uri uri = UriParserFactory.create("scheme0://:..user3%3f%AF@..host3%3f%AF/hallo/%3f%AF/3?..query3%3f%AF=&").parse();
		assertNotNull(uri);
		
		assertFalse(uri.getHost()instanceof IPv4Address);
		assertEquals(scheme, uri.getScheme());
		assertEquals(user, uri.getUserInfo());
		assertEquals(host, uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());
	}

	@Test
	public void noHost() {
		String scheme = "scheme0";
		String user = "%ff:..user3%3f%aF";
		String host = "";
		String path = "/hallo/%3f%AF/3";
		String query = "..query3%3f%AF=&";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + user + "@" + host + path + "?" + query).parse());
		//System.out.println(scheme + "://" + user + "@" + host + path + "?" + query);
		//Uri uri = UriParserFactory.create("scheme0://:..user3%3f%AF@..host3%3f%AF/hallo/%3f%AF/3?..query3%3f%AF=&").parse();
		assertNotNull(uri);
		
		assertTrue(uri.getHost()instanceof Host);
		assertEquals(scheme, uri.getScheme());
		assertEquals(user, uri.getUserInfo());
		assertEquals(host, uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());
	}

	@Test
	public void noUser() {
		String scheme = "scheme0";
		String host = "%00..host3%3f%aF";
		String path = "/hallo/%3f%AF/3";
		String query = "..query3%3f%AF=&";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + host + path + "?" + query).parse());
		//System.out.println(scheme + "://" + user + "@" + host + path + "?" + query);
		//Uri uri = UriParserFactory.create("scheme0://:..user3%3f%AF@..host3%3f%AF/hallo/%3f%AF/3?..query3%3f%AF=&").parse();
		assertNotNull(uri);
		
		assertTrue(uri.getHost()instanceof Host);
		assertEquals(scheme, uri.getScheme());
		assertNull(uri.getUserInfo());
		assertEquals(host, uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());
	}
	
	@Test
	public void validIP() {
		
		String scheme = "scheme0";
		String user = "";
		String host = "04.00.255.0";
		String path = "/hallo/%3f%AF/3";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + user + "@" + host + path).parse());
		
		assertNotNull(uri);
		
		assertEquals(scheme, uri.getScheme());
		assertEquals(user, uri.getUserInfo());
		assertEquals("4.0.255.0", uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertNull(uri.getQuery());
		assertTrue(uri.getHost() instanceof IPv4Address);
		//System.out.println(uri.getHost() instanceof IPv4Address);
		IPv4Address ip = (IPv4Address) uri.getHost();
		assertEquals(4, ip.getOctets().length);
		assertEquals((byte)4, ip.getOctets()[0]);
		assertEquals((byte)0, ip.getOctets()[1]);
		//System.out.println(ip.getOctets()[2]);
		assertEquals((byte)255, ip.getOctets()[2]);
		assertEquals((byte)0, ip.getOctets()[3]);
	}

	@Test
	public void invalidIPtooLarge() {
		String scheme = "scheme0";
		String user = ":..user3%3f%AF";
		String host = "256.0.0.0";
		String path = "/hallo/%3f%AF/3";
		String query = "";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + user + "@" + host + path + "?" + query).parse());
		
		assertNotNull(uri);
		
		assertEquals(scheme, uri.getScheme());
		assertEquals(user, uri.getUserInfo());
		assertEquals(host, uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());

		assertFalse(uri.getHost() instanceof IPv4Address);
	}


	@Test
	public void invalidIPtooMany() {
		String scheme = "scheme0";
		String user = ":..user3%3f%AF";
		String host = "0025.0.0.0";
		String path = "";
		String query = "..query3%3f%AF=&";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + user + "@" + host + path + "?" + query).parse());
		
		assertNotNull(uri);
		
		assertEquals(scheme, uri.getScheme());
		assertEquals(user, uri.getUserInfo());
		assertEquals(host, uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());
		//int oct = ((IPv4Address)uri.getHost()).getOctets();
		assertFalse(uri.getHost() instanceof IPv4Address);
	}


	@Test
	public void invalidIPEmpty() {
		String scheme = "scheme0";
		String user = ":..user3%3f%AF";
		String host = "002..0.0";
		String path = "";
		String query = "..query3%3f%AF=&";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + user + "@" + host + path + "?" + query).parse());
		
		assertNotNull(uri);
		
		assertEquals(scheme, uri.getScheme());
		assertEquals(user, uri.getUserInfo());
		assertEquals(host, uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());

		assertFalse(uri.getHost() instanceof IPv4Address);
	}


	@Test
	public void invalidIPtooManyBlocks() {
		String scheme = "scheme0";
		String user = ":..user3%3f%AF";
		String host = ".253.0.0.0.0";
		String path = "";
		String query = "..query3%3f%AF=&";
		
		Uri uri = (UriParserFactory.create(scheme + "://" + user + "@" + host + path + "?" + query).parse());
		
		assertNotNull(uri);
		
		assertEquals(scheme, uri.getScheme());
		assertEquals(user, uri.getUserInfo());
		assertEquals(host, uri.getHost().toString());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());

		assertFalse(uri.getHost() instanceof IPv4Address);
	}

	
	
	
	
	
}