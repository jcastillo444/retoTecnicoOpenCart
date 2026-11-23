package co.com.opencart.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class CheckoutPage {

    public static final Target GUEST_CHECKOUT_OPTION = Target.the("opcion Guest Checkout")
            .located(By.xpath("//input[@name='account' and @value='guest']"));
    public static final Target ACCOUNT_CONTINUE_BUTTON = Target.the("boton continuar de Checkout Options")
            .located(By.id("button-account"));

    public static final Target FIRST_NAME = Target.the("campo nombre").located(By.id("input-payment-firstname"));
    public static final Target LAST_NAME = Target.the("campo apellido").located(By.id("input-payment-lastname"));
    public static final Target EMAIL = Target.the("campo correo").located(By.id("input-payment-email"));
    public static final Target TELEPHONE = Target.the("campo telefono").located(By.id("input-payment-telephone"));
    public static final Target COMPANY = Target.the("campo empresa").located(By.id("input-payment-company"));
    public static final Target ADDRESS = Target.the("campo direccion").located(By.id("input-payment-address-1"));
    public static final Target CITY = Target.the("campo ciudad").located(By.id("input-payment-city"));
    public static final Target POSTCODE = Target.the("campo codigo postal").located(By.id("input-payment-postcode"));
    public static final Target COUNTRY = Target.the("lista pais").located(By.id("input-payment-country"));
    public static final Target REGION = Target.the("lista departamento").located(By.id("input-payment-zone"));
    public static final Target REGION_OPTION = Target.the("departamento {0}")
            .locatedBy("//select[@id='input-payment-zone']/option[normalize-space(.)='{0}']");
    public static final Target GUEST_CONTINUE_BUTTON = Target.the("boton continuar de Billing Details")
            .located(By.id("button-guest"));

    public static final Target NEXT_STEP_AFTER_BILLING = Target.the("siguiente paso despues de Billing Details")
            .located(By.xpath("//input[@id='button-shipping-method' or @id='button-payment-method']"));
    public static final Target SHIPPING_METHOD_CONTINUE_BUTTON = Target.the("boton continuar de Delivery Method")
            .located(By.id("button-shipping-method"));

    public static final Target TERMS_AND_CONDITIONS = Target.the("casilla de terminos y condiciones")
            .located(By.name("agree"));
    public static final Target PAYMENT_METHOD_CONTINUE_BUTTON = Target.the("boton continuar de Payment Method")
            .located(By.id("button-payment-method"));

    public static final Target CONFIRM_ORDER_ROWS = Target.the("productos del resumen del pedido")
            .located(By.xpath("//div[@id='collapse-checkout-confirm']//table/tbody/tr"));
    public static final Target CONFIRM_ORDER_TOTAL_ROWS = Target.the("totales del resumen del pedido")
            .located(By.xpath("//div[@id='collapse-checkout-confirm']//table/tfoot/tr"));
    public static final Target CONFIRM_ORDER_BUTTON = Target.the("boton Confirm Order")
            .located(By.id("button-confirm"));

    public static final By ROW_NAME = By.xpath("./td[1]/a");
    public static final By ROW_QUANTITY = By.xpath("./td[3]");
    public static final By ROW_UNIT_PRICE = By.xpath("./td[4]");

    public static final By TOTAL_LABEL = By.xpath("./td[1]");
    public static final By TOTAL_AMOUNT = By.xpath("./td[2]");

    private CheckoutPage() {
    }
}
