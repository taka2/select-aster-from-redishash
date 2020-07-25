package select.aster.from.redishash.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import select.aster.from.redishash.exception.ApplicationException;

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
	}
	
	@Test
	public void testParse_nowhere_large() {
		QueryData queryData = queryParser.parseQuery("FROM HashA");
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_nowhere_mixed() {
		QueryData queryData = queryParser.parseQuery("From HashA");
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_nowhere_small_withspace() {
		QueryData queryData = queryParser.parseQuery(" from  HashA ");
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_where_small() {
		QueryData queryData = queryParser.parseQuery("from HashB where key1=value1");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals("key1", queryData.getFilterField());
		Assertions.assertEquals("value1", queryData.getFilterValue());
	}
	
	@Test
	public void testParse_where_large() {
		QueryData queryData = queryParser.parseQuery("FROM HashB where key1=value1");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals("key1", queryData.getFilterField());
		Assertions.assertEquals("value1", queryData.getFilterValue());
	}
	
	@Test
	public void testParse_where_mixed() {
		QueryData queryData = queryParser.parseQuery("From HashB where key1=value1");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals("key1", queryData.getFilterField());
		Assertions.assertEquals("value1", queryData.getFilterValue());
	}
	
	@Test
	public void testParse_where_small_withspace() {
		QueryData queryData = queryParser.parseQuery(" from   HashB  where    key1 =   value1  ");
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals("key1", queryData.getFilterField());
		Assertions.assertEquals("value1", queryData.getFilterValue());
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
	
	@Test
	public void testParse_fromwhere() {
		try {
			queryParser.parseQuery("from where");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_whereonly() {
		try {
			queryParser.parseQuery("from HashA where");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_nokey() {
		try {
			queryParser.parseQuery("from HashA where =value1");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_novalue() {
		try {
			queryParser.parseQuery("from HashA where key1=");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
}
