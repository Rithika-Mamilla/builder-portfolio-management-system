package tech.zeta.builder.dao;

import tech.zeta.builder.exception.ProjectNotFoundException;
import tech.zeta.builder.model.Project;
import tech.zeta.builder.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectDAO {
    private Connection connection;
    private static final Logger logger = Logger.getLogger(ProjectDAO.class.getName());

    public ProjectDAO() {
        this.connection = DBUtil.getConnection();
    }

    // Add new project
    public int addProject(Project project) {
        String sql = "INSERT INTO projects (name, description, builder_id, client_id, status, budget, expenses, start_date, end_date, progress) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, project.getName());
                statement.setString(2, project.getDescription());
                statement.setInt(3, project.getBuilderId());
                statement.setInt(4, project.getClientId());
                statement.setString(5, project.getStatus()); // Default: "Upcoming"
                statement.setDouble(6, project.getBudget());
                statement.setDouble(7, project.getExpenses());
                statement.setNull(8, Types.DATE);
                statement.setNull(9, Types.DATE);
                statement.setDouble(10, project.getProgress());

                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet rs = statement.getGeneratedKeys();
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return -1;
    }

    // Assign project manager to project
    public boolean assignManager(int projectId, int builderId, int managerId) throws ProjectNotFoundException {
        try {
            if (checkProject(projectId)) {
                String sql = "UPDATE projects SET manager_id=? WHERE id=? AND builder_id=?";
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, managerId);
                    statement.setInt(2, projectId);
                    statement.setInt(3, builderId);
                    if (statement.executeUpdate() > 0) {
                        return true;
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return false;
    }

    // Update project status based on project_id
    public boolean updateStatus(int projectId, int managerId, String status) throws ProjectNotFoundException {
        try {
            if (checkProject(projectId)) {
                String sql = "UPDATE projects SET status=? WHERE id=? AND manager_id=?";
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, status);
                    statement.setInt(2, projectId);
                    statement.setInt(3, managerId);
                    if (statement.executeUpdate() > 0) {
                        return true;
                    } else {
                        throw new ProjectNotFoundException("Project not found!");
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return false;
    }

    // Update budget of project
    public boolean updateBudget(int projectId, int managerId, double budget
    ) throws ProjectNotFoundException {
        try {
            if (checkProject(projectId)) {
                String sql = "UPDATE projects SET budget=? WHERE id=? AND manager_id=?";
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setDouble(1, budget);
                    statement.setInt(2, projectId);
                    statement.setInt(3, managerId);
                    if (statement.executeUpdate() > 0) {
                        return true;
                    } else {
                        throw new ProjectNotFoundException("Project not found!");
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return false;
    }

    // Update expenses of project
    public boolean updateExpenses(int projectId, int managerId, double expense) throws ProjectNotFoundException {
        try {
            if (checkProject(projectId)) {
                String sql = "UPDATE projects SET expenses=expenses + ? WHERE id=? AND manager_id=?";
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setDouble(1, expense);
                    statement.setInt(2, projectId);
                    statement.setInt(3, managerId);
                    if (statement.executeUpdate() > 0) {
                        return true;
                    } else {
                        throw new ProjectNotFoundException("Project not found!");
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return false;
    }

    // Update project timeline
    public boolean updateTimeline(int projectId, int managerId, Date startDate, Date endDate) throws ProjectNotFoundException{
        try {
            if (checkProject(projectId)) {
                String sql = "UPDATE projects SET start_date=?, end_date=? WHERE id=? AND manager_id=?";
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setDate(1, startDate);
                    statement.setDate(2, endDate);
                    statement.setInt(3, projectId);
                    statement.setInt(4, managerId);
                    if (statement.executeUpdate() > 0) {
                        return true;
                    } else {
                        throw new ProjectNotFoundException("Project not found!");
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return false;
    }

    // Update project progress
    public boolean updateProgress(int projectId, int managerId, double progress) throws ProjectNotFoundException {
        try {
            if (checkProject(projectId)) {
                String sql = "UPDATE projects SET progress=? WHERE id=? AND manager_id=?";
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setDouble(1, progress);
                    statement.setInt(2, projectId);
                    statement.setInt(3, managerId);
                    if (statement.executeUpdate() > 0) {
                        return true;
                    } else {
                        throw new ProjectNotFoundException("Project not found!");
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return false;
    }

    // Get all projects associated with a user (Builder / Client / Manager)
    public List<Project> getProjectsByUserId(int userId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE builder_id=? OR client_id=? OR manager_id=?";
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                statement.setInt(2, userId);
                statement.setInt(3, userId);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    projects.add(mapToProject(rs));
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return projects;
    }

    // Get project details
    public Project getProjectById(int projectId) throws ProjectNotFoundException {
        try {
            if (checkProject(projectId)) {
                String sql = "SELECT * FROM projects WHERE id=?";
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, projectId);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        return mapToProject(rs);
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return null;
    }

    private boolean checkProject(int projectId) throws ProjectNotFoundException {
        String checkProjectSql = "SELECT id FROM projects WHERE id=?";
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(checkProjectSql);
                statement.setInt(1, projectId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(!resultSet.next()) {
                        throw new ProjectNotFoundException("Project with ID: " + projectId + " not found!");
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return true;
    }

    // Utility: map ResultSet → Project
    private Project mapToProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt("id"));
        project.setName(rs.getString("name"));
        project.setDescription(rs.getString("description"));
        project.setBuilderId(rs.getInt("builder_id"));
        project.setClientId(rs.getInt("client_id"));
        project.setManagerId(rs.getInt("manager_id"));
        project.setStatus(rs.getString("status"));
        project.setBudget(rs.getDouble("budget"));
        project.setExpenses(rs.getDouble("expenses"));
        project.setStartDate(rs.getDate("start_date"));
        project.setEndDate(rs.getDate("end_date"));
        project.setProgress(rs.getDouble("progress"));
        return project;
    }
}
