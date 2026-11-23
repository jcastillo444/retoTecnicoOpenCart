package co.com.opencart.questions;

import co.com.opencart.models.OrderLine;
import co.com.opencart.userinterface.CheckoutPage;
import co.com.opencart.utils.Money;
import net.serenitybdd.screenplay.Question;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class OrderSummary {

    public static final String SUB_TOTAL = "Sub-Total";
    public static final String TOTAL = "Total";

    private OrderSummary() {
    }

    public static Question<List<OrderLine>> lines() {
        return Question.about("los productos del resumen del pedido").answeredBy(actor ->
                CheckoutPage.CONFIRM_ORDER_ROWS.resolveAllFor(actor).stream()
                        .map(row -> new OrderLine(
                                row.findElement(CheckoutPage.ROW_NAME).getText().trim(),
                                Integer.parseInt(row.findElement(CheckoutPage.ROW_QUANTITY).getText().trim()),
                                Money.parse(row.findElement(CheckoutPage.ROW_UNIT_PRICE).getText())))
                        .toList());
    }

    public static Question<Map<String, BigDecimal>> totals() {
        return Question.about("los totales del resumen del pedido").answeredBy(actor -> {
            Map<String, BigDecimal> totals = new LinkedHashMap<>();
            CheckoutPage.CONFIRM_ORDER_TOTAL_ROWS.resolveAllFor(actor).forEach(row -> totals.put(
                    row.findElement(CheckoutPage.TOTAL_LABEL).getText().replace(":", "").trim(),
                    Money.parse(row.findElement(CheckoutPage.TOTAL_AMOUNT).getText())));
            return totals;
        });
    }
}
