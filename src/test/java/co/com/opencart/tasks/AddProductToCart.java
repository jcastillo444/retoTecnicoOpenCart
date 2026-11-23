package co.com.opencart.tasks;

import co.com.opencart.models.Product;
import co.com.opencart.questions.ItemsInCart;
import co.com.opencart.questions.PublishedProduct;
import co.com.opencart.userinterface.HomePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.ArrayList;
import java.util.List;

import static co.com.opencart.utils.MemoryKeys.SELECTED_PRODUCTS;
import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;

public class AddProductToCart implements Task {

    private final String productName;

    public AddProductToCart(String productName) {
        this.productName = productName;
    }

    public static AddProductToCart fromHomePage(String productName) {
        return Tasks.instrumented(AddProductToCart.class, productName);
    }

    @Override
    @Step("{0} agrega el producto '#productName' al carrito desde la pagina principal")
    public <T extends Actor> void performAs(T actor) {
        int itemsBefore = actor.asksFor(ItemsInCart.count());
        Product product = actor.asksFor(PublishedProduct.named(productName));

        actor.attemptsTo(
                Scroll.to(HomePage.ADD_TO_CART_BUTTON.of(productName)),
                Click.on(HomePage.ADD_TO_CART_BUTTON.of(productName)),
                WaitUntil.the(HomePage.CART_TOTAL, containsText((itemsBefore + 1) + " item(s)"))
                        .forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );

        List<Product> selected = new ArrayList<>();
        List<Product> previous = actor.recall(SELECTED_PRODUCTS);
        if (previous != null) {
            selected.addAll(previous);
        }
        selected.add(product);
        actor.remember(SELECTED_PRODUCTS, selected);
    }
}
