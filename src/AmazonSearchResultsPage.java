import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonSearchResultsPage extends AmazonPage {
    public AmazonSearchResultsPage(AmazonPage parent) {
        super(parent);
    }

    @Override
    public void load() {
        waitForPageLoad();
    }

    public int getTotalResults() {
        WebElement resultsField = webDriver.findElement(By.id("s-result-count"));
        String resultsText = resultsField.getText();

        Pattern resultsPattern = Pattern.compile("\\s([0-9,]+)\\s"); // TODO: doesn't work for 16 or less
        Matcher resultsMatch = resultsPattern.matcher(resultsText);
        if (resultsMatch.find()) {
            String value = resultsMatch.group(0);
            value = value.trim();
            value = value.replace(",", "");
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    public AmazonItemPage getItem(int index) throws Exception {
        WebElement resultsList = webDriver.findElement(By.id("s-results-list-atf"));
        List<WebElement> results = resultsList.findElements(By.tagName("li"));
        WebElement result = results.get(index);
        List<WebElement> links = result.findElements(By.tagName("a"));

        for (WebElement link : links) {
            try {
                WebElement title = link.findElement(By.tagName("h2"));
                title.click();
                return new AmazonItemPage(this);
            } catch (NoSuchElementException e) {
                continue;
            }
        }

        throw new Exception("Failed to get item");
    }
}
