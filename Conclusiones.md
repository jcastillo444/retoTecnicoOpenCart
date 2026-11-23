# Hallazgos y conclusiones - Compra como invitado en OpenCart

Sitio probado: https://opencart.abstracta.us/ (tienda demo pública)
Herramientas: Serenity BDD 4.3.2 + Cucumber 7.31.0, patrón Screenplay, Java 17, Chrome.
A continuación se mostrará el resumen de los escenarios creados, en donde como compra exitosa
se dejaron 2 y el resto son hallazgos, si estos son resueltos la prueba fallaría pues está 
pensado para que se valide el comportamiento actual.

## 1. Resumen de la ejecución

| Escenario | Ejemplo | Resultado | Qué demuestra |
|---|---|---|---|
| Compra exitosa | MacBook + iPhone | ✅ Pasa | Flujo completo con envío: carrito, precios, totales y "Your order has been placed!" |
| Compra exitosa | iPhone | ✅ Pasa | Checkout de 6 pasos (con Delivery Details y Delivery Method) |
| Hallazgo 1 | Apple Cinema 30" | ✅ Pasa (valida el hallazgo) | No se agrega desde la página principal: 9 opciones obligatorias |
| Hallazgo 1 | Canon EOS 5D | ✅ Pasa (valida el hallazgo) | No se agrega desde la página principal: 1 opción obligatoria |
| Hallazgo 2 | MacBook solo | ✅ Pasa (valida el hallazgo) | Se cobra IVA del Reino Unido a una dirección de Colombia |

Los escenarios de hallazgo validan el comportamiento **actual** del sitio y dejan el motivo escrito en el informe de
Serenity (sección *Report data* de cada escenario). Si la tienda cambia ese comportamiento, el escenario falla y avisa.

Ejecución: `gradlew clean test`. Solo hallazgos: `gradlew clean test -Dcucumber.filter.tags="@hallazgo"`.

## 2. Hallazgos del sitio

### Hallazgo 1 - Productos con opciones obligatorias no se agregan al carrito desde la página principal

**Qué pasa:** al pulsar "Add to Cart" en la página principal, el Apple Cinema 30" y la Canon EOS 5D **no se agregan**.
La tienda redirige a la página del producto sin mostrar ningún mensaje en la página principal.

**Por qué:** los dos productos tienen opciones obligatorias que solo se pueden elegir en su propia página. El botón de
la página principal envía el producto sin opciones, y el servidor responde con error y una redirección:

| Producto | Opciones obligatorias | Pendientes en la página del producto |
|---|---|---|
| Apple Cinema 30" | Radio, Checkbox, Text, Select, Textarea, File, Date, Time, Date & Time (9) | Radio, Checkbox, Select, Textarea, File (5) |
| Canon EOS 5D | Select (1) | Select (1) |

En la página del Apple Cinema 30", 4 opciones vienen **prellenadas** con valores de ejemplo (Text = "test",
Date = 2011-02-20, Time = 22:25, Date & Time = 2011-02-20 22:25). Por eso la tienda solo pide completar 5.

**Impacto:**
- **En la experiencia de usuario:** el usuario cree que agregó el producto y termina en otra página sin explicación.

**Recomendación:** mostrar en la página principal un aviso ("Este producto requiere elegir opciones"), o cambiar el
botón por "Ver opciones" en los productos que las tengan.

### Hallazgo 2 - Se cobran impuestos del Reino Unido a una dirección de Colombia cuando el carrito no requiere envío

**Qué pasa:** el mismo comprador, con la misma dirección de Bogotá, paga distinto por el mismo MacBook según lo que
tenga en el carrito:

| Carrito | Precio del MacBook en *Confirm Order* | Cargos | Total |
|---|---|---|---|
| MacBook + iPhone | $500.00 | Envío $5.00 | $606.00 (con el iPhone) |
| MacBook solo | **$602.00** | **Eco Tax $2.00 + VAT 20% $100.00** | **$602.00** |

**Por qué:**
- OpenCart calcula los impuestos con la **dirección de envío**.
- El MacBook está configurado como producto que **no requiere envío**. Si ningún producto del carrito lo requiere, el
  checkout no pide dirección de envío (no muestra la casilla "My delivery and billing addresses are the same").
- Sin dirección de envío, OpenCart usa la **dirección de la tienda (Reino Unido)** y cobra IVA británico y Eco Tax,
  aunque la facturación sea en Colombia.
- Cuando el carrito incluye un producto con envío (iPhone), la dirección de Colombia se usa también para los impuestos
  y esos cargos desaparecen.

**Impacto:** cobro incorrecto o inconsistente de impuestos, del orden de un 20% más en este caso.

**Recomendación:** revisar en la administración de OpenCart la configuración de impuestos (*Tax Rates* basados en
"Shipping Address" y la opción *Use Store Tax Address*). Para productos sin envío, los impuestos deberían calcularse
con la dirección de facturación.

### Hallazgo 3 - Productos sin stock

En el carrito, MacBook e iPhone aparecen marcados con `***` (sin stock). La tienda permite comprarlos igual. Si la
configuración cambia a "no permitir compra sin stock", el checkout se bloquearía.

## 3. Conclusiones sobre Serenity BDD y Screenplay

1. **Validar datos de negocio, no solo que la página cargue.** Comparar productos y precios en cada etapa fue lo que
   destapó los hallazgos 1 y 2.
2. **Una demo pública cambia sin aviso.** Las pruebas deben adaptarse a lo que muestra la página (paso de envío condicional)
   y no asumir un número fijo de pasos.
3. **La memoria del actor de Screenplay** (`remember` / `recall`) permite guardar el precio publicado en la página
   principal y compararlo después en el carrito y en el checkout, sin variables globales.
4. **Screenplay facilita la lectura y la reutilización.** Las tareas (`AddProductToCart`, `CheckoutAsGuest`,
   `ConfirmOrder`) se reutilizan en los escenarios de compra y de hallazgo, y el informe se lee como el flujo de negocio.
5. **Faltan identificadores únicos en el front.** Varios elementos se repiten (botones "Add to Cart", tablas del
   carrito en la página) y obligan a usar XPath relativos. Se recomienda agregar identificadores
   (`id` o `data-testid`) en el front, esto es buena práctica y es una recomendación a hacer al equipo de desarrollo.
6. Se optó por dejar el informe en el repo solo para dar a conocer su resultado sin la necesidad de ejecutarlo, sin 
   embargo esto no es buena práctica pues este informe genera muchos archivos temporales que cambian constantemente.