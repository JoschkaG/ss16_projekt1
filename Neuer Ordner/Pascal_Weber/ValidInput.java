package uri.tests;

import uri.Host;
import uri.IPv4Address;
import uri.Uri;
import uri.UriParserFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
public class ValidInput {
	
	/*@Test
	public void CorrectInput(){
		String scheme = "Sch3mde";
		String userinfo ="%4F%9f:";
		String host = "%1a.Google.com";
		String path = "/%a6/Bing/78";
		String query = "%a2&Ss2=file";
		Uri uri = UriParserFactory.create(scheme+"://"+userinfo+"@"+host+path+"?"+query).parse();
		assertEquals(scheme,uri.getScheme());
		assertEquals(userinfo,uri.getUserInfo());
		assertEquals(path,uri.getPath());
		assertEquals(query,uri.getQuery());
		assertEquals(host,uri.getHost().toString());
	}*/
	
	@Test
	public void Correctscheme(){
		String scheme = "Sch3mde";
		Uri uri = UriParserFactory.create(scheme+"://").parse();
		assertNotNull(uri);
		assertEquals(scheme,uri.getScheme());
	}
	
	@Test
	public void CorrectschemeSmall(){
		String scheme = "a";
		Uri uri = UriParserFactory.create(scheme+"://").parse();
		assertNotNull(uri);
		assertEquals(scheme,uri.getScheme());
	}
	
	@Test
	public void CorrectUserinfoNormal(){
		String userinfo ="%4F..%9a::alB.";
		Uri uri = UriParserFactory.create("https://"+userinfo+"@").parse();
		assertNotNull(uri);
		assertEquals(userinfo,uri.getUserInfo());
	}
	
	@Test
	public void CorrectUserinfoDoubpoint(){
		String userinfo ="::::::";
		Uri uri = UriParserFactory.create("https://"+userinfo+"@").parse();
		assertNotNull(uri);
		assertEquals(userinfo,uri.getUserInfo());
	}

	@Test
	public void CorrectUserinfoEmpty(){
		String userinfo ="";
		Uri uri = UriParserFactory.create("https://"+userinfo+"@").parse();
		assertNotNull(uri);
		assertEquals(userinfo,uri.getUserInfo());
	}
	

	@Test
	public void CorrectUserinfoNull(){
		Uri uri = UriParserFactory.create("https://prog2.de").parse();
		assertNotNull(uri);
		assertNull(uri.getUserInfo());
	}

	@Test
	public void CorrectpathDoubSlash(){
		String path ="//";
		Uri uri = UriParserFactory.create("https://"+path).parse();
		assertNotNull(uri);
		assertEquals(path,uri.getPath());
	}

	@Test
	public void CorrectpathNormal(){
		String path ="/%a6/Bing/78.";
		Uri uri = UriParserFactory.create("https://"+path).parse();
		assertNotNull(uri);
		assertEquals(path,uri.getPath());
	}

	@Test
	public void CorrectpathOneSlash(){
		String path ="/";
		Uri uri = UriParserFactory.create("https://"+path).parse();
		assertNotNull(uri);
		assertEquals(path,uri.getPath());
	}

	@Test
	public void CorrectqueryNormal(){
		String query = "%a2&Ss2=file.";
		Uri uri = UriParserFactory.create("https://google.com/bing?"+query).parse();
		assertNotNull(uri);
		assertEquals(query,uri.getQuery());
	}
	
	@Test
	public void CorrectqueryEmpty(){
		String query = "";
		Uri uri = UriParserFactory.create("https://google.com/bing?"+query).parse();
		assertNotNull(uri);
		assertEquals(query,uri.getQuery());
	}

	@Test
	public void CorrectqueryNull(){
		Uri uri = UriParserFactory.create("https://GOOGLE.COM/bing").parse();
		assertNotNull(uri);
		assertNull(uri.getQuery());
	}

