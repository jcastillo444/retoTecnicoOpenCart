# language: es
@compra
Característica: Compra como invitado en la tienda OpenCart
  Yo como usuario final
  Quiero seleccionar equipos de tecnologia y añadirlos al carrito de compras
  Para finalmente realizar una compra como invitado en la tienda virtual

  Antecedentes:
    Dado que Jonathan se dirige a la pagina de OpenCart

  @compra-exitosa
  Esquema del escenario: Compra exitosa como invitado - <caso>
    Cuando agrega los productos "<productos>" al carrito
    Y visualiza el carrito de compras
    Entonces el carrito deberia contener los productos seleccionados con el precio publicado
    Cuando realiza el checkout como invitado
    Entonces el resumen del pedido deberia mostrar los productos seleccionados con su precio sin impuestos
    Y el total del pedido deberia corresponder al Sub-Total mas los cargos adicionales
    Cuando confirma el pedido
    Entonces deberia ver el mensaje "Your order has been placed!"

    Ejemplos:
      | caso                                     | productos       |
      | dos productos con envio                  | MacBook, iPhone |
      | un producto que requiere envio (6 pasos) | iPhone          |

  @hallazgo @hallazgo-opciones-obligatorias
  Esquema del escenario: Hallazgo - producto con opciones obligatorias no se agrega desde la pagina principal - <producto>
    Cuando intenta agregar el producto "<producto>" al carrito desde la pagina principal
    Entonces la tienda no agrega el producto y lo redirige a la pagina del producto
    Y el motivo es que el producto tiene las opciones obligatorias "<opciones_obligatorias>"
    Y la tienda pide completar las opciones "<opciones_pendientes>"

    Ejemplos:
      | producto        | opciones_obligatorias                                                  | opciones_pendientes                     |
      | Apple Cinema 30 | Radio, Checkbox, Text, Select, Textarea, File, Date, Time, Date & Time | Radio, Checkbox, Select, Textarea, File |
      | Canon EOS 5D    | Select                                                                 | Select                                  |

  @hallazgo @hallazgo-impuestos
  Escenario: Hallazgo - se cobran impuestos del Reino Unido cuando el carrito no requiere envio
    Cuando agrega los productos "MacBook" al carrito
    Y visualiza el carrito de compras
    Y realiza el checkout como invitado
    Entonces el resumen del pedido cobra los impuestos del Reino Unido aunque la direccion sea de Colombia
