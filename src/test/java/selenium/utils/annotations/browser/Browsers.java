package selenium.utils.annotations.browser;

public enum Browsers {

    FIREFOX("firefox"),
    FIREFOX_HEADLESS("firefox-headless"),
    CHROME("chrome"),
    CHROME_HEADLESS("chrome-headless"),
    OPERA("opera"),
    EDGE("MicrosoftEdge"),
    INTERNET_EXPLORER("internet-explorer");

    private String value;

    Browsers(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public enum options {
        SKIPPED,
        REQUIRED
    }
}
