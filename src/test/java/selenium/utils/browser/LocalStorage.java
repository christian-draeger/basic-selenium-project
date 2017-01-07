package selenium.utils.browser;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class LocalStorage {

    private final JavascriptExecutor js;

    public LocalStorage(final WebDriver webDriver) {
        this.js = (JavascriptExecutor) webDriver;
    }

    public void removeItemFromLocalStorage(final String item) {
        this.js.executeScript(String.format("window.localStorage.removeItem('%s');", item));
    }

    public boolean isItemPresentInLocalStorage(final String item) {
        return !(this.js.executeScript(String.format("return window.localStorage.getItem('%s');", item)) == null);
    }

    public String getItemFromLocalStorage(final String key) {
        return (String) this.js.executeScript(String.format("return window.localStorage.getItem('%s');", key));
    }

    public String getKeyFromLocalStorage(final int key) {
        return (String) this.js.executeScript(String.format("return window.localStorage.key('%s');", Integer.valueOf(key)));
    }

    public List<String> getAllKeysFromLocalStorage() {
        List<String> keys = new ArrayList<>();
        for(int i = 0; i < getLocalStorageLength(); i++){
            keys.add(getKeyFromLocalStorage(i));
        }
        return keys;
    }

    public Long getLocalStorageLength() {
        return (Long) this.js.executeScript("return window.localStorage.length;");
    }

    public void setItemInLocalStorage(final String item, final String value) {
        this.js.executeScript(String.format("window.localStorage.setItem('%s','%s');", item, value));
    }

    public void clearLocalStorage() {
        this.js.executeScript(String.format("window.localStorage.clear();"));
    }

}
