package selenium.testcases.layout.startpage;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import selenium.LayoutTestSetup;
import selenium.pageobjects.StartPage;

public class TestStartPageLayoutRWD extends LayoutTestSetup {

    private StartPage startPage;

    @Before
    public void setup() {
        startPage = PageFactory.initElements(getDriver(), StartPage.class);
    }

    @Test
    public void checkStartPage() throws Exception {
        startPage.open();
        checkSpecFile("StartPage");
    }
}
