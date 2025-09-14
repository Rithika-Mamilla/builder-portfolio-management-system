package tech.zeta.builder.model;

public class Client extends User {
    public Client(int id, String name, String email, String password) {
        super(id, name, email, password, "Client");
    }
}
