package select.aster.from.redishash.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
	String regexp1 = "from (\\p{Alpha}+)";
	String regexp2 = "from (\\p{Alpha}+) where (\\p{Alnum}+)=(\\p{Graph}+)";
	String[] regexpArray = {regexp1, regexp2};

	List<Pattern> patterns;
	
	public QueryParser() {
		patterns = new ArrayList<>();
		for(String regexp : regexpArray) {
			patterns.add(Pattern.compile(regexp));
		}
	}
	
	public QueryData parseQuery(String query) {
		for(Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(query);
			if(matcher.matches()) {
				if(matcher.groupCount() == 1) {
					QueryData result = new QueryData(matcher.group(1), null, null);
					return result;
				} else if(matcher.groupCount() == 3) {
					QueryData result = new QueryData(matcher.group(1), matcher.group(2), matcher.group(3));
					return result;
				}
			}
		}
		
		return null;
	}
}
