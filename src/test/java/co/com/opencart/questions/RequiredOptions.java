package co.com.opencart.questions;

import co.com.opencart.userinterface.ProductPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

import java.util.List;

public final class RequiredOptions {

    private RequiredOptions() {
    }

    public static Question<List<String>> names() {
        return Question.about("las opciones obligatorias del producto")
                .answeredBy(actor -> textsOf(ProductPage.REQUIRED_OPTION_LABELS, actor));
    }

    public static Question<List<String>> pendingMessages() {
        return Question.about("los mensajes de opciones obligatorias pendientes")
                .answeredBy(actor -> textsOf(ProductPage.REQUIRED_OPTION_ERRORS, actor));
    }

    private static List<String> textsOf(Target target, Actor actor) {
        return target.resolveAllFor(actor).stream()
                .map(element -> element.getText().trim())
                .toList();
    }
}
