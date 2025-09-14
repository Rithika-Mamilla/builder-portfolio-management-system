package tech.zeta.builder.model;

public class ProjectDocument {
    private int id;
    private String name;
    private String path;
    private int projectId;

    public ProjectDocument() {}

    public ProjectDocument(int projectId, String name, String path) {
        this.projectId = projectId;
        this.name = name;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "ProjectDocument { " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", projectId=" + projectId +
                " }";
    }
}
