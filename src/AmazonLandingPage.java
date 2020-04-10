import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonLandingPage extends AmazonPage {

    public AmazonLandingPage(AmazonCountry country, WebDriver webDriver) {
        super(webDriver, getUrlForCountry(country));
    }

    @Override
    public void load() {
        webDriver.get(baseUrl);
        waitForPageLoad();
    }

    public AmazonSearchResultsPage search(String term) {
        WebElement searchField = webDriver.findElement(By.id("twotabsearchtextbox"));

        searchField.sendKeys(term);
        searchField.submit();

        return new AmazonSearchResultsPage(this);
    }

    private static String getUrlForCountry(AmazonCountry country) {
        switch (country) {
            case US:
                return "http://www.amazon.com";
            case UK:
                return "http://www.amazon.co.uk";
            default:
                throw new IllegalArgumentException();
        }
    }
}
