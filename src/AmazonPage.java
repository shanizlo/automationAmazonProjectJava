import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AmazonPage {
    protected WebDriver webDriver;
    protected String baseUrl;

    public AmazonPage(WebDriver webDriver, String baseUrl) {
        this.webDriver = webDriver;
        this.baseUrl = baseUrl;
    }

    public AmazonPage(AmazonPage parent) {
        this(parent.webDriver, parent.baseUrl);
    }

    public abstract void load();

    protected void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete");
            }
        };

        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.until(pageLoadCondition);
    }
}
