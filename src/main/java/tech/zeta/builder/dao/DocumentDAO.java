package tech.zeta.builder.dao;

import tech.zeta.builder.exception.ProjectNotFoundException;
import tech.zeta.builder.model.ProjectDocument;
import tech.zeta.builder.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentDAO {
    private Connection connection;
    private static final Logger logger = Logger.getLogger(DocumentDAO.class.getName());

    // Database Connection
    public DocumentDAO() {
        this.connection = DBUtil.getConnection();
    }

    // Saves Document Details into the Database
    public boolean saveDocument(ProjectDocument document) throws ProjectNotFoundException {
        String checkProjectSql = "SELECT id FROM projects WHERE id=?";
        String sql = "INSERT INTO documents (project_id, file_name, file_path) VALUES (?, ?, ?)";
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(checkProjectSql);
                statement.setInt(1, document.getProjectId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(!resultSet.next()) {
                        throw new ProjectNotFoundException("Project with ID: " + document.getProjectId()+ " not found!");
                    }
                }
                statement = connection.prepareStatement(sql);
                statement = connection.prepareStatement(sql);
                statement.setInt(1, document.getProjectId());
                statement.setString(2, document.getName());
                statement.setString(3, document.getPath());
                if (statement.executeUpdate() > 0) {
                    return true;
                } else {
                    throw new ProjectNotFoundException("Project not found!");
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return false;
    }

    // Retrieves the Project Documents based on projectId
    public List<ProjectDocument> getDocumentsByProjectId(int projectId) throws ProjectNotFoundException {
        List<ProjectDocument> documents = new ArrayList<>();
        String checkProjectSql = "SELECT id FROM projects WHERE id=?";
        String sql = "SELECT * FROM documents WHERE project_id=?";
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(checkProjectSql);
                statement.setInt(1, projectId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(!resultSet.next()) {
                        throw new ProjectNotFoundException("Project with ID: " + projectId + " not found!");
                    }
                }
                statement = connection.prepareStatement(sql);
                statement.setInt(1, projectId);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        ProjectDocument document = new ProjectDocument();
                        document.setId(rs.getInt("id"));
                        document.setProjectId(rs.getInt("project_id"));
                        document.setName(rs.getString("file_name"));
                        document.setPath(rs.getString("file_path"));
                        documents.add(document);
                    }
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return documents;
    }
}
