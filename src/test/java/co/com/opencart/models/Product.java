package co.com.opencart.models;

import java.math.BigDecimal;

public record Product(String name, BigDecimal price, BigDecimal priceExTax) {
}
