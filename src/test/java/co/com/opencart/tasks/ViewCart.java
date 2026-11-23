package co.com.opencart.tasks;

import co.com.opencart.userinterface.CartPage;
import co.com.opencart.userinterface.HomePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ViewCart implements Task {

    public static ViewCart fromTheHeader() {
        return Tasks.instrumented(ViewCart.class);
    }

    @Override
    @Step("{0} abre el carrito de compras")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(HomePage.CART_DROPDOWN_BUTTON),
                WaitUntil.the(HomePage.VIEW_CART_LINK, isClickable()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                Click.on(HomePage.VIEW_CART_LINK),
                WaitUntil.the(CartPage.CHECKOUT_BUTTON, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
    }
}
