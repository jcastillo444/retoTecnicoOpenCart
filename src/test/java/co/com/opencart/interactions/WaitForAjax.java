package co.com.opencart.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static co.com.opencart.utils.Timeouts.MAX_WAIT_SECONDS;

public class WaitForAjax implements Interaction {

    private static final String NO_PENDING_REQUESTS = "return window.jQuery ? jQuery.active === 0 : true;";

    public static WaitForAjax toFinish() {
        return Tasks.instrumented(WaitForAjax.class);
    }

    @Override
    @Step("{0} espera a que terminen las peticiones AJAX de la pagina")
    public <T extends Actor> void performAs(T actor) {
        new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(MAX_WAIT_SECONDS))
                .until(driver -> Boolean.TRUE.equals(((JavascriptExecutor) driver).executeScript(NO_PENDING_REQUESTS)));
    }
}
