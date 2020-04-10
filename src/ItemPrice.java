import org.openqa.selenium.InvalidArgumentException;

public class ItemPrice {
    private double amount;
    private Currency currency;

    public ItemPrice(String priceText) {
        if (priceText == null || priceText.length() == 0)
            throw new InvalidArgumentException("Invalid price: " + priceText);

        char currencySymbol = priceText.charAt(0);
        switch (currencySymbol) {
            case '$':
                currency = Currency.USD;
                amount = Double.parseDouble(priceText.substring(1));
                break;
            case '£':
                currency = Currency.GBP;
                amount = Double.parseDouble(priceText.substring(1));
                break;
            default:
                currency = Currency.ILS;
                amount = Double.parseDouble(priceText);
        }
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        char currencySymbol;
        switch (currency) {

            case USD:
                currencySymbol = '$';
                break;
            case GBP:
                currencySymbol = '£';
                break;
            case ILS:
                currencySymbol = '₪';
                break;
            default:
                currencySymbol = '?';
        }

        return String.format("%.2f", amount) + ' ' + Character.toString(currencySymbol);
    }
}
