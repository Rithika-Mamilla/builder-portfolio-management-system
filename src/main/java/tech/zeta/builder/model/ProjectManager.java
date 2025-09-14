package tech.zeta.builder.model;

public class ProjectManager extends User {
    public ProjectManager(int id, String name, String email, String password) {
        super(id, name, email, password, "Project Manager");
    }
}
