package select.aster.from.redishash.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import select.aster.from.redishash.exception.ApplicationException;
import select.aster.from.redishash.redis.service.QueryData;
import select.aster.from.redishash.redis.service.QueryParser;

public class QueryParserTest {
	private QueryParser queryParser;
	
	@BeforeEach
	public void beforeEach() {
		this.queryParser = new QueryParser();
	}
	
	// OK
	
	@Test
	public void testParse_nowhere_small() {
		QueryData queryData = queryParser.parseQuery("from HashA");
		Assertions.assertEquals("HashA", queryData.getHashKey());
		Assertions.assertEquals("*", queryData.getKeysPattern());
	}
	
	@Test
	public void testParse_nowhere_large() {
		QueryData queryData = queryParser.parseQuery("FROM HashA");
		Assertions.assertEquals("HashA", queryData.getHashKey());
		Assertions.assertEquals("*", queryData.getKeysPattern());
	}
	
	@Test
	public void testParse_nowhere_mixed() {
		QueryData queryData = queryParser.parseQuery("From HashA");
		Assertions.assertEquals("HashA", queryData.getHashKey());
		Assertions.assertEquals("*", queryData.getKeysPattern());
	}
	
	@Test
	public void testParse_nowhere_small_withspace() {
		QueryData queryData = queryParser.parseQuery(" from  HashA ");
		Assertions.assertEquals("HashA", queryData.getHashKey());
		Assertions.assertEquals("*", queryData.getKeysPattern());
	}
	
	@Test
	public void testParse_where_small() {
		QueryData queryData = queryParser.parseQuery("from HashB :2020*");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals(":2020*", queryData.getKeysPattern());
	}
	
	@Test
	public void testParse_where_large() {
		QueryData queryData = queryParser.parseQuery("FROM HashB :2020*");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals(":2020*", queryData.getKeysPattern());
	}
	
	@Test
	public void testParse_where_mixed() {
		QueryData queryData = queryParser.parseQuery("From HashB :2020*");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals(":2020*", queryData.getKeysPattern());
	}
	
	@Test
	public void testParse_where_small_withspace() {
		QueryData queryData = queryParser.parseQuery(" from   HashB  :2020*  ");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals(":2020*", queryData.getKeysPattern());
	}
	
	// NG
	
	@Test
	public void testParse_blank() {
		try {
			queryParser.parseQuery("");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_space() {
		try {
			queryParser.parseQuery(" ");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_nofrom() {
		try {
			queryParser.parseQuery("HashA");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_fromonly() {
		try {
			queryParser.parseQuery("from");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
}
