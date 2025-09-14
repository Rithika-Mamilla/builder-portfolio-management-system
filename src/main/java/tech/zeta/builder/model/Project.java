package tech.zeta.builder.model;

import java.sql.Date;

public class Project {
    private int id;
    private String name;
    private String description;
    private String status;
    private Date startDate;
    private Date endDate;
    private double budget;
    private double expenses;
    private int builderId;
    private int managerId;
    private int clientId;
    private double progress;

    public Project() {}

    public Project(int id, String name, String description, double budget, int builderId, int clientId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = "Upcoming";
        this.budget = budget;
        this.expenses = 0;
        this.builderId = builderId;
        this.clientId = clientId;
        this.progress = 0;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expense) {
        this.expenses = expense;
    }

    public int getBuilderId() {
        return builderId;
    }

    public void setBuilderId(int builderId) {
        this.builderId = builderId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", budget=" + budget +
                ", expenses=" + expenses +
                ", builderId=" + builderId +
                ", managerId=" + managerId +
                ", clientId=" + clientId +
                ", progress=" + progress +
                '}';
    }
}
