package config.annotations


enum class Screen {
    SMALL,
    MEDIUM,
    LARGE,
    XLARGE,
    XXLARGE,
    FULLSCREEN,
    MAXIMIZED,
    DEFAULT,
}

enum class Browsers(val value: String) {
    FIREFOX("firefox"),
    FIREFOX_HEADLESS("firefox-headless"),
    CHROME("chrome"),
    CHROME_HEADLESS("chrome-headless"),
    SAFARI("safari"),
    OPERA("opera"),
    EDGE("edge"),
    INTERNET_EXPLORER("ie"),
    DEFAULT(System.getProperty("browser").orEmpty()),
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Browser(
    val dimension: Screen = Screen.DEFAULT,
    val use: Browsers = Browsers.DEFAULT
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Screenshot