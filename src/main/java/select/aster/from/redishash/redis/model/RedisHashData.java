package select.aster.from.redishash.redis.model;

import java.util.ArrayList;
import java.util.List;
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
	
	public String toHeaderCsvString() {
		List<String> columnNames = getColumnNames();
		
		StringBuilder sb = new StringBuilder();
		for(String columnName : columnNames) {
			if(sb.length() != 0) {
				sb.append(",");
			}
			sb.append(columnName);
		}
		
		return sb.toString();
	}
	
	public String toCsvString(List<String> columnNames) {
		Map<String, String> map = getMap();
		
		StringBuilder sb = new StringBuilder();
		for(String columnName : columnNames) {
			if(sb.length() != 0) {
				sb.append(",");
			}
			String value = map.get(columnName);
			if(value == null || "".equals(value)) {
				sb.append(" ");
			} else {
				sb.append(value);
			}
		}
		
		return sb.toString();
	}
	
	public List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<>();
		for(Map.Entry<String, String> entry : getMap().entrySet()) {
			columnNames.add(entry.getKey());
		}
		
		return columnNames;
	}
}
