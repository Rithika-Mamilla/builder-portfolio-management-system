package tech.zeta.builder.model;

public class Builder extends User{
    public Builder(int id, String name, String email, String password) {
        super(id, name, email, password, "Builder");
    }
}
