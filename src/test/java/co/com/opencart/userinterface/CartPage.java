package co.com.opencart.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class CartPage {

    public static final Target PRODUCT_ROWS = Target.the("productos del carrito")
            .located(By.xpath("//div[@id='content']//form[contains(@action,'checkout/cart/edit')]//tbody/tr"));

    public static final Target CHECKOUT_BUTTON = Target.the("boton Checkout del carrito")
            .located(By.xpath("//div[@id='content']//a[contains(@href,'checkout/checkout') and contains(@class,'btn-primary')]"));

    public static final By ROW_NAME = By.xpath("./td[2]/a");
    public static final By ROW_QUANTITY = By.xpath("./td[4]//input");
    public static final By ROW_UNIT_PRICE = By.xpath("./td[5]");

    private CartPage() {
    }
}
