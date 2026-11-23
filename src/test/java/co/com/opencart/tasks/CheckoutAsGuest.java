package co.com.opencart.tasks;

import co.com.opencart.models.GuestCustomer;
import co.com.opencart.userinterface.CartPage;
import co.com.opencart.userinterface.CheckoutPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class CheckoutAsGuest implements Task {

    private final GuestCustomer customer;

    public CheckoutAsGuest(GuestCustomer customer) {
        this.customer = customer;
    }

    public static CheckoutAsGuest withData(GuestCustomer customer) {
        return Tasks.instrumented(CheckoutAsGuest.class, customer);
    }

    @Override
    @Step("{0} realiza el checkout como invitado hasta el paso Confirm Order")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(CartPage.CHECKOUT_BUTTON),
                WaitUntil.the(CheckoutPage.GUEST_CHECKOUT_OPTION, isClickable())
                        .forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                Click.on(CheckoutPage.GUEST_CHECKOUT_OPTION),
                Click.on(CheckoutPage.ACCOUNT_CONTINUE_BUTTON),
                FillBillingDetails.with(customer),
                ChooseShippingMethod.ifRequired(),
                ChoosePaymentMethod.acceptingTerms()
        );
    }
}
