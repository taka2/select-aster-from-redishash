package select.aster.from.redishash.redis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import select.aster.from.redishash.exception.ApplicationException;

public class QueryParser {
	String regexp1 = "\\p{Space}*from\\p{Space}*(\\p{Alpha}+)\\p{Space}*";
	String regexp2 = "\\p{Space}*from\\p{Space}*(\\p{Alpha}+)\\p{Space}*(\\p{Graph}+)\\p{Space}*";
	String[] regexpArray = {regexp1, regexp2};

	List<Pattern> patterns;
	
	public QueryParser() {
		patterns = new ArrayList<>();
		for(String regexp : regexpArray) {
			patterns.add(Pattern.compile(regexp, Pattern.CASE_INSENSITIVE));
		}
	}
	
	public QueryData parseQuery(String query) {
		for(Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(query);
			if(matcher.matches()) {
				if(matcher.groupCount() == 1) {
					QueryData result = new QueryData(matcher.group(1), "*");
					return result;
				} else if(matcher.groupCount() == 2) {
					QueryData result = new QueryData(matcher.group(1), matcher.group(2));
					return result;
				}
			}
		}
		
		throw new ApplicationException("Query parse error. query = " + query);
	}
}
