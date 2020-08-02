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
	public void testParse_oneField() {
		QueryData queryData = queryParser.parseQuery("select field1 from HashA");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("field1", queryData.getSelectFields()[0]);
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_twoFields() {
		QueryData queryData = queryParser.parseQuery("select field1,field2 from HashA");
		Assertions.assertEquals(2, queryData.getSelectFields().length);
		Assertions.assertEquals("field1", queryData.getSelectFields()[0]);
		Assertions.assertEquals("field2", queryData.getSelectFields()[1]);
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_threeFields() {
		QueryData queryData = queryParser.parseQuery("select field1,field2,field3 from HashA");
		Assertions.assertEquals(3, queryData.getSelectFields().length);
		Assertions.assertEquals("field1", queryData.getSelectFields()[0]);
		Assertions.assertEquals("field2", queryData.getSelectFields()[1]);
		Assertions.assertEquals("field3", queryData.getSelectFields()[2]);
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_nowhere_small() {
		QueryData queryData = queryParser.parseQuery("select * from HashA");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("*", queryData.getSelectFields()[0]);
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_nowhere_large() {
		QueryData queryData = queryParser.parseQuery("SELECT * FROM HashA");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("*", queryData.getSelectFields()[0]);
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_nowhere_mixed() {
		QueryData queryData = queryParser.parseQuery("Select * From HashA");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("*", queryData.getSelectFields()[0]);
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_nowhere_small_withspace() {
		QueryData queryData = queryParser.parseQuery("select * from  HashA ");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("*", queryData.getSelectFields()[0]);
		Assertions.assertEquals("HashA", queryData.getHashKey());
	}
	
	@Test
	public void testParse_where_small() {
		QueryData queryData = queryParser.parseQuery("select * from HashB where key1=value1");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("*", queryData.getSelectFields()[0]);
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals("key1", queryData.getFilterField());
		Assertions.assertEquals("value1", queryData.getFilterValue());
	}
	
	@Test
	public void testParse_where_large() {
		QueryData queryData = queryParser.parseQuery("SELECT * FROM HashB where key1=value1");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("*", queryData.getSelectFields()[0]);
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals("key1", queryData.getFilterField());
		Assertions.assertEquals("value1", queryData.getFilterValue());
	}
	
	@Test
	public void testParse_where_mixed() {
		QueryData queryData = queryParser.parseQuery("Select * From HashB where key1=value1");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("*", queryData.getSelectFields()[0]);
		Assertions.assertEquals("HashB", queryData.getHashKey());
		Assertions.assertEquals("key1", queryData.getFilterField());
		Assertions.assertEquals("value1", queryData.getFilterValue());
	}
	
	@Test
	public void testParse_where_small_withspace() {
		QueryData queryData = queryParser.parseQuery("select    *    from   HashB  where    key1 =   value1  ");
		Assertions.assertEquals(1, queryData.getSelectFields().length);
		Assertions.assertEquals("*", queryData.getSelectFields()[0]);
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
	public void testParse_selectonly() {
		try {
			queryParser.parseQuery("select");
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
	public void testParse_selectfrom() {
		try {
			queryParser.parseQuery("select from");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}

	
	@Test
	public void testParse_fromwhere() {
		try {
			queryParser.parseQuery("select * from where");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_whereonly() {
		try {
			queryParser.parseQuery("select * from HashA where");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_selectfromwhereonly() {
		try {
			queryParser.parseQuery("select from where");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_nokey() {
		try {
			queryParser.parseQuery("select * from HashA where =value1");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
	
	@Test
	public void testParse_novalue() {
		try {
			queryParser.parseQuery("select * from HashA where key1=");
			Assertions.fail();
		} catch(ApplicationException e) {
			// OK
		}
	}
}
