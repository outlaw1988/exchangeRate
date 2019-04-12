import java.io.IOException;
import java.math.BigDecimal;

public class Main {

    private static Exchange exchange = new Exchange();

    private static BigDecimal averageRateUSD;
    private static BigDecimal averageRateEUR;
    private static BigDecimal averageRateGBP;
    private static BigDecimal averageRateCHF;

    private static BigDecimal sellingRateUSD;
    private static BigDecimal sellingRateEUR;
    private static BigDecimal sellingRateGBP;
    private static BigDecimal sellingRateCHF;

    private static BigDecimal sellingRateUSDmonthAgo;
    private static BigDecimal sellingRateEURmonthAgo;
    private static BigDecimal sellingRateGBPmonthAgo;
    private static BigDecimal sellingRateCHFmonthAgo;

    private static BigDecimal buyingRateUSD;
    private static BigDecimal buyingRateEUR;
    private static BigDecimal buyingRateGBP;
    private static BigDecimal buyingRateCHF;

    public static void main(String[] args) {
        initializeRates();

        System.out.println("Current average rate:");
        System.out.println("---------------------------------------");
        System.out.printf("%s %10s %10s %10s", "USD", "EUR", "GBP", "CHF");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.format("%s %10s %10s %10s",
                averageRateUSD.setScale(2, BigDecimal.ROUND_HALF_UP),
                averageRateEUR.setScale(2, BigDecimal.ROUND_HALF_UP),
                averageRateGBP.setScale(2, BigDecimal.ROUND_HALF_UP),
                averageRateCHF.setScale(2, BigDecimal.ROUND_HALF_UP)
        );
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();

        System.out.println("Current average rate 100 PLN:");
        System.out.println("---------------------------------------");
        System.out.printf("%s %10s %10s %10s", "USD", "EUR", "GBP", "CHF");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.format("%s %10s %10s %10s",
                exchange.hundredPLNtoCurrency(averageRateUSD),
                exchange.hundredPLNtoCurrency(averageRateEUR),
                exchange.hundredPLNtoCurrency(averageRateGBP),
                exchange.hundredPLNtoCurrency(averageRateCHF)
        );
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();

        System.out.println("Current selling rate 100 PLN:");
        System.out.println("---------------------------------------");
        System.out.printf("%s %10s %10s %10s", "USD", "EUR", "GBP", "CHF");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.format("%s %10s %10s %10s",
                exchange.hundredPLNtoCurrency(sellingRateUSD),
                exchange.hundredPLNtoCurrency(sellingRateEUR),
                exchange.hundredPLNtoCurrency(sellingRateGBP),
                exchange.hundredPLNtoCurrency(sellingRateCHF)
        );
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();

        BigDecimal diffUSD = exchange.hundredPLNtoCurrency(sellingRateUSDmonthAgo).multiply(buyingRateUSD)
                            .subtract(new BigDecimal(100));
        BigDecimal diffEUR = exchange.hundredPLNtoCurrency(sellingRateEURmonthAgo).multiply(buyingRateEUR)
                            .subtract(new BigDecimal(100));
        BigDecimal diffGBP = exchange.hundredPLNtoCurrency(sellingRateGBPmonthAgo).multiply(buyingRateGBP)
                            .subtract(new BigDecimal(100));
        BigDecimal diffCHF = exchange.hundredPLNtoCurrency(sellingRateCHFmonthAgo).multiply(buyingRateCHF)
                            .subtract(new BigDecimal(100));

        System.out.println("Buying currency for 100 PLN month ago, selling now, balance in PLN:");
        System.out.println("---------------------------------------");
        System.out.printf("%s %10s %10s %10s", "USD", "EUR", "GBP", "CHF");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.format("%s %10s %10s %10s",
                diffUSD.setScale(2, BigDecimal.ROUND_HALF_UP),
                diffEUR.setScale(2, BigDecimal.ROUND_HALF_UP),
                diffGBP.setScale(2, BigDecimal.ROUND_HALF_UP),
                diffCHF.setScale(2, BigDecimal.ROUND_HALF_UP)
        );
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
    }

    private static void initializeRates() {
        try {
            averageRateUSD = exchange.exchangeCurrency(ExchangeType.MEDIUM, "USD", 0);
            averageRateEUR = exchange.exchangeCurrency(ExchangeType.MEDIUM, "EUR", 0);
            averageRateGBP = exchange.exchangeCurrency(ExchangeType.MEDIUM, "GBP", 0);
            averageRateCHF = exchange.exchangeCurrency(ExchangeType.MEDIUM, "CHF", 0);

            sellingRateUSD = exchange.exchangeCurrency(ExchangeType.SELLING, "USD", 0);
            sellingRateEUR = exchange.exchangeCurrency(ExchangeType.SELLING, "EUR", 0);
            sellingRateGBP = exchange.exchangeCurrency(ExchangeType.SELLING, "GBP", 0);
            sellingRateCHF = exchange.exchangeCurrency(ExchangeType.SELLING, "CHF", 0);

            sellingRateUSDmonthAgo = exchange.exchangeCurrency(ExchangeType.SELLING, "USD", -30);
            sellingRateEURmonthAgo = exchange.exchangeCurrency(ExchangeType.SELLING, "EUR", -30);
            sellingRateGBPmonthAgo = exchange.exchangeCurrency(ExchangeType.SELLING, "GBP", -30);
            sellingRateCHFmonthAgo = exchange.exchangeCurrency(ExchangeType.SELLING, "CHF", -30);

            buyingRateUSD = exchange.exchangeCurrency(ExchangeType.BUYING, "USD", 0);
            buyingRateEUR = exchange.exchangeCurrency(ExchangeType.BUYING, "EUR", 0);
            buyingRateGBP = exchange.exchangeCurrency(ExchangeType.BUYING, "GBP", 0);
            buyingRateCHF = exchange.exchangeCurrency(ExchangeType.BUYING, "CHF", 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