	@Test
	public void CorrectquerySpecial(){
		String query = "&&==&&";
		Uri uri = UriParserFactory.create("https://google.com/bing?"+query).parse();
		assertNotNull(uri);
		assertEquals(query,uri.getQuery());
	}
	
	@Test
	public void CorrectHostNormal(){
		String host = "%a1..Google5.com";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectHostEmpty(){
		String host = "";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectHostEmpty2(){
		String host = "";
		Uri uri = UriParserFactory.create("https://"+host+"/path").parse();
		assertNotNull(uri);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectIP4(){
		String ip4 = "04.1.000.255";
		Uri uri = UriParserFactory.create("https://"+ip4).parse();
		assertNotNull(uri);
		assertTrue(uri.getHost() instanceof  IPv4Address);
		assertEquals("4.1.0.255",uri.getHost().toString());
	}

	@Test
	public void CorrectIP4_2(){
		String ip4 = "168.193.56.1";
		Uri uri = UriParserFactory.create("https://"+ip4+"/path").parse();
		assertNotNull(uri);
		assertTrue(uri.getHost() instanceof  IPv4Address);
		assertEquals(ip4,uri.getHost().toString());
	}
	

	@Test
	public void CorrectIPTestOctets(){
		String ip4 = "255.000.056.001";
		Uri uri = UriParserFactory.create("https://"+ip4+"/path").parse();
		assertNotNull(uri);
		assertTrue(uri.getHost() instanceof  IPv4Address);
		IPv4Address ip4_2 =(IPv4Address) uri.getHost();
		byte[] b = ip4_2.getOctets();
		assertEquals(255,b[0]& 0xff);
		assertEquals(0,b[1]& 0xff);
		assertEquals(56,b[2]& 0xff);
		assertEquals(1,b[3]& 0xff);
	}

	@Test
	public void CorrectHostNotIp4(){
		String host = "04.1.300.256";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertFalse(uri.getHost() instanceof  IPv4Address);
		assertTrue(uri.getHost() instanceof  Host);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectHostNotIp4_2(){
		String host = "04.1.000.255.255";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertTrue(uri.getHost() instanceof  Host);
		assertFalse(uri.getHost() instanceof  IPv4Address);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectHostNotIp4_3(){
		String host = "04.1.0000.0255";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertFalse(uri.getHost() instanceof  IPv4Address);
		assertTrue(uri.getHost() instanceof  Host);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectInputComplet(){
		String host = "..prog2%4f%AF";
		String path = "/..bing/%4f%AF/3";
		String userinfo = ":..weberpascal96%4f%AF";
		String query = "=&file%4f%AF.";
		Uri uri = UriParserFactory.create("https5://"+userinfo+"@"+host+path+"?"+ query).parse();
		assertNotNull(uri);
		assertEquals(host,uri.getHost().toString());
		assertEquals(path,uri.getPath());
		assertEquals(userinfo,uri.getUserInfo());
		assertEquals(query,uri.getQuery());
	}
	
	@Test
	public void CorrectHostOnlyPoints(){
		String host = "....";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertFalse(uri.getHost() instanceof  IPv4Address);
		assertEquals(host,uri.getHost().toString());
	}
	
	@Test
	public void CorrectHostNotIp4_4(){
		String host = "04.1.000.";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertFalse(uri.getHost() instanceof  IPv4Address);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectHostNotIp4_5(){
		String host = "04.%01.000.255";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertFalse(uri.getHost() instanceof  IPv4Address);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectHostNotIp4_6(){
		String host = "04..01.000.255";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertFalse(uri.getHost() instanceof  IPv4Address);
		assertEquals(host,uri.getHost().toString());
	}

	@Test
	public void CorrectHostSimple(){
		String host ="abentz";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertEquals(host,uri.getHost().toString());
	}
	

	@Test
	public void CorrectHostSimple2(){
		String host ="96";
		Uri uri = UriParserFactory.create("https://"+host).parse();
		assertNotNull(uri);
		assertEquals(host,uri.getHost().toString());
	}
	
	
}
