package tech.zeta.builder.service;

import tech.zeta.builder.exception.InvalidUserException;
import tech.zeta.builder.exception.ProjectNotFoundException;
import tech.zeta.builder.model.Project;
import tech.zeta.builder.model.ProjectDocument;

import java.sql.Date;
import java.util.List;

public interface ProjectService {
    int addProject(String name, String description, int builderId, int clientId) throws InvalidUserException;
    boolean assignManager(int projectId,int builderId, int managerId) throws ProjectNotFoundException;
    List<Project> viewProjects(int userId);
    Project viewProjectDetails(int userId, int projectId) throws ProjectNotFoundException;
    void trackBudget(int userId, int projectId) throws ProjectNotFoundException;
    void viewTimeline(int userId, int projectId) throws ProjectNotFoundException;
    List<ProjectDocument> viewProjectDocuments(int userId, int projectId) throws ProjectNotFoundException;
    boolean updateStatus(int projectId, int userId, String status) throws ProjectNotFoundException;
    boolean uploadDocuments(int projectId, int managerId, String filename, String filepath) throws ProjectNotFoundException;
    boolean updateBudget(int projectId, int managerId, double budget) throws ProjectNotFoundException;
    boolean updateExpenses(int projectId, int managerId, double expense) throws ProjectNotFoundException;
    boolean updateTimeline(int projectId, int managerId, Date startDate, Date endDate) throws ProjectNotFoundException;
    boolean updateProgress(int projectId, int managerId, double progress) throws ProjectNotFoundException;
}
