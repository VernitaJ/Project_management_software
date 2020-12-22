package tools;

import access_roles.*;
import budget.Budget;
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
import java.time.LocalDate;
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
        writeJsonProjects();
        parseJson();
        
        // readJsonUsers();
        // writeJsonTeam();

        // readJsonProjects();
        
        
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
    
    // Parse Project
    // Project info
    // ProjectManager (Check if it's you or deny import, Parameter etc.)
    
    // Team
    // memberList (User, Role) -> Has to create the users and fill list of team-member in team.
    
    // TaskList
    // Task, assignees
    
    // workedHoursLog
    // workedHours in Task (if user assigned exists)
    
    private void parseJson() throws IOException {
        JsonParser jsonParser = new JsonFactory().createParser(new File(this.projectFileName));
     //   parseUserJson(jsonParser);
        parseProjectJson(jsonParser);
    }
    
    private void parseProjectJson(JsonParser jsonParser) throws IOException {
        Project project = new Project();
        
        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            if ("name".equals(field)) {
                jsonParser.nextToken();
                project.setName(jsonParser.getText());
            } else if("description".equals(field)) {
                jsonParser.nextToken();
                project.setDescription(field);
            } else if("status".equals(field)) {
                jsonParser.nextToken();
                project.setStatus(field);
            } else if("createdDate".equals(field)) {
                jsonParser.nextToken();
                project.setCreatedDate(LocalDate.parse(jsonParser.getText()));
            } else if("startDate".equals(field)) {
                jsonParser.nextToken();
                project.setStartDate(LocalDate.parse(jsonParser.getText()));
            } else if("endDate".equals(field)) {
                jsonParser.nextToken();
                project.setEndDate(LocalDate.parse(jsonParser.getText()));
            } else if("budget".equals(field)) {
                project = parseBudgetJson(jsonParser, project);
            } else if("projectManager".equals(field)) {
                User projectManager = parseUserJson(jsonParser);
                project.setProjectManager(projectManager);
            } else if("team".equals(field)){
                System.out.println("in the Team");
                parseTeamJson(jsonParser, project);

            }

        }
    }
    
    private Project parseBudgetJson(JsonParser jsonParser, Project project) throws IOException {
        Budget budget = new Budget();
        String field = jsonParser.getCurrentName();

        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            if ("money".equals(field)) {
                jsonParser.nextToken();
               budget.setMoney(jsonParser.getDoubleValue());
            } else if ("hours".equals(field)) {
                jsonParser.nextToken();
                budget.setMoney(jsonParser.getDoubleValue());
            }
        }
        project.setBudget(budget);
        return project;
    }
    
    private void parseTeamJson(JsonParser jsonParser, Project project) throws IOException {
        // 1:

        TeamMember tempMember = null;
        System.out.println("Before memberList");
        jsonParser.nextToken();
        System.out.println(jsonParser.getText());
        jsonParser.nextToken();
        System.out.println(jsonParser.getText());
        String field = jsonParser.getCurrentName();
            if ("memberList".equals(field)) {
                System.out.println("in the memberList");
                while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    field = jsonParser.getCurrentName();
                    //jsonParser.nextToken();
                    System.out.println("parseTeamJson -> parseTeamMember");
                        tempMember = parseTeamMember(jsonParser);
                    }
                    project.getTeam().getMemberList().put(tempMember.getUser().getUserName(),tempMember);
                }

        System.out.println(project.getTeam().getAllTeamUsers());
    }
    
    private TeamMember parseTeamMember(JsonParser jsonParser) throws JsonParseException, IOException {
        TeamMember member = null;
        System.out.println("parseTeamMember");
        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            System.out.println("-----------------");
            System.out.println(jsonParser.getText());
            User tempUser = parseUserJson(jsonParser);
            System.out.println("parse Team member method");
            System.out.println(tempUser.toString());
            Role tempRole = parseRoleJson(jsonParser);
            member = new TeamMember(tempUser, tempRole);
            System.out.println("parsedMember");
        }
        return member;
    }
    
    private Role parseRoleJson(JsonParser jsonParser) throws IOException {
        Role tempRole = null;
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            if ("type".equals(field)) {
                jsonParser.nextToken();
                if("Owner".equals(field)) {
                    tempRole = new Owner();
                } else if ("Maintainer".equals(field)) {
                    tempRole = new Maintainer();
                } else if ("Developer".equals(field)) {
                    tempRole = new Developer();
                } else {
                    tempRole = new CustomRoles(jsonParser.getText());
                    jsonParser.nextToken();
                    tempRole.setCanCreateTask(jsonParser.getValueAsBoolean());
                    jsonParser.nextToken();
                    tempRole.setAdminAccess(jsonParser.getValueAsBoolean());
                }
            }
        }
        return (tempRole);
    }

    private User parseUserJson(JsonParser jsonParser) throws JsonParseException, IOException {
        User user = new User();
        System.out.println(jsonParser.getText());
        //loop through the JsonTokens
        while(jsonParser.nextToken() != JsonToken.END_OBJECT){
            String field = jsonParser.getCurrentName();
            if("userName".equals(field)){
                jsonParser.nextToken();
                user.setName(jsonParser.getText());
            }else if("password".equals(field)){
                jsonParser.nextToken();
                user.setPassword(jsonParser.getText());
            }else if("eMail".equals(field)){
                jsonParser.nextToken();
                user.setEmail(jsonParser.getText());
            }else if("occupation".equals(field)){
                jsonParser.nextToken();
                user.setOccupation(jsonParser.getText());
            }else if("companyName".equals(field)){
                jsonParser.nextToken();
                user.setCompanyName(jsonParser.getText());
            }else if("salary".equals(field)){
                jsonParser.nextToken();
                user.setSalary(jsonParser.getFloatValue());
            }else if("workingHours".equals(field)){
                jsonParser.nextToken();
                user.setWorkingHours(jsonParser.getFloatValue());
            }else if("inbox".equals(field)){
                jsonParser.nextToken();
                //while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                //    user.setInbox(user.getInbox().add( (Message) jsonParser.getText())); // ??
                //}
            }else if("achievementTracker".equals(field)){
                while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    // user.setAchievementTracker(); // ??
                }
                System.out.println("EO tracker");
                System.out.println(jsonParser.getText());
            }else if("experience".equals(field)){
                jsonParser.nextToken();
                user.setExperience(jsonParser.getValueAsInt());
            }else if("id".equals(field)){
                jsonParser.nextToken();
                user.setID(jsonParser.getText());
            }
        }
        if(userLibrary.findUserInList(user.getUserName()) == null){
            userLibrary.addUserToList(user);
        }
        return user;
    }


    
}
