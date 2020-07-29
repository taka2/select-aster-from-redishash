package select.aster.from.redishash.redis.service;

import java.util.List;

import select.aster.from.redishash.redis.model.RedisHashData;

public class QueryResult {
	private List<RedisHashData> queryResultList;
	private boolean queryResultCountExceeded;
	private int queryResultLimitCount;
	private int keysCount;
	
	public QueryResult(List<RedisHashData> queryResultList, boolean queryResultCountExceeded, int queryResultLimitCount, int keysCount) {
		this.queryResultList = queryResultList;
		this.queryResultCountExceeded = queryResultCountExceeded;
		this.queryResultLimitCount = queryResultLimitCount;
		this.keysCount = keysCount;
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

	public void setQueryResultLimitCount(int queryResultLimitCount) {
		this.queryResultLimitCount = queryResultLimitCount;
	}

	public int getKeysCount() {
		return keysCount;
	}

	public void setKeysCount(int keysCount) {
		this.keysCount = keysCount;
	}
}

