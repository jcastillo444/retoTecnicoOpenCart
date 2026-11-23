package co.com.opencart.models;

import java.math.BigDecimal;

public record OrderLine(String name, int quantity, BigDecimal unitPrice) {

    @Override
    public String toString() {
        return name + " x" + quantity + " a $" + unitPrice;
    }
}
