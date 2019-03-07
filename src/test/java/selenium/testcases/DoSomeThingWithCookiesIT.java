package selenium.testcases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeFalse;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import selenium.SeleniumTestWrapper;
import selenium.pageobjects.StartPage;
import selenium.utils.browser.Cookies;

public class DoSomeThingWithCookiesIT extends SeleniumTestWrapper {

    private StartPage startPage = PageFactory.initElements(getDriver(), StartPage.class);
    private Cookies cookies = new Cookies(getDriver());

    @Before
    public void setup() {
        startPage.open();
    }

    @Test
    public void checkSomeValueFromCertainCookie() {
        assertThat(cookies.getValueOfCookieNamed("logged_in")).isEqualTo("no");
    }

    @Test
    public void addCustomCookie() {
        // skip if browser is phantomjs
        assumeFalse(testConfig.getBrowser().equals("phantomjs"));

        cookies.addCookie("myTestCookie", "added by selenium","github.com", "/", cookies.getValideExpireDate());
        // check if custom cookie has been added successfully
        assertThat(cookies.getValueOfCookieNamed("myTestCookie")).isEqualTo("added by selenium");
    }

    @Test
    public void deleteCertainCookie() {

        // check if cookie exists initially
        assertThat(cookies.isCookiePresent("logged_in")).isTrue();

        // delete certain cookie
        cookies.deleteCookieNamed("logged_in");

        // check if cookie was deleted successfully
        assertThat(cookies.isCookiePresent("logged_in")).isFalse();
    }

    @Test
    public void deleteAllCookies() {

        // check if number of localStorage is greater than 0
        assertThat(cookies.getAllCookies().size()).isGreaterThan(0);

        // delete all localStorage
        cookies.deleteAllCookies();

        // check if number of localStorage is 0
        assertThat(cookies.getAllCookies().size()).isZero();
    }
}
