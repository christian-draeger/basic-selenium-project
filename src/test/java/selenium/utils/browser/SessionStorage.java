package selenium.utils.browser;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SessionStorage {

    private final JavascriptExecutor js;
    public SessionStorage(final WebDriver webdriver){
        this.js = (JavascriptExecutor) webdriver;
    }

    public void removeItemFromSessionStorage(final String item) {
        js.executeScript(String.format("window.sessionStorage.removeItem('%s');", item));
    }

    public boolean isItemPresentInSessionStorage(final String item) {
        return !(js.executeScript(String.format("return window.sessionStorage.getItem('%s');", item)) == null);
    }

    public String getItemFromSessionStorage(final String key) {
        return (String) js.executeScript(String.format("return window.sessionStorage.getItem('%s');", key));
    }

    public String getKeyFromSessionStorage(final int key) {
        return (String) js.executeScript(String.format("return window.sessionStorage.key('%s');", Integer.valueOf(key)));
    }

    public Long getSessionStorageLength() {
        return (Long) js.executeScript("return window.sessionStorage.length;");
    }

    public void setItemInSessionStorage(final String item, final String value) {
        js.executeScript(String.format("window.sessionStorage.setItem('%s','%s');", item, value));
    }

    public void clearSessionStorage() {
        js.executeScript(String.format("window.sessionStorage.clear();"));
    }

}
