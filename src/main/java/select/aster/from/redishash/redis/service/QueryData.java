package select.aster.from.redishash.redis.service;

public class QueryData {
	private String hashKey;
	private String keysPattern;
	
	public QueryData(String hashKey, String keysPattern) {
		this.hashKey = hashKey;
		this.keysPattern = keysPattern;
	}

	public String getHashKey() {
		return hashKey;
	}

	public String getKeysPattern() {
		return keysPattern;
	}
}
