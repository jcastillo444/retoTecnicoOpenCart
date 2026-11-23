package co.com.opencart.tasks;

import co.com.opencart.userinterface.CheckoutPage;
import co.com.opencart.userinterface.OrderSuccessPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;

public class ConfirmOrder implements Task {

    public static ConfirmOrder now() {
        return Tasks.instrumented(ConfirmOrder.class);
    }

    @Override
    @Step("{0} confirma el pedido")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(CheckoutPage.CONFIRM_ORDER_BUTTON),
                WaitUntil.the(OrderSuccessPage.SUCCESS_BREADCRUMB, isPresent())
                        .forNoMoreThan(MAX_WAIT_SECONDS).seconds()
        );
    }
}
