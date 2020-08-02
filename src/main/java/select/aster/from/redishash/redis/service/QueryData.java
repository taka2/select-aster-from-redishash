package select.aster.from.redishash.redis.service;

public class QueryData {
	private String[] selectFields;
	private String hashKey;
	private String filterField;
	private String filterValue;
	
	public QueryData(String[] selectFields, String hashKey, String filterField, String filterValue) {
		this.selectFields = selectFields;
		this.hashKey = hashKey;
		this.filterField = filterField;
		this.filterValue = filterValue;
	}

	public String[] getSelectFields() {
		return selectFields;
	}
	
	public void setSelectFields(String[] selectFields) {
		this.selectFields = selectFields;
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
