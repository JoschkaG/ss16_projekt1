package uri.tests;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import uri.UriParserFactory;

public class InvalidTests {
	
	@Test
	
	public void schemeFirstnum(){
		assertNull(UriParserFactory.create("4a://").parse());
	}
	
	@Test
	public void schemeEmpty(){
		assertNull(UriParserFactory.create("://").parse());
	}
	
	@Test
	public void schemeOE(){
		assertNull(UriParserFactory.create("ö://").parse());
	}
	
	@Test
	public void schemeOEBig(){
		assertNull(UriParserFactory.create("Ö://").parse());
	}
	
	@Test
	public void schemeOtherChar(){
		assertNull(UriParserFactory.create("a%://").parse());
	}
	
	@Test
	public void UsernameOE(){
		assertNull(UriParserFactory.create("h://ö@").parse());
	}
	
	@Test
	public void UsernameOEBig(){
		assertNull(UriParserFactory.create("h://Ö@").parse());
	}
	
	@Test
	public void UsernameOtherChar(){
		assertNull(UriParserFactory.create("h://=@").parse());
	}
	
	@Test
	public void UsernametoLessHex(){
		assertNull(UriParserFactory.create("h://%a@").parse());
	}
	
	@Test
	public void UsernameTwoadd(){
		assertNull(UriParserFactory.create("h://a@a@").parse());
	}
	
	@Test
	public void UsernameHexChartoobig(){
		assertNull(UriParserFactory.create("h://%5g@").parse());
	}
	
	@Test
	public void UserInfoMissingAt(){
		assertNull(UriParserFactory.create("h://Hhg:").parse());
	}
	
	@Test
	public void querrytwoquestmarks(){
		assertNull(UriParserFactory.create("h://?h?H").parse());
	}
	
	@Test
	public void querryOE(){
		assertNull(UriParserFactory.create("h://?ö").parse());
	}
	
	@Test
	public void querryOEBig(){
		assertNull(UriParserFactory.create("h://?Ö").parse());
	}

	@Test
	public void querrytooLessHex(){
		assertNull(UriParserFactory.create("h://?%a").parse());
	}
	
	@Test
	public void querryHexChartooBig(){
		assertNull(UriParserFactory.create("h://?%ga").parse());
	}
	
	@Test
	public void querryOtherChar(){
		assertNull(UriParserFactory.create("h://?-").parse());
	}

	@Test
	public void querryMissingQuestionmark(){
		assertNull(UriParserFactory.create("h://gooogle.com&.=").parse());
	}
	
	@Test
	public void pathOE(){
		assertNull(UriParserFactory.create("h:///ö").parse());
	}
	
	@Test
	public void pathOEBig(){
		assertNull(UriParserFactory.create("h:///Ö").parse());
	}
	
	@Test
	public void pathHextooless(){
		assertNull(UriParserFactory.create("h:///%a").parse());
	}
	
	@Test
	public void pathHexChartoobBg(){
		assertNull(UriParserFactory.create("h:///%ga").parse());
	}
	

	@Test
	public void pathOtherChar(){
		assertNull(UriParserFactory.create("h:///-").parse());
	}
	

	@Test
	public void HostHexChartooBig(){
		assertNull(UriParserFactory.create("h://%4K").parse());
	}

	@Test
	public void HostToLessHex(){
		assertNull(UriParserFactory.create("h://%a").parse());
	}
	

	@Test
	public void HostHexDoubPro(){
		assertNull(UriParserFactory.create("h://%%").parse());
	}

	@Test
	public void HostOE(){
		assertNull(UriParserFactory.create("h://ö").parse());
	}

	@Test
	public void HostOEBig(){
		assertNull(UriParserFactory.create("h://Ö").parse());
	}

	@Test
	public void HostOtherChar(){
		assertNull(UriParserFactory.create("h://=3$&§.[]{}").parse());
	}

	@Test
	public void HostOtherChar2(){
		assertNull(UriParserFactory.create("h://=5").parse());
	}
	

	@Test
	public void HostOtherChar3(){
		assertNull(UriParserFactory.create("h://&5").parse());
	}
	
	@Test
	public void Generalfalse1(){
		assertNull(UriParserFactory.create("http//").parse());
	}

	@Test
	public void Generalfalse2(){
		assertNull(UriParserFactory.create("http:/").parse());
	}

	@Test
	public void Generalfalse3(){
		assertNull(UriParserFactory.create("http:google.com").parse());
	}
	
	
	
}
