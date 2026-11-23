package co.com.opencart.questions;

import co.com.opencart.models.Product;
import co.com.opencart.userinterface.HomePage;
import co.com.opencart.utils.Money;
import net.serenitybdd.screenplay.Question;

public final class PublishedProduct {

    private PublishedProduct() {
    }

    public static Question<Product> named(String productName) {
        return Question.about("el producto publicado " + productName).answeredBy(actor -> {
            String name = HomePage.PRODUCT_NAME.of(productName).resolveFor(actor).getText().trim();
            String priceText = HomePage.PRODUCT_PRICE.of(productName).resolveFor(actor).getText();
            return new Product(name, Money.parse(priceText), Money.parseAfter(priceText, "Ex Tax:"));
        });
    }
}
