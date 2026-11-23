package co.com.opencart.tasks;

import co.com.opencart.interactions.WaitForAjax;
import co.com.opencart.models.GuestCustomer;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.opencart.userinterface.CheckoutPage.*;
import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class FillBillingDetails implements Task {

    private final GuestCustomer customer;

    public FillBillingDetails(GuestCustomer customer) {
        this.customer = customer;
    }

    public static FillBillingDetails with(GuestCustomer customer) {
        return Tasks.instrumented(FillBillingDetails.class, customer);
    }

    @Override
    @Step("{0} diligencia los datos de facturacion del invitado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(FIRST_NAME, isVisible()).forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                Enter.theValue(customer.firstName()).into(FIRST_NAME),
                Enter.theValue(customer.lastName()).into(LAST_NAME),
                Enter.theValue(customer.email()).into(EMAIL),
                Enter.theValue(customer.telephone()).into(TELEPHONE),
                Enter.theValue(customer.company()).into(COMPANY),
                Enter.theValue(customer.address()).into(ADDRESS),
                Enter.theValue(customer.city()).into(CITY),
                Enter.theValue(customer.postcode()).into(POSTCODE),
                SelectFromOptions.byVisibleText(customer.country()).from(COUNTRY),
                WaitUntil.the(REGION_OPTION.of(customer.region()), isPresent())
                        .forNoMoreThan(MAX_WAIT_SECONDS).seconds(),
                SelectFromOptions.byVisibleText(customer.region()).from(REGION),
                WaitForAjax.toFinish(),
                Click.on(GUEST_CONTINUE_BUTTON)
        );
    }
}
