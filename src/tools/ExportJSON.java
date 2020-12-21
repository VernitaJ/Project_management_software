package tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entities.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;


public class ExportJSON {
    private final ProjectLibrary projectLibrary;
    private final TaskLibrary taskLibrary;
    private final UserLibrary userLibrary;
    private final ObjectMapper MAPPER;
    private String userFileName;
    private String listOfUsersFileName;
    private String projectFileName;
    private String listOfProjectsFileName;
    
    public ExportJSON(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        this.MAPPER = new ObjectMapper();
        this.MAPPER.registerModule(new JavaTimeModule());
        this.MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.MAPPER.configure(WRAP_ROOT_VALUE, false);
        this.userFileName = System.getProperty("user.home") + "/user.json";
        this.listOfUsersFileName = System.getProperty("user.home") + "/users.json";
        this.projectFileName = System.getProperty("user.home") + "/project.json";
        this.listOfProjectsFileName = System.getProperty("user.home") + "/projects.json";
        //    collectData();
        writeJsonUsers();
        readJsonUsers();
        
        writeJsonProjects();
        readJsonProjects();
        
        
    }
    
    public void writeJsonUsers() throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.userFileName).toFile(), this.userLibrary.getDataList().get(0));
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.listOfUsersFileName).toFile(), userLibrary.getDataList());
        
    }
    
    public void readJsonUsers() throws IOException {
        User readUser = MAPPER.readValue(new File(this.userFileName), User.class);
        userLibrary.getDataList().add(readUser);
    
        List<User> newLibrary = MAPPER.readValue(new File(this.listOfUsersFileName), new TypeReference<List<User>>(){});
        for (User user : newLibrary) {
           if(userLibrary.findUserInList(user.getUserName()) != null) {
               System.out.println("User already exist in system");
           } else {
               userLibrary.addUserToList(user);
           }
        }
        userLibrary.printAllUsers();
    }
    
    public void writeJsonProjects() throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.projectFileName).toFile(), this.projectLibrary.getDataList().get(0));
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.listOfProjectsFileName).toFile(), projectLibrary.getDataList());
        
    }
    
    public void readJsonProjects() throws IOException {
        Project readProject = MAPPER.readValue(new File(this.projectFileName), Project.class);
        userLibrary.getDataList().add(readProject);
        
        List<Project> newLibrary = MAPPER.readValue(new File(this.listOfProjectsFileName), new TypeReference<List<Project>>(){});
        for (Project project : newLibrary) {
            if(projectLibrary.findItInList(project.getID()) != null) {
                System.out.println("Project already exist in system");
            } else {
                projectLibrary.addProjectToList(project);
            }
        }
        projectLibrary.printAllProjects();
    }
    
/*    public void writeJSONUser() throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.path).toFile(), this.userLibrary.getDataList().get(0));
        // MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.userPath).toFile(), userLibrary.getDataList());
        
    }*/
    
/*    public void readJSONUser() throws IOException {
        User readUser = MAPPER.readValue(new File(this.path), User.class);
        userLibrary.getDataList().add(readUser);
    }*/
}
