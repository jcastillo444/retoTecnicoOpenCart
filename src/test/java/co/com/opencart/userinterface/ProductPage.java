package co.com.opencart.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class ProductPage {

    public static final Target TITLE = Target.the("titulo de la pagina del producto")
            .located(By.xpath("//div[@id='content']//h1"));

    public static final Target ADD_TO_CART_BUTTON = Target.the("boton Add to Cart de la pagina del producto")
            .located(By.id("button-cart"));

    public static final Target REQUIRED_OPTION_LABELS = Target.the("nombres de las opciones obligatorias")
            .located(By.xpath("//div[@id='product']//div[contains(@class,'form-group') and contains(@class,'required')]/label"));

    public static final Target REQUIRED_OPTION_ERRORS = Target.the("mensajes de opciones obligatorias")
            .located(By.xpath("//div[@id='product']//div[contains(@class,'text-danger')]"));

    private ProductPage() {
    }
}
