package select.aster.from.redishash.redis.model;

import java.util.Map;

public class RedisHashData {
	String key;
	Map<String, String> map;
	
	public RedisHashData(String key, Map<String, String> map) {
		this.key = key;
		this.map = map;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
}
