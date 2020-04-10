import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Assignment {
    private static class AssignmentResult {
        private int searchResults;
        private ItemPrice price;
        private String stockStatus;

        private AssignmentResult(int searchResults, ItemPrice price, String stockStatus) {
            this.searchResults = searchResults;
            this.price = price;
            this.stockStatus = stockStatus;
        }
    }

    public static void main(String[] args) throws Exception {
        WebDriver webDriver = new ChromeDriver();

        AssignmentResult usResult = getResultBySite(webDriver, AmazonCountry.US);
        AssignmentResult ukResult = getResultBySite(webDriver, AmazonCountry.UK);

        double usPriceInIls = CurrencyConverter.convert(usResult.price.getAmount(),
                usResult.price.getCurrency(), Currency.ILS);
        double ukPriceInIls = CurrencyConverter.convert(ukResult.price.getAmount(),
                ukResult.price.getCurrency(), Currency.ILS);
        double ukPriceInUsd = CurrencyConverter.convert(ukResult.price.getAmount(),
                ukResult.price.getCurrency(), Currency.USD);

        String cheaper, cheaperStockStatus;
        if (usPriceInIls < ukPriceInIls) {
            cheaper = "amazon.com";
            cheaperStockStatus = usResult.stockStatus;
        } else {
            cheaper = "amazon.co.uk";
            cheaperStockStatus = ukResult.stockStatus;
        }

        StringBuilder textToSend = new StringBuilder();
        textToSend.append("amazon.com: " + usResult.searchResults + " results, "
                + "price: " + usResult.price + " " + String.format("%.2f", usPriceInIls) + " ILS\n");
        textToSend.append("amazon.co.uk: " + ukResult.searchResults + " results, "
                + "price: " + ukResult.price + " (" + String.format("%.2f", ukPriceInUsd) + " $) "
                + String.format("%.2f", ukPriceInIls) + " ILS\n");
        textToSend.append("You should buy from " + cheaper + ", which is cheaper! ");
        textToSend.append("Stock status: " + cheaperStockStatus);

        MailSender mailSender = new MailSender();
        mailSender.send("shanizlotnik@gmail.com","shanizlotnik@gmail.com",
                "home assignment", textToSend.toString());
    }

    private static AssignmentResult getResultBySite(WebDriver webDriver, AmazonCountry country) throws Exception {
        AmazonLandingPage landingPage = new AmazonLandingPage(country, webDriver);
        landingPage.load();
        AmazonSearchResultsPage amazonEchoResults = landingPage.search("Amazon Echo");
        int searchResults = amazonEchoResults.getTotalResults();
        AmazonItemPage itemPage = amazonEchoResults.getItem(0); // I assume the right result is always first
        return new AssignmentResult(searchResults, itemPage.getPrice(), itemPage.getStockStatus());
    }
}
