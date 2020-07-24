package select.aster.from.redishash.service;

public class QueryData {
	private String hashKey;
	private String filterField;
	private String filterValue;
	
	public QueryData(String hashKey, String filterField, String filterValue) {
		this.hashKey = hashKey;
		this.filterField = filterField;
		this.filterValue = filterValue;
	}

	public String getHashKey() {
		return hashKey;
	}

	public String getFilterField() {
		return filterField;
	}

	public String getFilterValue() {
		return filterValue;
	}
}
