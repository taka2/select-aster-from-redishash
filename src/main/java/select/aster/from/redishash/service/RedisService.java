package select.aster.from.redishash.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import select.aster.from.redishash.model.RedisHashData;

public class RedisService {
	private RedisClient redisClient;
	private StatefulRedisConnection<String, String> connection;
	
	private QueryParser queryParser;
	
	public RedisService(String connectionString) {
		this.redisClient = RedisClient.create(connectionString);
		this.connection = redisClient.connect();
		
		this.queryParser = new QueryParser();
	}
	
	public List<RedisHashData> query(String query) {
		RedisCommands<String, String> syncCommands = connection.sync();

		QueryData queryData = queryParser.parseQuery(query);

		List<RedisHashData> resultList = new ArrayList<>();

		// Hash��+*�ŒT��
		List<String> keys = syncCommands.keys(queryData.getHashKey() + "*");
		for(String key : keys) {
			// Hash�݂̂𒊏o
			String type = syncCommands.type(key);
			if("hash".equals(type)) {
				// HashKey���擾
				List<String> fields = syncCommands.hkeys(key);
				Map<String, String> resultMap = new HashMap<>();
				for(String field : fields) {
					// _class�����O
					if("_class".equals(field)) {
						continue;
					}
					resultMap.put(field, syncCommands.hget(key, field));
				}
				
				// filter
				if(queryData.getFilterField() == null) {
					// �t�B���^�Ȃ�
				} else {
					String valueOfFilterField = resultMap.get(queryData.getFilterField());
					if(valueOfFilterField == null) {
						// �t�B���^�t�B�[���h�ɒl�Ȃ�
					} else {
						if(valueOfFilterField.equals(queryData.getFilterValue())) {
							// ��v
						} else {
							// �s��v
							continue;
						}
					}
				}

				resultList.add(new RedisHashData(type, resultMap));
			}
		}
		
		return resultList;
	}
	
	public void close() {
		connection.close();
		redisClient.shutdown();
	}
}
