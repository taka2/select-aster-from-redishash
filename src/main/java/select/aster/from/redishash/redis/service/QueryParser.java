package select.aster.from.redishash.redis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import select.aster.from.redishash.exception.ApplicationException;

public class QueryParser {
	String regexp1 = "select\\p{Space}*(\\p{Graph}+)\\p{Space}*from\\p{Space}*(\\p{Alpha}+)\\p{Space}*";
	String regexp2 = "select\\p{Space}*(\\p{Graph}+)\\p{Space}*from\\p{Space}*(\\p{Alpha}+)\\p{Space}*where\\p{Space}*(\\p{Alnum}+)\\p{Space}*=\\p{Space}*(\\p{Graph}+)\\p{Space}*";
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
				if(matcher.groupCount() == 2) {
					QueryData result = new QueryData(matcher.group(1).split(","), matcher.group(2), null, null);
					if("where".equals(result.getHashKey().toLowerCase())) {
						throw new ApplicationException("Hash key is 'where', it's wrong.");
					}
					return result;
				} else if(matcher.groupCount() == 4) {
					QueryData result = new QueryData(matcher.group(1).split(","), matcher.group(2), matcher.group(3), matcher.group(4));
					return result;
				}
			}
		}
		
		throw new ApplicationException("Query parse error. query = " + query);
	}
}
