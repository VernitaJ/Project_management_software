package tools;

import access_roles.*;
import achievements.Achievement;
import budget.Budget;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import entities.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ImportJson {
    private final ProjectLibrary projectLibrary;
    private final TaskLibrary taskLibrary;
    private final UserLibrary userLibrary;
    private String projectFileName;
    
    
    public ImportJson(ProjectLibrary projectLibrary, TaskLibrary taskLibrary, UserLibrary userLibrary) throws IOException {
        this.projectLibrary = projectLibrary;
        this.taskLibrary = taskLibrary;
        this.userLibrary = userLibrary;
        this.projectFileName = System.getProperty("user.home") + "/project.json";
        
        parseJson();
    }
    
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
                
                parseTeamJson(jsonParser, project);
                
            }
            
        }
        
        this.projectLibrary.addProjectToList(project);
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
        
        TeamMember tempMember = null;
        
        jsonParser.nextToken();
        
        jsonParser.nextToken();
        
        
        
        String field = jsonParser.getCurrentName();
        if ("memberList".equals(field)) {
            while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
                //jsonParser.nextToken();
                tempMember = parseTeamMember(jsonParser);
                if(tempMember != null) {
                    project.getTeam().getMemberList().put(tempMember.getUser().getUserName(),tempMember);
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
              //  while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
              //      parseAchievements(jsonParser);
              //  }
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
    
    private Achievement parseAchievements(JsonParser jsonParser) throws IOException {
        Achievement tempAchievement = new Achievement();
        /*while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
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
        }*/
        return(tempAchievement);
    }
}
