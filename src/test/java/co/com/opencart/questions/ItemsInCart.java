package co.com.opencart.questions;

import co.com.opencart.userinterface.HomePage;
import net.serenitybdd.screenplay.Question;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ItemsInCart {

    private static final Pattern ITEMS = Pattern.compile("(\\d+)\\s+item");

    private ItemsInCart() {
    }

    public static Question<Integer> count() {
        return Question.about("la cantidad de productos en el carrito").answeredBy(actor -> {
            String text = HomePage.CART_TOTAL.resolveFor(actor).getText();
            Matcher matcher = ITEMS.matcher(text);
            if (!matcher.find()) {
                throw new IllegalStateException("No se pudo leer la cantidad del carrito en: '" + text + "'");
            }
            return Integer.parseInt(matcher.group(1));
        });
    }
}
