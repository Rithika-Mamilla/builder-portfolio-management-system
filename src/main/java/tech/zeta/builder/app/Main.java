package tech.zeta.builder.app;

import tech.zeta.builder.exception.InvalidEmailException;
import tech.zeta.builder.exception.InvalidUserException;
import tech.zeta.builder.exception.ProjectNotFoundException;
import tech.zeta.builder.model.ProjectDocument;
import tech.zeta.builder.model.Project;
import tech.zeta.builder.service.ProjectService;
import tech.zeta.builder.service.impl.AuthenticationServiceImpl;
import tech.zeta.builder.service.impl.ProjectServiceImpl;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner input;
    static AuthenticationServiceImpl authenticationService;
    static ProjectService projectService;
    static int projectId;
    public static void main(String[] args) {
        authenticationService = new AuthenticationServiceImpl();
        projectService = new ProjectServiceImpl();

        input = new Scanner(System.in);
        boolean exit = false;

        while(!exit){
            try {
                System.out.println("\n==== Builder Portfolio Management System ====");
                System.out.printf("1. Register (Builder only) %n2. Login %n3. Exit%n");
                System.out.print("\nEnter your option: ");
                int option = input.nextInt(); input.nextLine();
                switch(option) {
                    case 1: addUser("Builder"); break;
                    case 2: loginMenu(); break;
                    case 3:
                        System.out.println("Exiting...");
                        exit = true; break;
                    default: System.out.println("Invalid option! Try Again."); break;
                }
            } catch (InvalidEmailException | InvalidUserException exception) {
                System.err.println(exception.getMessage());
            }
        }
        input.close();
    }

    public static void loginMenu() {
        try {
            System.out.println("\n------- Login Menu -------");
            System.out.printf("1. Builder %n2. Project Manager %n3. Client%n");
            System.out.print("\nEnter your choice: ");
            int choice = input.nextInt(); input.nextLine();
            if(choice > 0 && choice < 4) {
                System.out.print("Enter email: ");
                String email = input.nextLine();
                if (!email.contains("@") || !email.contains(".")) {
                    throw new InvalidEmailException("Please enter a valid email!");
                }
                System.out.print("Enter password: ");
                String password = input.nextLine();
                String role = (choice == 1) ? "Builder" : (choice == 2) ? "Project Manager" : "Client";
                int id = authenticationService.login(email, password, role);
                if (id < 0) {
                    throw new InvalidUserException("User doesn't exist!");
                }
                switch (choice) {
                    case 1: builderMenu(id); break;
                    case 2: managerMenu(id); break;
                    case 3: clientMenu(id); break;
                }
            }
            else {
                System.out.println("Invalid choice!");
            }
        } catch (InvalidEmailException | InvalidUserException exception) {
            System.err.println(exception.getMessage());
        }

    }

    public static void builderMenu(int builderId) {
        boolean logout = false;
        while (!logout) {
            try {
                System.out.println("\n------- Builder Menu -------");
                System.out.printf("1. Add Project %n2. Add Client %n3. Add Project Manager %n4. Assign Project Manager %n5. View Assigned Projects%n");
                System.out.printf("6. View Project Details %n7. View Project Documents %n8. Track Project Budget vs Actual Spend %n9. View Project Timeline %n10. Logout%n");
                System.out.print("\nEnter your choice: ");
                int choice = input.nextInt(); input.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Enter project name: ");
                        String projectName = input.nextLine();
                        System.out.print("Enter description: ");
                        String description = input.nextLine();
                        System.out.print("Enter client id: ");
                        int clientId = input.nextInt();
                        projectId = projectService.addProject(projectName, description, builderId, clientId);
                        if (projectId >= 0) {
                            System.out.println("Project added successfully! Project ID: " + projectId);
                        } break;
                    case 2: addUser("Client"); break;
                    case 3: addUser("Project Manager"); break;
                    case 4:
                        projectId = inputProjectId();
                        System.out.print ("Enter project manager id: ");
                        int managerId = input.nextInt();
                        if (projectService.assignManager(projectId, builderId, managerId)) {
                            System.out.println("Project Manager assigned successfully!");
                        } break;
                    case 5: viewAllProjects(builderId); break;
                    case 6: viewProjectDetails(builderId); break;
                    case 7: viewProjectDocuments(builderId); break;
                    case 8: viewProjectBudget(builderId); break;
                    case 9: viewProjectTimeline(builderId); break;
                    case 10:
                        System.out.println("Logging out...");
                        logout = true; break;
                    default: System.out.println("Invalid option! Try Again."); break;
                }
            } catch (InvalidEmailException | InvalidUserException | ProjectNotFoundException exception) {
                System.err.println(exception.getMessage());
            }

        }
    }

    public static void managerMenu(int managerId) {
        boolean logout = false;
        while (!logout) {
            try {
                System.out.println("\n------- Project Manager Menu -------");
                System.out.printf("1. Update Project Status %n2. Update Project Timeline  %n3. Update Project Budget %n4. Update Project Expenses %n5. Upload Project Documents %n6. Update Project Progress %n");
                System.out.printf("7. View All Assigned Projects %n8. View Project Details %n9. Track Project Budget vs Actual Spend %n10. View Project Timeline %n11. View Project Documents %n12. Logout%n");
                System.out.print("\nEnter your choice: ");
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        projectId = inputProjectId();
                        System.out.print("Enter Project Status: ");
                        String status = input.nextLine();
                        if (projectService.updateStatus(projectId, managerId, status)) {
                            System.out.println("Project status updated successfully!");
                        }
                        break;
                    case 2:
                        projectId = inputProjectId();
                        System.out.print("Enter start date (YYYY-MM-DD): ");
                        String startDate = input.nextLine();
                        System.out.print("Enter planned end date (YYYY-MM-DD): ");
                        String endDate = input.nextLine();
                        if (projectService.updateTimeline(projectId, managerId, Date.valueOf(startDate), Date.valueOf(endDate))) {
                            System.out.println("Project timeline updated successfully!");
                        }
                        break;
                    case 3:
                        projectId = inputProjectId();
                        System.out.print("Enter budget: ");
                        double budget = input.nextDouble();
                        if (projectService.updateBudget(projectId, managerId, budget)) {
                            System.out.println("Project budget updated successfully!");
                        }
                        break;
                    case 4:
                        projectId = inputProjectId();
                        System.out.print("Enter expense: ");
                        double expense = input.nextDouble();
                        if (projectService.updateExpenses(projectId, managerId, expense)) {
                            System.out.println("Project expense updated successfully!");
                        }
                        break;
                    case 5:
                        projectId = inputProjectId();
                        System.out.print("Enter file name: ");
                        String filename = input.nextLine();
                        System.out.print("Upload file (path): ");
                        String filepath = input.nextLine();
                        if (projectService.uploadDocuments(projectId, managerId, filename, filepath)) {
                            System.out.println("Project document updated successfully!");
                        }
                        break;
                    case 6:
                        projectId = inputProjectId();
                        System.out.print("Enter project progress percentage: ");
                        double progress = input.nextDouble();
                        if (projectService.updateProgress(projectId, managerId, progress)) {
                            System.out.println("Project progress updated successfully!");
                        }
                        break;
                    case 7:
                        viewAllProjects(managerId);
                        break;
                    case 8:
                        viewProjectDetails(managerId);
                        break;
                    case 9:
                        viewProjectBudget(managerId);
                        break;
                    case 10:
                        viewProjectTimeline(managerId);
                        break;
                    case 11:
                        viewProjectDocuments(managerId);
                        break;
                    case 12:
                        System.out.println("Logging out...");
                        logout = true;
                        break;
                    default:
                        System.out.println("Invalid option! Try Again.");
                        break;
                }
            } catch (ProjectNotFoundException exception) {
                System.err.println(exception.getMessage());
            }
        }
    }

    public static void clientMenu(int clientId) {
        boolean logout = false;
        while (!logout) {
            try {
                System.out.println("\n------- Client Menu -------");
                System.out.printf("1. View All Assigned Projects %n2. View Project Details %n3. Track Project Budget vs Actual Spend %n4. View Project Timeline %n5. View Project Documents %n6. Logout%n");
                System.out.print("\nEnter your choice: ");
                int choice = input.nextInt(); input.nextLine();
                switch (choice) {
                    case 1: viewAllProjects(clientId); break;
                    case 2: viewProjectDetails(clientId); break;
                    case 3: viewProjectBudget(clientId); break;
                    case 4: viewProjectTimeline(clientId); break;
                    case 5: viewProjectDocuments(clientId); break;
                    case 6:
                        System.out.println("Logging out...");
                        logout = true; break;
                    default: System.out.println("Invalid option! Try Again."); break;
                }
            } catch (ProjectNotFoundException exception) {
                System.err.println(exception.getMessage());
            }
        }
    }

    public static int inputProjectId() {
        System.out.print("Enter project id: ");
        int projectId = input.nextInt(); input.nextLine();
        return projectId;
    }

    public static void addUser(String role) throws InvalidEmailException, InvalidUserException {
        System.out.print("Enter name: ");
        String name = input.nextLine();
        System.out.print("Enter email: ");
        String email = input.nextLine();
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException("Please enter a valid email!");
        }
        System.out.print("Enter password: ");
        String password = input.nextLine();
        int id = authenticationService.register(name, email, password, role);
        if (id >= 0) {
            System.out.printf("Registration successful! %s ID: %d%n", role, id);
        } else {
            throw new InvalidUserException("User already exists!");
        }
    }

    public static void viewAllProjects(int userId) {
        List<Project> projects = projectService.viewProjects(userId);
        if (!projects.isEmpty()) {
            projects.forEach(System.out::println);
        } else {
            System.out.println("No projects found!");
        }
    }

    public static void viewProjectDetails(int userId) throws ProjectNotFoundException {
        projectId = inputProjectId();
        Project project = projectService.viewProjectDetails(userId, projectId);
        if (project != null) {
            System.out.println(project);
        }
    }

    public static void viewProjectDocuments(int userId) throws ProjectNotFoundException {
        projectId = inputProjectId();
        List<ProjectDocument> documents = projectService.viewProjectDocuments(userId, projectId);
        if (!documents.isEmpty()) {
            documents.forEach(System.out::println);
        } else {
            System.out.println("No documents found!");
        }
    }

    public static void viewProjectBudget(int userId) throws ProjectNotFoundException {
        projectId = inputProjectId();
        projectService.trackBudget(userId, projectId);
    }

    public static void viewProjectTimeline(int userId) throws ProjectNotFoundException {
        projectId = inputProjectId();
        projectService.viewTimeline(userId, projectId);
    }
}
