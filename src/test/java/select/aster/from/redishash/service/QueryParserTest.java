package select.aster.from.redishash.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueryParserTest {
	private QueryParser queryParser;
	
	@BeforeEach
	public void beforeEach() {
		this.queryParser = new QueryParser();
	}
	
	@Test
	public void testParse1() {
		QueryData queryData = queryParser.parseQuery("from HashA");
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse2() {
		QueryData queryData = queryParser.parseQuery("from HashB where key1=value1");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals("key1", queryData.getFilterField());
		Assertions.assertEquals("value1", queryData.getFilterValue());
	}
}
