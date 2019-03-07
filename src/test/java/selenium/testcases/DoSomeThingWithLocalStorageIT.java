package selenium.testcases;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static selenium.utils.annotations.browser.Browsers.INTERNET_EXPLORER;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import selenium.SeleniumTestWrapper;
import selenium.pageobjects.StartPage;
import selenium.utils.annotations.browser.Browser;
import selenium.utils.browser.LocalStorage;

/** session storage works exactly the same **/
@Browser(skip = INTERNET_EXPLORER)
public class DoSomeThingWithLocalStorageIT extends SeleniumTestWrapper {

    StartPage startPage = PageFactory.initElements(getDriver(), StartPage.class);
    LocalStorage localStorage = new LocalStorage(getDriver());

    @Before
    public void setup() {
        startPage.open();
    }

    @Test
    public void checkSomeValueFromCertainLocalStorageEntry() {
        assertThat(localStorage.getItemFromLocalStorage("logged-in")).isEqualTo("false");
    }

    @Test
    public void addCustomLocalStorageEntry() {
        localStorage.setItemInLocalStorage("myLocalStorageEntry", "added by selenium");

        // get all keys from localstorage and check if added entry is present
        assertThat(localStorage.getAllKeysFromLocalStorage()).contains("myLocalStorageEntry");

        // check if custom localStorage entry has been added successfully with expected value
        assertThat(localStorage.getItemFromLocalStorage("myLocalStorageEntry")).isEqualTo("added by selenium");
    }

    @Test
    public void deleteCertainLocalStorageEntry() {

        // check if localStorage entry exists initially
        assertThat(localStorage.isItemPresentInLocalStorage("logged-in")).isTrue();

        // delete certain localStorage entry
        localStorage.removeItemFromLocalStorage("logged-in");

        // check if localStorage entry was deleted successfully
        assertThat(localStorage.isItemPresentInLocalStorage("logged-in")).isFalse();
    }

    @Test
    public void deleteAllLocalStorageEntries() {

        // check if number of localStorage entries is greater than 0
        assertThat(localStorage.getLocalStorageLength()).isGreaterThan(0L);

        // delete all localStorage entries
        localStorage.clearLocalStorage();

        // check if number of localStorage entries is 0
        assertThat(localStorage.getLocalStorageLength()).isZero();
    }
}
