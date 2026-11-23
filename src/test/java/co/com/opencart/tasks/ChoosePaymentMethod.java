package co.com.opencart.tasks;

import co.com.opencart.interactions.WaitForAjax;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.opencart.userinterface.CheckoutPage.*;
import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class ChoosePaymentMethod implements Task {

    public static ChoosePaymentMethod acceptingTerms() {
        return Tasks.instrumented(ChoosePaymentMethod.class);
    }

    @Override
    @Step("{0} acepta los terminos y continua con el metodo de pago")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(TERMS_AND_CONDITIONS, isClickable()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                WaitForAjax.toFinish(),
                Click.on(TERMS_AND_CONDITIONS),
                Click.on(PAYMENT_METHOD_CONTINUE_BUTTON),
                WaitUntil.the(CONFIRM_ORDER_BUTTON, isClickable()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                WaitForAjax.toFinish()
        );
    }
}
