package select.aster.from.redishash.util;

import java.util.Properties;

public class PropertyUtil {
	private static final String PROPERTY_FILE_NAME = "/application.properties";

	private static final String KEY_REDIS_CONNECTION_STRING = "redis.connection-string";
	private static final String KEY_REDIS_MAX_SHOW_LINES = "redis.max-show-lines";
	
	private Properties properties;

	public PropertyUtil() {
		try {
			this.properties = new Properties();
			properties.load(PropertyUtil.class.getResourceAsStream(PROPERTY_FILE_NAME));
		} catch(Exception e) {
			throw new RuntimeException("Cannot read property file. " + PROPERTY_FILE_NAME);
		}
	}
	
	public String getRedisConnectionString() {
		return properties.getProperty(KEY_REDIS_CONNECTION_STRING);
	}
	
	public Integer getMaxShowLines() {
		return Integer.parseInt(properties.getProperty(KEY_REDIS_MAX_SHOW_LINES));
	}
}
