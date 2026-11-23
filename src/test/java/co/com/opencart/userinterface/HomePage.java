package co.com.opencart.userinterface;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("https://opencart.abstracta.us/")
public class HomePage extends PageObject {

    private static final String PRODUCT_CARD =
            "//div[contains(@class,'product-thumb')][.//h4/a[contains(normalize-space(.),'{0}')]]";

    public static final Target PRODUCT_NAME = Target.the("nombre del producto {0}")
            .locatedBy(PRODUCT_CARD + "//h4/a");

    public static final Target PRODUCT_PRICE = Target.the("precio del producto {0}")
            .locatedBy(PRODUCT_CARD + "//p[@class='price']");

    public static final Target ADD_TO_CART_BUTTON = Target.the("boton Add to Cart del producto {0}")
            .locatedBy(PRODUCT_CARD + "//button[contains(@onclick,'cart.add')]");

    public static final Target CART_TOTAL = Target.the("total del carrito")
            .located(By.id("cart-total"));

    public static final Target CART_DROPDOWN_BUTTON = Target.the("boton del carrito")
            .located(By.xpath("//div[@id='cart']/button"));

    public static final Target VIEW_CART_LINK = Target.the("enlace View Cart")
            .located(By.xpath("//div[@id='cart']//a[contains(@href,'route=checkout/cart')]"));
}
