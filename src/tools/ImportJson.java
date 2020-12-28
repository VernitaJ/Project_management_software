package tools;

import access_roles.*;
import budget.Budget;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import entities.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ImportJson {
    private final ProjectLibrary projectLibrary;
    private final TaskLibrary taskLibrary;
    private final UserLibrary userLibrary;
    private String projectFileName;
    
    
    public ImportJson(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        this.projectFileName = "project.json";
        parseJson();
        System.out.println("End");
    }
    
    private void parseJson() throws IOException {
        JsonParser jsonParser = new JsonFactory().createParser(new File(this.projectFileName));
        //   parseUserJson(jsonParser);
        parseProjectJson(jsonParser);
    }
    
    private boolean emptyArray(JsonParser jsonParser) throws IOException {
        if(jsonParser.getText() == "[ ]") {
            return true;
        }
        return false;
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
            } else if("team".equals(field)) {
                parseTeamJson(jsonParser, project);
            }else if("taskList".equals(field)) {
                parseTaskListJson(jsonParser, project);
            }
            
        }
        this.projectLibrary.addProjectToList(project);
    }
    
    private void parseTaskListJson(JsonParser jsonParser, Project project) throws IOException {
        String field = jsonParser.getCurrentName();
        
        System.out.println("parseTaskList");
        System.out.println(jsonParser.getText());
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            field = jsonParser.getCurrentName();
            if ("taskList".equals(field)) {
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    field = jsonParser.getCurrentName();
                    if("dataList".equals(field)) {
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                            project.getTaskList().addToList(parseTaskJson(jsonParser, project));
                        }
                        return; // Probably needs some tweaks.
                    }
                }
            }
        }
    }
    
    private Task parseTaskJson(JsonParser jsonParser, Project project) throws IOException {
        Task task = new Task();
        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            if ("createdBy".equals(field)) {
                task.setCreatedBy(parseUserJson(jsonParser));
            } else if("name".equals(field)) {
                jsonParser.nextToken();
                task.setName(field);
            } else if("description".equals(field)) {
                jsonParser.nextToken();
                task.setDescription(field);
            } else if("status".equals(field)) {
                jsonParser.nextToken();
                task.setStatus(field);
            } else if("startDate".equals(field)) {
                jsonParser.nextToken();
                task.setStartDate(LocalDate.parse(jsonParser.getText()));
            } else if("deadline".equals(field)) {
                jsonParser.nextToken();
                task.setDeadline(LocalDate.parse(jsonParser.getText()));
            } else if("assignees".equals(field)) {
                task.setAssignees(parseAssignees(jsonParser));
            } else if("workedHours".equals(field)) {
                task.setWorkedHours(parseWorkedHours(jsonParser));
                System.out.println(jsonParser.getCurrentName());
                System.out.println(jsonParser.getText());
            }
        }
        return task;
    }
    
    private ArrayList<WorkedHours> parseWorkedHours(JsonParser jsonParser) throws IOException {
        ArrayList<WorkedHours> workedHoursLog = new ArrayList<>();
        WorkedHours tempWorkedHours = new WorkedHours();
        String field = jsonParser.getCurrentName();
        
        //  "workedHours" : [ {
        
        System.out.println("WorkedHours");
        if ("workedHours".equals(field)) {
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                field = jsonParser.getCurrentName();
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    field = jsonParser.getCurrentName();
                    if ("user".equals(field)) {
                        jsonParser.nextToken();
                        tempWorkedHours.setUser(parseUserJson(jsonParser));
                    } else if ("workedHours".equals(field)) {
                        jsonParser.nextToken();
                        tempWorkedHours.setWorkedHours(jsonParser.getValueAsDouble());
                    }
                    workedHoursLog.add(tempWorkedHours);
                    System.out.println(workedHoursLog.size());
                }
                System.out.println("End of WorkedHourLogObjects");
            }
        }
        System.out.println("End of loop");
        return workedHoursLog;
    }
    
    private ArrayList<User> parseAssignees(JsonParser jsonParser) throws IOException {
        System.out.println("Assignees");
        ArrayList<User> assignees = new ArrayList<>();
        while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
                assignees.add(parseUserJson(jsonParser));
        }
        return assignees;
    }

        /*
        "taskList" : {
            "dataList" : [ {
                "createdBy" : {
                    "userName" : "boye",
                    "password" : "1",
                    "email" : "pog@pog.com",
                    "occupation" : "Leet",
                    "companyName" : "Ericsson",
                    "salary" : 400.0,
                    "workingHours" : 2.0,
                        "inbox" : [ {
                          "sender" : "System",
                          "receiver" : "boye",
                          "content" : "Congratulations\r\nDear boye,\r\nWe are happy to report that you have earned a new achievement.\r\nGood idea? - Tier 1\r\nCreate some projects to earn this achievement\r\n5 more points required to achieve next tier\r\n",
                          "dateSent" : "2020-12-23",
                          "read" : false,
                          "id" : "df3a0675-da58-4c46-be1f-5ed70a625176"
                        },
                        "name" : "Lecture",
                        "description" : "",
                        "status" : "Default",
                        "startDate" : "2020-11-02",
                        "deadline" : "2021-01-08",
                        "assignees" : [ {
                            "userName" : "jens",
                            "password" : "jens",
                            "email" : "gussjodije@student.gu.se",
                            "occupation" : "Student",
                            "companyName" : "Simple Direction",
                            "salary" : 100.0,
                            "workingHours" : 8.0,
                            "inbox" : [ ],
                                "achievementTracker" : {
                                  "tracker" : { },
                                  "experience" : 0
                                },
                            "id" : "7effda0a-2931-4c74-962b-2755b819dcf5"
                        }
         */
    
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
        TeamMember tempMember = null;
        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            if ("memberList".equals(field)) {
                while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    tempMember = parseTeamMember(jsonParser);
                    if(tempMember != null) {
                        project.getTeam().getMemberList().put(tempMember.getUser().getUserName(),tempMember);
                    }
                }
            }
        }
    }
    
    private TeamMember parseTeamMember(JsonParser jsonParser) throws JsonParseException, IOException {
        TeamMember member = null;
        
        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            if("user".equals(field)) {
                User tempUser = parseUserJson(jsonParser);
                Role tempRole = parseRoleJson(jsonParser);
                member = new TeamMember(tempUser, tempRole);
            }
        }
        return member;
    }
    
    private Role parseRoleJson(JsonParser jsonParser) throws IOException {
        Role tempRole = null;
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            if ("type".equals(field)) {
                jsonParser.nextToken();
                if("Owner".equals(jsonParser.getText())) {
                    tempRole = new Owner();
                } else if ("Maintainer".equals(jsonParser.getText())) {
                    tempRole = new Maintainer();
                } else if ("Developer".equals(jsonParser.getText())) {
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
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    user.getInbox().add(parseMessage(jsonParser));
                }
            }else if("achievementTracker".equals(field)){
                jsonParser.nextToken();
                while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    parseAchievements(jsonParser, user);
                }
            }else if("id".equals(field)){
                jsonParser.nextToken();
                user.setID(jsonParser.getText());
            }
        }
        if(user.getUserName() != null) {
            if (userLibrary.findUserInList(user.getUserName()) == null) {
                userLibrary.addUserToList(user);
            }
        }
        return user;
    }
    
    private Message parseMessage(JsonParser jsonParser) throws IOException {
        Message tempMessage = new Message();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            if("sender".equals(field)) {
                jsonParser.nextToken();
                tempMessage.setSender(jsonParser.getText());
            } else if("receiver".equals(field)) {
                jsonParser.nextToken();
                tempMessage.setReceiver(jsonParser.getText());
            } else if("content".equals(field)) {
                jsonParser.nextToken();
                tempMessage.setContent(jsonParser.getText());
            } else if("dateSent".equals(field)) {
                jsonParser.nextToken();
                tempMessage.setDateSent(LocalDate.parse(jsonParser.getText()));
            } else if("status".equals(field)) {
                jsonParser.nextToken();
                tempMessage.setRead(jsonParser.getValueAsBoolean());
            }
        }
        return(tempMessage);
    }
    
    private void parseAchievements(JsonParser jsonParser, User user) throws IOException {
        String field = jsonParser.getCurrentName();
        if ("tracker".equals(field)) {
            jsonParser.nextToken();
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String name = jsonParser.getText();
                jsonParser.nextToken();
                int value = jsonParser.getValueAsInt();
                user.achievementTracker.addPoints(name, value, user);
            }
        } else if ("experience".equals(field)) {
            jsonParser.nextToken();
            user.achievementTracker.setExperience(jsonParser.getValueAsInt());
        }
    }
/*
        Exception in thread "main" java.lang.NullPointerException: Cannot invoke "achievements.Achievement.getRequiredPoints()" because the return value of "achievements.AchievementLibrary.getAchievement(String)" is null
        at achievements.AchievementTracker.getCurrentTier(AchievementTracker.java:73)
        at achievements.AchievementTracker.addPoints(AchievementTracker.java:32)
        at tools.ImportJson.parseAchievements(ImportJson.java:241)
        at tools.ImportJson.parseUserJson(ImportJson.java:191)
        at tools.ImportJson.parseProjectJson(ImportJson.java:64)
        at tools.ImportJson.parseJson(ImportJson.java:35)
        at tools.ImportJson.<init>(ImportJson.java:28)
        at controller.Controller.run(Controller.java:52)
        at Main.main(Main.java:8)
*/
        
        /*
        "achievementTracker" : {
              "tracker" : {
                "deleteProject" : 50,
                "createProject" : 25
              },
              "experience" : 15
            },
        */
    
}
