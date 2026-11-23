package co.com.opencart.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Money {

    private static final Pattern AMOUNT = Pattern.compile("\\$\\s*([\\d,]+\\.\\d{2})");

    private Money() {
    }

    public static BigDecimal parse(String text) {
        Matcher matcher = AMOUNT.matcher(text);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No se encontro un precio en el texto: '" + text + "'");
        }
        return new BigDecimal(matcher.group(1).replace(",", ""));
    }

    public static BigDecimal parseAfter(String text, String label) {
        int index = text.indexOf(label);
        if (index < 0) {
            throw new IllegalArgumentException("No se encontro '" + label + "' en el texto: '" + text + "'");
        }
        return parse(text.substring(index + label.length()));
    }
}
