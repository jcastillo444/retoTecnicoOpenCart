# Reto técnico automatizado - OpenCart

Pruebas funcionales automatizadas del proceso de compra como invitado en la tienda demo
[opencart.abstracta.us](https://opencart.abstracta.us/), con el patrón **Screenplay** y **Serenity BDD + Cucumber** en Java.

La rama principal del repositorio es **main**.
Los hallazgos de la prueba están en [Conclusiones.md](Conclusiones.md).

## Flujo automatizado

1. Agregar productos al carrito desde la página principal.
2. Visualizar el carrito y validar productos y precios.
3. Hacer el checkout como invitado (Guest Checkout).
4. Validar productos, precios y totales en el paso *Confirm Order*.
5. Confirmar el pedido y validar el mensaje **"Your order has been placed!"**.

## Escenarios

| Escenario | Etiquetas | Ejemplos | Qué valida |
|---|---|---|---|
| Compra exitosa como invitado | `@compra-exitosa` | MacBook + iPhone; iPhone | Flujo completo: carrito, precios, totales y mensaje final |
| Hallazgo: producto con opciones obligatorias | `@hallazgo` `@hallazgo-opciones-obligatorias` | Apple Cinema 30"; Canon EOS 5D | El producto no se agrega desde la página principal; deja el motivo (opciones obligatorias) en el informe |
| Hallazgo: impuestos sin dirección de envío | `@hallazgo` `@hallazgo-impuestos` | MacBook solo | Se cobran impuestos del Reino Unido a una dirección de Colombia cuando el carrito no requiere envío |

Los escenarios de hallazgo validan el comportamiento **actual** del sitio: en verde significa que el hallazgo sigue
ocurriendo. Si la tienda cambia ese comportamiento, el escenario falla y avisa. El detalle de cada hallazgo está en
[Conclusiones.md](Conclusiones.md).

## Versiones

| Herramienta | Versión |
|---|---|
| Java | 17 |
| Gradle (wrapper) | 8.14.3 |
| Serenity BDD (librería y plugin de Gradle) | 4.3.2 |
| Cucumber | 7.31.0 (alineada con Serenity) |
| JUnit Platform | 5.13.0 (alineada con Serenity) |
| Selenium | 4.38.0 (incluida en Serenity) |
| Navegador | Google Chrome; el driver lo descarga Selenium Manager |

## Estructura

```
src/test/java/co/com/opencart
├── models           Datos: Product, OrderLine, GuestCustomer
├── questions        Lo que se consulta en pantalla para validar (carrito, resumen, mensajes)
├── runners          Runner de Cucumber (JUnit Platform Suite)
├── stepdefinitions  Puente entre el feature en español y las tareas
├── tasks            Acciones de negocio del actor (agregar producto, checkout, confirmar)
├── userinterface    Localizadores (Targets) por página
└── utils            Utilidades: precios, llaves de memoria y tiempos de espera
src/test/resources
├── features         compra_invitado.feature (Gherkin en español)
└── serenity.conf    Configuración de Serenity y del navegador
```

Convención: el feature está en español (negocio) y el código en inglés. Las descripciones de los Targets y de los pasos
van en español porque aparecen en el informe.

## Ejecución

```bash
# Toda la suite (abre Chrome)
gradlew clean test

# Sin ventana del navegador (por ejemplo en CI)
gradlew clean test -Denvironment=headless

# Solo los escenarios de compra o solo los hallazgos
gradlew clean test -Dcucumber.filter.tags="@compra-exitosa"
gradlew clean test -Dcucumber.filter.tags="@hallazgo"

# Un hallazgo en particular
gradlew clean test -Dcucumber.filter.tags="@hallazgo-opciones-obligatorias"
gradlew clean test -Dcucumber.filter.tags="@hallazgo-impuestos"
```

En Windows con PowerShell se usa `.\gradlew`.

El informe queda en `target/site/serenity/index.html`. Si algún escenario falla, el build también falla
(tarea `checkOutcomes`), así que el resultado de Gradle coincide con el del informe.

El repositorio incluye el informe de la última ejecución como evidencia (5 casos, todos exitosos), con una captura por
paso. Cada ejecución borra y regenera el informe, así que solo conviene volver a subirlo cuando se quiera dejar
evidencia de una ejecución nueva.

## Documentación oficial

- Serenity BDD: https://serenity-bdd.github.io/docs/
- Cucumber: https://cucumber.io/docs/cucumber/
- Gradle: https://docs.gradle.org/current/userguide/userguide.html
