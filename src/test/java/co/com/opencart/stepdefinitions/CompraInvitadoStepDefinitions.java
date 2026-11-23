package co.com.opencart.stepdefinitions;

import co.com.opencart.models.GuestCustomer;
import co.com.opencart.models.OrderLine;
import co.com.opencart.models.Product;
import co.com.opencart.questions.CartContent;
import co.com.opencart.questions.ItemsInCart;
import co.com.opencart.questions.OrderSummary;
import co.com.opencart.questions.RequiredOptions;
import co.com.opencart.questions.TheTitle;
import co.com.opencart.tasks.AddProductToCart;
import co.com.opencart.tasks.CheckoutAsGuest;
import co.com.opencart.tasks.ConfirmOrder;
import co.com.opencart.tasks.OpenTheStore;
import co.com.opencart.tasks.TryToAddProductToCart;
import co.com.opencart.tasks.ViewCart;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static co.com.opencart.utils.MemoryKeys.ATTEMPTED_PRODUCT;
import static co.com.opencart.utils.MemoryKeys.SELECTED_PRODUCTS;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class CompraInvitadoStepDefinitions {

    @Dado("que {actor} se dirige a la pagina de OpenCart")
    public void seDirigeALaPaginaDeOpenCart(Actor actor) {
        actor.wasAbleTo(OpenTheStore.homePage());
    }

    @Cuando("agrega los productos {string} al carrito")
    public void agregaLosProductosAlCarrito(String productos) {
        Actor actor = theActorInTheSpotlight();
        for (String producto : splitList(productos)) {
            actor.attemptsTo(AddProductToCart.fromHomePage(producto));
        }
    }

    @Y("visualiza el carrito de compras")
    public void visualizaElCarritoDeCompras() {
        theActorInTheSpotlight().attemptsTo(ViewCart.fromTheHeader());
    }

    @Entonces("el carrito deberia contener los productos seleccionados con el precio publicado")
    public void elCarritoDeberiaContenerLosProductosSeleccionados() {
        OrderLine[] expected = selectedProducts().stream()
                .map(product -> new OrderLine(product.name(), 1, product.price()))
                .toArray(OrderLine[]::new);

        theActorInTheSpotlight().should(
                seeThat("los productos del carrito y su precio publicado en la pagina principal",
                        CartContent.lines(), containsInAnyOrder(expected))
        );
    }

    @Cuando("realiza el checkout como invitado")
    public void realizaElCheckoutComoInvitado() {
        theActorInTheSpotlight().attemptsTo(CheckoutAsGuest.withData(GuestCustomer.withDefaultData()));
    }

    @Entonces("el resumen del pedido deberia mostrar los productos seleccionados con su precio sin impuestos")
    public void elResumenDelPedidoDeberiaMostrarLosProductosSeleccionados() {
        OrderLine[] expected = selectedProducts().stream()
                .map(product -> new OrderLine(product.name(), 1, product.priceExTax()))
                .toArray(OrderLine[]::new);

        theActorInTheSpotlight().should(
                seeThat("los productos del resumen del pedido y su precio sin impuestos",
                        OrderSummary.lines(), containsInAnyOrder(expected))
        );
    }

    @Y("el total del pedido deberia corresponder al Sub-Total mas los cargos adicionales")
    public void elTotalDelPedidoDeberiaCorresponder() {
        Actor actor = theActorInTheSpotlight();
        Map<String, BigDecimal> totals = actor.asksFor(OrderSummary.totals());

        BigDecimal productsSum = actor.asksFor(OrderSummary.lines()).stream()
                .map(line -> line.unitPrice().multiply(BigDecimal.valueOf(line.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expectedTotal = totals.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(OrderSummary.TOTAL))
                .map(Map.Entry::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Serenity.recordReportData().withTitle("Totales del resumen del pedido").andContents(totals.toString());

        actor.should(
                seeThat("el Sub-Total (suma de los productos)",
                        Question.about("Sub-Total").answeredBy(a -> totals.get(OrderSummary.SUB_TOTAL)),
                        comparesEqualTo(productsSum)),
                seeThat("el Total (Sub-Total mas envio e impuestos)",
                        Question.about("Total").answeredBy(a -> totals.get(OrderSummary.TOTAL)),
                        comparesEqualTo(expectedTotal))
        );
    }

    @Cuando("confirma el pedido")
    public void confirmaElPedido() {
        theActorInTheSpotlight().attemptsTo(ConfirmOrder.now());
    }

    @Entonces("deberia ver el mensaje {string}")
    public void deberiaVerElMensaje(String mensaje) {
        theActorInTheSpotlight().should(
                seeThat("el mensaje de pedido realizado", TheTitle.ofTheOrderSuccessPage(), equalTo(mensaje))
        );
    }

    @Cuando("intenta agregar el producto {string} al carrito desde la pagina principal")
    public void intentaAgregarElProductoAlCarrito(String producto) {
        theActorInTheSpotlight().attemptsTo(TryToAddProductToCart.fromHomePage(producto));
    }

    @Entonces("la tienda no agrega el producto y lo redirige a la pagina del producto")
    public void laTiendaNoAgregaElProductoYLoRedirige() {
        Actor actor = theActorInTheSpotlight();
        String producto = actor.recall(ATTEMPTED_PRODUCT);

        actor.should(
                seeThat("la cantidad de productos en el carrito", ItemsInCart.count(), equalTo(0)),
                seeThat("la tienda redirige a la pagina del producto", TheTitle.ofTheProductPage(),
                        containsString(producto))
        );
    }

    @Y("el motivo es que el producto tiene las opciones obligatorias {string}")
    public void elMotivoEsQueTieneLasOpcionesObligatorias(String opciones) {
        Actor actor = theActorInTheSpotlight();
        List<String> expectedOptions = splitList(opciones);
        List<String> shownOptions = actor.asksFor(RequiredOptions.names());
        List<String> pendingMessages = actor.asksFor(RequiredOptions.pendingMessages());
        String productTitle = actor.asksFor(TheTitle.ofTheProductPage());

        Serenity.recordReportData()
                .withTitle("Motivo: el producto no se agrega al carrito")
                .andContents(String.format(
                        "El producto '%s' no se puede agregar al carrito desde la pagina principal porque tiene "
                                + "%d opcion(es) obligatoria(s): %s.%n"
                                + "El boton 'Add to Cart' de la pagina principal no permite elegirlas, por eso la tienda "
                                + "no agrega el producto y redirige a la pagina del producto.%n"
                                + "En la pagina del producto algunas opciones traen un valor por defecto; la tienda pide "
                                + "completar las demas: %s",
                        productTitle, shownOptions.size(), String.join(", ", shownOptions),
                        String.join(" | ", pendingMessages)));

        actor.should(
                seeThat("las opciones obligatorias del producto", RequiredOptions.names(),
                        containsInAnyOrder(expectedOptions.toArray(String[]::new)))
        );
    }

    @Y("la tienda pide completar las opciones {string}")
    public void laTiendaPideCompletarLasOpciones(String opciones) {
        String[] expectedMessages = splitList(opciones).stream()
                .map(option -> option + " required!")
                .toArray(String[]::new);

        theActorInTheSpotlight().should(
                seeThat("los mensajes de opciones pendientes que muestra la tienda",
                        RequiredOptions.pendingMessages(), containsInAnyOrder(expectedMessages))
        );
    }

    @Entonces("el resumen del pedido cobra los impuestos del Reino Unido aunque la direccion sea de Colombia")
    public void elResumenDelPedidoCobraLosImpuestosDelReinoUnido() {
        Actor actor = theActorInTheSpotlight();
        List<Product> products = selectedProducts();
        OrderLine[] expected = products.stream()
                .map(product -> new OrderLine(product.name(), 1, product.price()))
                .toArray(OrderLine[]::new);
        Map<String, BigDecimal> totals = actor.asksFor(OrderSummary.totals());
        List<String> taxes = totals.keySet().stream()
                .filter(label -> !label.equals(OrderSummary.SUB_TOTAL) && !label.equals(OrderSummary.TOTAL))
                .toList();

        Serenity.recordReportData()
                .withTitle("Hallazgo: impuestos del Reino Unido a una direccion de Colombia")
                .andContents(String.format(
                        "Productos: %s. Ninguno requiere envio, por eso el checkout no pide direccion de envio.%n"
                                + "OpenCart calcula los impuestos con la direccion de envio; al no haberla, usa la "
                                + "direccion de la tienda (Reino Unido) y cobra sus impuestos aunque la direccion de "
                                + "facturacion sea de Colombia.%n"
                                + "Totales del resumen: %s%n"
                                + "Cuando el carrito incluye un producto que requiere envio (por ejemplo iPhone), los "
                                + "mismos productos se cobran sin impuestos (precio 'Ex Tax').",
                        products.stream().map(Product::name).toList(), totals));

        actor.should(
                seeThat("los productos del resumen se cobran con el precio con impuestos",
                        OrderSummary.lines(), containsInAnyOrder(expected)),
                seeThat("los cargos de impuestos del resumen",
                        Question.about("impuestos").answeredBy(a -> taxes), not(empty()))
        );
    }

    private static List<Product> selectedProducts() {
        return theActorInTheSpotlight().recall(SELECTED_PRODUCTS);
    }

    private static List<String> splitList(String values) {
        return Arrays.stream(values.split(","))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .toList();
    }
}
