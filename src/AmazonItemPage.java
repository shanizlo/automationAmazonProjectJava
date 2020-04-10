import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AmazonItemPage extends AmazonPage {
    public AmazonItemPage(AmazonPage parent) {
        super(parent);
    }

    @Override
    public void load() {
        waitForPageLoad();
    }

    public String getStockStatus() {
        WebElement availabilityElement = webDriver.findElement(By.id("availability"));
        return availabilityElement.getText();
    }

    public ItemPrice getPrice() {
        WebElement priceElement = webDriver.findElement(By.id("priceblock_ourprice"));
        return new ItemPrice(priceElement.getText());
    }
}
