package selenium.utils.browser;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class Cookies {

    private final WebDriver driver;

    public Cookies(final WebDriver driver) {
        this.driver = driver;
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    public Cookie getCookieNamed(String name) {
        return driver.manage().getCookieNamed(name);
    }

    public String getValueOfCookieNamed(String name) {
        return driver.manage().getCookieNamed(name).getValue();
    }

    public void addCookie(String name, String value, String domain, String path, Date expiry) {
        driver.manage().addCookie(new Cookie(name, value, domain, path, expiry));
    }

    public boolean isCookiePresent(String name) {
        try {
            if (getCookieNamed(name) != null) {
                return true;
            }
        } catch (NullPointerException npe) {
        }
        return false;
    }

    public void deleteCookieNamed(String name) {
        driver.manage().deleteCookieNamed(name);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public Date getValideExpireDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();

    }

}
