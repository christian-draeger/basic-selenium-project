package selenium.testcases;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static selenium.utils.annotations.browser.Browsers.PHANTOMJS;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import selenium.SeleniumTestWrapper;
import selenium.pageobjects.StartPage;
import selenium.utils.annotations.browser.Browser;
import selenium.utils.browser.LocalStorage;

/** session storage works exactly the same **/
@Browser(skip = PHANTOMJS)
public class DoSomeThingWithLocalStorageIT extends SeleniumTestWrapper {

    StartPage startPage = PageFactory.initElements(getDriver(), StartPage.class);
    LocalStorage localStorage = new LocalStorage(getDriver());

    @Before
    public void setup() {
        startPage.open();
    }

    @Test
    public void checkSomeValueFromCertainLocalStorageEntry() {
        assertThat(localStorage.getItemFromLocalStorage("logged-in"), is("false"));
    }

    @Test
    public void addCustomLocalStorageEntry() {
        localStorage.setItemInLocalStorage("myLocalStorageEntry", "added by selenium");

        // get all keys from localstorage and check if added entry is present
        assertThat(localStorage.getAllKeysFromLocalStorage(), hasItem("myLocalStorageEntry"));

        // check if custom localStorage entry has been added successfully with expected value
        assertThat(localStorage.getItemFromLocalStorage("myLocalStorageEntry"), is("added by selenium"));
    }

    @Test
    public void deleteCertainLocalStorageEntry() {

        // check if localStorage entry exists initially
        assertThat(localStorage.isItemPresentInLocalStorage("logged-in"), is(true));

        // delete certain localStorage entry
        localStorage.removeItemFromLocalStorage("logged-in");

        // check if localStorage entry was deleted successfully
        assertThat(localStorage.isItemPresentInLocalStorage("logged-in"), is(false));
    }

    @Test
    public void deleteAllLocalStorageEntries() {

        // check if number of localStorage entries is greater than 0
        assertThat(localStorage.getLocalStorageLength(), greaterThan(0L));

        // delete all localStorage entries
        localStorage.clearLocalStorage();

        // check if number of localStorage entries is 0
        assertThat(localStorage.getLocalStorageLength(), is(0L));
    }
}
