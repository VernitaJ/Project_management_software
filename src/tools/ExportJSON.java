package tools;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entities.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.CLOSE_CLOSEABLE;
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
    private String teamFileName;
    
    public ExportJSON(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        this.MAPPER = new ObjectMapper();
        this.MAPPER.registerModule(new JavaTimeModule());
        this.MAPPER.configure(CLOSE_CLOSEABLE, false);
        this.MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.MAPPER.configure(WRAP_ROOT_VALUE, false);
        this.userFileName = System.getProperty("user.home") + "/user.json";
        this.listOfUsersFileName = System.getProperty("user.home") + "/users.json";
        this.projectFileName = System.getProperty("user.home") + "/project.json";
        this.listOfProjectsFileName = System.getProperty("user.home") + "/projects.json";
        this.teamFileName = System.getProperty("user.home") + "/team.json";

        //    collectData();
        writeJsonUsers();
        JsonParser jsonParser = new JsonFactory().createParser(new File(userFileName));
        parseUserJSON(jsonParser);
        /*
        readJsonUsers();
        writeJsonTeam();
        writeJsonProjects();
        readJsonProjects();
        */
        
    }
    
    public void writeJsonUsers() throws IOException {
        List<Data> userList = this.userLibrary.getDataList();


        for (int i = 0; i < userList.size(); i++) {
            this.MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.userFileName).toFile(), this.userLibrary.getDataList().get(i));
        }

        //this.MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.listOfUsersFileName).toFile(), userLibrary.getDataList());
    }
    
    public void readJsonUsers() throws IOException {
        User readUser = MAPPER.readValue(new File(this.userFileName), User.class);
        userLibrary.getDataList().add(readUser);


        ArrayList<User> newLibrary = MAPPER.readValue(new File(this.listOfUsersFileName), new TypeReference<>(){});
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

    public void writeJsonTeam() throws IOException {
        Data stuff = this.projectLibrary.getDataList().get(0);
        Project project = (Project) stuff;
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(Paths.get(this.teamFileName).toFile(), project.getTeam());
    }

    private void parseUserJSON(JsonParser jsonParser) throws JsonParseException, IOException {
        User user = new User();

        //loop through the JsonTokens
        while(jsonParser.nextToken() != JsonToken.END_OBJECT){
            String name = jsonParser.getCurrentName();
            if("userName".equals(name)){
                jsonParser.nextToken();
                user.setName(jsonParser.getText());
            }else if("password".equals(name)){
                jsonParser.nextToken();
                user.setPassword(jsonParser.getText());
            }else if("eMail".equals(name)){
                jsonParser.nextToken();
                user.setEmail(jsonParser.getText());
            }else if("occupation".equals(name)){
                jsonParser.nextToken();
                user.setOccupation(jsonParser.getText());
            }else if("companyName".equals(name)){
                jsonParser.nextToken();
                user.setCompanyName(jsonParser.getText());
            }else if("salary".equals(name)){
                jsonParser.nextToken();
                user.setSalary(jsonParser.getFloatValue());
            }else if("workingHours".equals(name)){
                jsonParser.nextToken();
                user.setWorkingHours(jsonParser.getFloatValue());
            }else if("inbox".equals(name)){
                jsonParser.nextToken();
                //while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                //    user.setInbox(user.getInbox().add( (Message) jsonParser.getText())); // ??
                //}
            }else if("achievementTracker".equals(name)){
                jsonParser.nextToken();
                // user.setAchievementTracker(); // ??
            }else if("experience".equals(name)){
                jsonParser.nextToken();
                user.setExperience(jsonParser.getIntValue());
            }else if("id".equals(name)){
                jsonParser.nextToken();
                user.setID(jsonParser.getText());
            }
        }
        System.out.println(user.getInfo());
    }

}
