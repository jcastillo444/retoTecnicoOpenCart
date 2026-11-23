package co.com.opencart.questions;

import co.com.opencart.models.OrderLine;
import co.com.opencart.userinterface.CartPage;
import co.com.opencart.utils.Money;
import net.serenitybdd.screenplay.Question;

import java.util.List;

public final class CartContent {

    private CartContent() {
    }

    public static Question<List<OrderLine>> lines() {
        return Question.about("los productos del carrito").answeredBy(actor ->
                CartPage.PRODUCT_ROWS.resolveAllFor(actor).stream()
                        .map(row -> new OrderLine(
                                row.findElement(CartPage.ROW_NAME).getText().trim(),
                                Integer.parseInt(row.findElement(CartPage.ROW_QUANTITY).getAttribute("value").trim()),
                                Money.parse(row.findElement(CartPage.ROW_UNIT_PRICE).getText())))
                        .toList());
    }
}
