package co.com.opencart.questions;

import co.com.opencart.userinterface.OrderSuccessPage;
import co.com.opencart.userinterface.ProductPage;
import net.serenitybdd.screenplay.Question;

public final class TheTitle {

    private TheTitle() {
    }

    public static Question<String> ofTheProductPage() {
        return Question.about("el titulo de la pagina del producto")
                .answeredBy(actor -> ProductPage.TITLE.resolveFor(actor).getText().trim());
    }

    public static Question<String> ofTheOrderSuccessPage() {
        return Question.about("el mensaje de pedido realizado")
                .answeredBy(actor -> OrderSuccessPage.TITLE.resolveFor(actor).getText().trim());
    }
}
