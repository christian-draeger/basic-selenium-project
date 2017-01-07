package selenium.configurations;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * provides type-safe access to properties.
 */
public class TypedProperties {
	private final Properties properties = new Properties();

	public TypedProperties(String resourceName) {
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		try {
			properties.load(inputStream);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getValue(final String key) {
		return properties.getProperty(key);
	}

	public boolean getBoolean(final String key) {
		return parseBoolean(getValue(key));
	}

	public int getInt(final String key) {
		return parseInt(getValue(key));
	}
}
