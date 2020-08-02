package select.aster.from.redishash.redis.service;

import java.util.List;

import select.aster.from.redishash.redis.model.RedisHashData;

public class QueryResult {
	private String[] selectFields;
	private List<RedisHashData> queryResultList;
	private boolean queryResultCountExceeded;
	private int queryResultLimitCount;
	private int keysCount;
	
	public QueryResult(String[] selectFields, List<RedisHashData> queryResultList, boolean queryResultCountExceeded, int queryResultLimitCount, int keysCount) {
		this.selectFields = selectFields;
		this.queryResultList = queryResultList;
		this.queryResultCountExceeded = queryResultCountExceeded;
		this.queryResultLimitCount = queryResultLimitCount;
		this.keysCount = keysCount;
	}

	public String[] getSelectFields() {
		return selectFields;
	}
	
	public List<RedisHashData> getQueryResultList() {
		return queryResultList;
	}

	public boolean isQueryResultCountExceeded() {
		return queryResultCountExceeded;
	}

	public int getQueryResultLimitCount() {
		return queryResultLimitCount;
	}

	public int getKeysCount() {
		return keysCount;
	}
}

