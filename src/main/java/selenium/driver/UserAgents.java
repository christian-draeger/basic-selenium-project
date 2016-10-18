package selenium.driver;

/**
 * Represents the different user agents for browsers.
 */
public enum UserAgents {

    /**
     * User agent from android smartphone.
     */
    ANDROID("Mozilla/5.0 (Linux; U; Android 4.0.4; en-gb; GT-I9300 Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"),

    /**
     * User agent from Apple iPhone, iOS 4.
     */
    IPHONE_I_OS_4(
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7"),

    /**
     * User agent from Apple iPhone, iOS 5.
     */
    IPHONE_I_OS_5(
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_1_1 like Mac OS X; da-dk) AppleWebKit/534.46.0 (KHTML, like Gecko) CriOS/19.0.1084.60 Mobile/9B206 Safari/7534.48.3"),

    /**
     * User agent from Apple iPhone, iOS 6.
     */
    IPHONE_I_OS_6("Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25"),

    /**
     * User agent from Apple iPhone, iOS 6, Chrome Browser.
     */
    IPHONE_I_OS_6_CHROME_MOBILE(
            "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/27.0.1453.10 Mobile/10B350 Safari/8536.25"),

    /**
     * User agent from Apple iPhone, iOS 7.
     */
    IPHONE_I_OS_7("Mozilla/5.0 (iPhone; CPU iPhone OS 7 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25"),

    /**
     * User agent from Apple iPhone, iOS 8.
     */
    IPHONE_I_OS_8("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A405 Safari/600.1.4"),

	/**
	 * User agent from Apple iPhone, iOS 9.
	 */
	IPHONE_I_OS_9(
			"Mozilla/5.0 (iPhone; CPU iPhone OS 9_0_2 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13A452 Safari/601.1"),

    // Tablets

    /**
     * User agent from android tablet.
     */
    ANDROID_TABLET("Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19"),

    /**
     * User agent from android tablet with android 5.
     */
    ANDROID_TABLET_ANDROID_5(
            "Mozilla/5.0 (Linux; Android 5.0.2; Nexus 10 Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.93 Safari/537.36"),

    /**
     * User agent from apple tablet.
     */
    IPAD_I_OS_3("Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.10"),

    /**
     * User agent from apple iPad 2.
     */
    IPAD_I_OS_6("Mozilla/5.0 (iPad; CPU OS 6_1 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B141 Safari/8536.25"),

    /**
     * User agent from apple iPad 3.
     */
    IPAD_I_OS_7("Mozilla/5.0 (iPad; CPU OS 7_0_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B141 Safari/8536.25"),

    /**
     * User agent from apple iPad 4.
     */
    IPAD_4_IOS_7("Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53"),

    /**
     * User agent from apple iPad 4 with iOS 8.
     */
    IPAD_4_IOS_8("Mozilla/5.0 (iPad; CPU OS 8_0 like Mac OS X) AppleWebKit/538.34.9 (KHTML, like Gecko) Mobile/12A4265u"),

	/**
	 * User agent from apple iPad 4 with iOS 8.
	 */
	IPAD_4_IOS_9(
			"Mozilla/5.0 (iPad; CPU OS 9_0 like Mac OS X) AppleWebKit/601.1.17 (KHTML, like Gecko) Version/8.0 Mobile/13A175 Safari/600.1.4");

    private String value;

    UserAgents(final String value) {
        this.value = value;
    }

    /**
     * @return User agent.
     */
    public String getValue() {
        return this.value;
    }

}
