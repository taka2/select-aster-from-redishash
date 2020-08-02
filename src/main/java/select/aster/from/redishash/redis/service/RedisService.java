package select.aster.from.redishash.redis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import select.aster.from.redishash.redis.model.RedisHashData;
import select.aster.from.redishash.util.PropertyUtil;

public class RedisService {
	private RedisClient redisClient;
	private StatefulRedisConnection<String, String> connection;
	
	private PropertyUtil propertyUtil;
	private QueryParser queryParser;
	
	public RedisService(String connectionString) {
		this.redisClient = RedisClient.create(connectionString);
		this.connection = redisClient.connect();
		
		this.propertyUtil = new PropertyUtil();
		this.queryParser = new QueryParser();
	}
	
	public QueryResult query(String query) {
		RedisCommands<String, String> syncCommands = connection.sync();

		QueryData queryData = queryParser.parseQuery(query);

		List<RedisHashData> queryResultList = new ArrayList<>();
		boolean queryResultCountExceeded = false;

		int numLines = 0;
		// Search redis keys by "hashname+*"
		List<String> keys = syncCommands.keys(queryData.getHashKey() + "*");
		for(String key : keys) {
			// Extract hash only
			String type = syncCommands.type(key);
			if("hash".equals(type)) {
				// Retrieve hashkey
				List<String> fields = syncCommands.hkeys(key);
				Map<String, String> resultMap = new HashMap<>();
				for(String field : fields) {
					// exclude _class
					if("_class".equals(field)) {
						continue;
					}
					resultMap.put(field, syncCommands.hget(key, field));
				}
				
				// filter
				if(queryData.getFilterField() == null) {
					// no filter
				} else {
					String valueOfFilterField = resultMap.get(queryData.getFilterField());
					if(valueOfFilterField == null) {
						// no filter value
					} else {
						if(valueOfFilterField.equals(queryData.getFilterValue())) {
							// matched
						} else {
							// unmatched
							continue;
						}
					}
				}
				
				queryResultList.add(new RedisHashData(type, resultMap));
				numLines++;
				if(numLines >= propertyUtil.getMaxShowLines()) {
					queryResultCountExceeded = true;
					break;
				}
			}
		}
		
		return new QueryResult(queryResultList, queryResultCountExceeded, propertyUtil.getMaxShowLines(), keys.size());
	}
	
	public void close() {
		connection.close();
		redisClient.shutdown();
	}
}
