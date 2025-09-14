package tech.zeta.builder.service.impl;

import tech.zeta.builder.dao.ProjectDAO;
import tech.zeta.builder.dao.DocumentDAO;
import tech.zeta.builder.dao.UserDAO;
import tech.zeta.builder.exception.InvalidUserException;
import tech.zeta.builder.exception.ProjectNotFoundException;
import tech.zeta.builder.model.Project;
import tech.zeta.builder.model.ProjectDocument;
import tech.zeta.builder.service.ProjectService;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

public class ProjectServiceImpl implements ProjectService {

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final DocumentDAO documentDAO;

    public ProjectServiceImpl() {
        this.userDAO = new UserDAO();
        this.projectDAO = new ProjectDAO();
        this.documentDAO = new DocumentDAO();
    }

    @Override
    public int addProject(String name, String description, int builderId, int clientId) throws InvalidUserException {
        if (!userDAO.checkUser(clientId)) {
            throw new InvalidUserException("Client with ID:" + clientId + " doesn't exist!");
        }
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setBuilderId(builderId);
        project.setClientId(clientId);
        project.setStatus("Upcoming");
        project.setBudget(0);
        project.setExpenses(0);
        project.setProgress(0);
        project.setStartDate(null);
        project.setEndDate(null);

        return projectDAO.addProject(project);
    }

    @Override
    public boolean assignManager(int projectId, int builderId, int managerId) throws ProjectNotFoundException {
        return projectDAO.assignManager(projectId, builderId, managerId);
    }

    @Override
    public List<Project> viewProjects(int userId) {
        return projectDAO.getProjectsByUserId(userId);
    }

    @Override
    public Project viewProjectDetails(int userId, int projectId) throws ProjectNotFoundException {
        return projectDAO.getProjectById(projectId);
    }

    @Override
    public void trackBudget(int userId, int projectId) throws ProjectNotFoundException {
        Project project = projectDAO.getProjectById(projectId);
        if (project != null) {
            double budget = project.getBudget();
            double expenses = project.getExpenses();
            int expensesPercentage = (int) ((expenses / budget) * 100);
            double remaining = project.getBudget() - project.getExpenses();
            int totalBars = 30; // bar length
            int filledBars = (expensesPercentage * totalBars) / 100;

            StringBuilder bar = new StringBuilder("|");
            for (int i = 0; i < totalBars; i++) {
                if (i < filledBars) {
                    bar.append("█");
                } else {
                    bar.append("░");
                }
            }
            bar.append("| ").append(expenses).append("/").append(budget);
            System.out.println("Budget Report for Project: " + project.getName());
            System.out.println("   Total Budget : " + project.getBudget());
            System.out.println("   Expenses     : " + project.getExpenses());
            System.out.println("   Remaining    : " + remaining);
            System.out.println("   Budget vs Expenses: " + bar);
        } else {
            System.out.println("Project not found.");
        }
    }

    @Override
    public void viewTimeline(int userId, int projectId) throws ProjectNotFoundException {
        Project project = projectDAO.getProjectById(projectId);
        if (project != null) {
            int progress = (int) project.getProgress();
            int totalBars = 30; // Total bar length
            int filledBars = (progress * totalBars) / 100;

            StringBuilder bar = new StringBuilder("|");
            for (int i = 0; i < totalBars; i++) {
                if (i < filledBars) {
                    bar.append("█"); // filled
                } else {
                    bar.append("░"); // empty
                }
            }
            bar.append("| " + progress + "%");
            System.out.println("Timeline for Project: " + project.getName());
            System.out.println("   Start Date : " + project.getStartDate());
            System.out.println("   End Date   : " + project.getEndDate());
            System.out.println("   Timeline Progress: " + bar);
        } else {
            System.out.println("Project not found.");
        }
    }

    @Override
    public List<ProjectDocument> viewProjectDocuments(int userId, int projectId) throws ProjectNotFoundException{
        return documentDAO.getDocumentsByProjectId(projectId);
    }

    @Override
    public boolean updateStatus(int projectId, int managerId, String status) throws ProjectNotFoundException {
        return projectDAO.updateStatus(projectId, managerId, status);
    }

    @Override
    public boolean uploadDocuments(int projectId, int managerId, String filename, String filepath) throws ProjectNotFoundException {
        ProjectDocument document = new ProjectDocument(projectId, filename, filepath);
        return documentDAO.saveDocument(document);
    }

    @Override
    public boolean updateBudget(int projectId, int managerId, double budget) throws ProjectNotFoundException {
        return projectDAO.updateBudget(projectId, managerId, budget);
    }

    @Override
    public boolean updateExpenses(int projectId, int managerId, double expense) throws ProjectNotFoundException {
        return projectDAO.updateExpenses(projectId, managerId, expense);
    }

    @Override
    public boolean updateTimeline(int projectId, int managerId, Date startDate, Date endDate) throws ProjectNotFoundException {
        return projectDAO.updateTimeline(projectId, managerId, startDate, endDate);
    }

    @Override
    public boolean updateProgress(int projectId, int managerId, double progress) throws ProjectNotFoundException {
        Project project = projectDAO.getProjectById(projectId);
        return projectDAO.updateProgress(projectId, managerId, progress);
    }
}
