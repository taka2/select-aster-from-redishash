package select.aster.from.redishash.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
	String regexp1 = "from (\\p{Alpha}+)";
	String regexp2 = "from (\\p{Alpha}+) where (\\p{Alnum}+)=(\\p{Graph}+)";

	Pattern pattern1;
	Pattern pattern2;
	public QueryParser() {
		this.pattern1 = Pattern.compile(regexp1);
		this.pattern2 = Pattern.compile(regexp2);
	}
	public QueryData parseQuery(String query) {
		Matcher matcher1 = pattern1.matcher(query);
		if(matcher1.matches()) {
			QueryData result = new QueryData(matcher1.group(1), null, null);
			return result;
		} else {
			Matcher matcher2 = pattern2.matcher(query);
			if(matcher2.matches()) {
				QueryData result = new QueryData(matcher2.group(1), matcher2.group(2), matcher2.group(3));
				return result;
			}
		}
		return null;
	}
}
