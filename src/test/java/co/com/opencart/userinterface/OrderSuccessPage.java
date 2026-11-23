package co.com.opencart.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class OrderSuccessPage {

    public static final Target TITLE = Target.the("titulo de la pagina de pedido realizado")
            .located(By.xpath("//div[@id='content']/h1"));

    public static final Target SUCCESS_BREADCRUMB = Target.the("ruta de navegacion Success")
            .located(By.xpath("//ul[@class='breadcrumb']//a[contains(@href,'checkout/success')]"));

    private OrderSuccessPage() {
    }
}
