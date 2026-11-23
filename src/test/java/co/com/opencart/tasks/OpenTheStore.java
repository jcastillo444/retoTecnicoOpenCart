package co.com.opencart.tasks;

import co.com.opencart.userinterface.HomePage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public final class OpenTheStore {

    private OpenTheStore() {
    }

    public static Performable homePage() {
        return Task.where("{0} abre la pagina principal de la tienda OpenCart",
                Open.browserOn().the(HomePage.class));
    }
}
