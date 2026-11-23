package co.com.opencart.models;

public record GuestCustomer(String firstName,
                            String lastName,
                            String email,
                            String telephone,
                            String company,
                            String address,
                            String city,
                            String postcode,
                            String country,
                            String region) {

    public static GuestCustomer withDefaultData() {
        return new GuestCustomer(
                "Carlos",
                "Gomez",
                "comprador.invitado@example.com",
                "3001234567",
                "QA Testing",
                "Avenida 123",
                "Bogota D.C.",
                "110110",
                "Colombia",
                "Bogota D.C.");
    }
}
