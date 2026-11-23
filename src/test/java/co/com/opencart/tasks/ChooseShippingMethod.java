package co.com.opencart.tasks;

import co.com.opencart.interactions.WaitForAjax;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.opencart.userinterface.CheckoutPage.NEXT_STEP_AFTER_BILLING;
import static co.com.opencart.userinterface.CheckoutPage.SHIPPING_METHOD_CONTINUE_BUTTON;
import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ChooseShippingMethod implements Task {

    public static ChooseShippingMethod ifRequired() {
        return Tasks.instrumented(ChooseShippingMethod.class);
    }

    @Override
    @Step("{0} elige el metodo de envio si el carrito lo requiere")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(NEXT_STEP_AFTER_BILLING, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                WaitForAjax.toFinish(),
                Check.whether(shippingStepIsDisplayed())
                        .andIfSo(Click.on(SHIPPING_METHOD_CONTINUE_BUTTON))
        );
    }

    private static Question<Boolean> shippingStepIsDisplayed() {
        return Question.about("si se muestra el paso Delivery Method")
                .answeredBy(actor -> SHIPPING_METHOD_CONTINUE_BUTTON.resolveFor(actor).isCurrentlyVisible());
    }
}
