package co.com.opencart.tasks;

import co.com.opencart.userinterface.HomePage;
import co.com.opencart.userinterface.ProductPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.opencart.utils.MemoryKeys.ATTEMPTED_PRODUCT;
import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class TryToAddProductToCart implements Task {

    private final String productName;

    public TryToAddProductToCart(String productName) {
        this.productName = productName;
    }

    public static TryToAddProductToCart fromHomePage(String productName) {
        return Tasks.instrumented(TryToAddProductToCart.class, productName);
    }

    @Override
    @Step("{0} intenta agregar el producto '#productName' al carrito desde la pagina principal")
    public <T extends Actor> void performAs(T actor) {
        actor.remember(ATTEMPTED_PRODUCT, productName);
        actor.attemptsTo(
                Scroll.to(HomePage.ADD_TO_CART_BUTTON.of(productName)),
                Click.on(HomePage.ADD_TO_CART_BUTTON.of(productName)),
                WaitUntil.the(ProductPage.ADD_TO_CART_BUTTON, isClickable())
                        .forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                Click.on(ProductPage.ADD_TO_CART_BUTTON),
                WaitUntil.the(ProductPage.REQUIRED_OPTION_ERRORS, isVisible())
                        .forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
    }
}
