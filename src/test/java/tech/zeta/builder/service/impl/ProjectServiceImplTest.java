package tech.zeta.builder.service.impl;

import org.junit.jupiter.api.*;
import tech.zeta.builder.model.Project;
import tech.zeta.builder.model.ProjectDocument;
import tech.zeta.builder.service.impl.ProjectServiceImpl;
import tech.zeta.builder.exception.ProjectNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectServiceImplTest {

    private static ProjectServiceImpl projectService;

    @BeforeAll
    static void setup() {
        projectService = new ProjectServiceImpl();
    }

    @Test
    void testViewProjects() {
        List<Project> projects = projectService.viewProjects(2);
        assertNotNull(projects, "Projects list should not be null");
        assertFalse(projects.isEmpty(), "User should have at least one project");
    }

    @Test
    void testViewProjectDetails() throws ProjectNotFoundException {
        Project project = projectService.viewProjectDetails(2, 3);
        assertNotNull(project, "Project should not be null");
        assertEquals(3, project.getId(), "Project ID should match expected value");
    }

    @Test
    void testViewProjectDocuments() throws ProjectNotFoundException {
        List<ProjectDocument> docs = projectService.viewProjectDocuments(2, 2);
        assertNotNull(docs, "Documents list should not be null");
        assertTrue(docs.isEmpty(), "Project should not have at least one document");
    }

    @Test
    void testTrackBudget_NoError() {
        assertDoesNotThrow(() -> projectService.trackBudget(2, 2));
    }

    @Test
    void testViewTimeline_NoError() {
        assertDoesNotThrow(() -> projectService.viewTimeline(2, 2));
    }
}
