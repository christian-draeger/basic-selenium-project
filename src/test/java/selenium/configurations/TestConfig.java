package selenium.configurations;

public class TestConfig {

    private TypedProperties typedProperties = new TypedProperties("/test_config.properties");

    public TestConfig() {
    }

    TestConfig(final TypedProperties typedProperties) {
        this.typedProperties = typedProperties;
    }

    public String getBrowser() {
        return this.typedProperties.getValue("browser.name");
    }

    public String getBaseUrl() {
        return this.typedProperties.getValue("base_url");
    }
}
