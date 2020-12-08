package entities;
import tools.*;


import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static entities.Task.sortByDeadline;
import static entities.Task.sortByStartDate;
import static java.time.temporal.ChronoUnit.DAYS;

public class ProjectLibrary extends DataLibrary{
    private static final ProjectLibrary instance = null;
    Input input = Input.getInstance();
    
    public static ProjectLibrary getInstance() {
        if (instance == null) {
            return new ProjectLibrary();
        } else {
            return instance;
        }
    }
    
    public boolean confirmAccess(Team projectTeam, User currentUser) {
        if (projectTeam.findTeamMember(currentUser).getRole().adminAccess()) {
            return true;
        } else {
            System.out.println("You are not authorized to perform this action!");
            return false;
        }
    }
    
    public boolean noTeam(Project currentProject) {
        if(currentProject.getTeam() == null) { //THIS SHOULD BE REMOVED AS TEAMS SHOULD BE CREATED AUTOMATICALLY.
            System.out.println("No team created in current project.");
            return true;
        }
        return false;
    }
    
    public void createProject(User currentUser) {
        Input input = Input.getInstance();
        String name = input.getStr("Project name: ");
        String description = input.getStr("Project description: ");
        LocalDate startDate = input.getDate("Project start date (YYYY-MM-DD): ");
        LocalDate endDate = input.getDate("Project end date (YYYY-MM-DD): ");
        addToList(new Project(name, currentUser, description, startDate, endDate));
    }
    
    public void addProjectToList(Project project)
    {
        list.add(project);
    }
    
    public void addBudgetToList(Project currentProject, double money, double hours) {
        currentProject.getBudget().setMoney(money);
        currentProject.getBudget().setHours(hours);
    }
    
    public ArrayList<Project> listUsersProjects(User currentUser){
        ArrayList<Project> tempList = new ArrayList<>();
        for(Data project : list){
            Project currentProject = ((Project)project);
            if ((currentProject.getTeam() == null &&
                    currentProject.getProjectManager().getUserName().equals(currentUser.getUserName()))
                    ||
                    (currentProject.getTeam() != null &&
                            currentProject.getTeam().findTeamMember(currentUser) != null)) {
                
                tempList.add(currentProject);
            }
        }
        
        if (tempList.size() == 0){
            System.out.println("You have no projects!");
        } else {
            for (int i=0; i<tempList.size(); i++){
                System.out.println(" " + (i + 1) + ") " + tempList.get(i).getName());
            }
        }
        
        return tempList;
    }
    
    public Project navigateBetweenProjects(User currentUser){
        ArrayList<Project> projectList = listUsersProjects(currentUser);
        if(projectList.size()==0){
            return null;
        } else {
            int choice;
            do{
                choice = input.getInt("Enter project number or 0 to return to the previous menu: ");
            } while(choice < 0 || choice > projectList.size());
            
            if (choice == 0){
                return null;
            } else
                return projectList.get(choice-1);
        }
    }
    
    public void updateName(User currentUser){
        Project currentProject = null;
        for(Data project : list) {
            currentProject = ((Project) project);
        }
        if(currentProject.getTeam().findTeamMember(currentUser).getRole().adminAccess()){
            String newDesc = input.getStr("Enter the description: ");
            currentProject.setName(newDesc);
        }
        else{
            System.out.println("You are not authorized to perform this action!!");
        }
        
    }
    
    public void viewProjectDetails(Project currentProject){
        if(findItInList(currentProject.getID()) != null){
            System.out.println("Project Name: " + currentProject.getName());
            System.out.println("Project Description: " + currentProject.getDescription());
            if(!currentProject.getStatus().isEmpty()){
                System.out.println("Status: "+ currentProject.getStatus());
            }
            System.out.println("Start Date: " + currentProject.getStartDate());
            System.out.println("End Date: " + currentProject.getEndDate());
            taskVisualisation(currentProject);
        } else {
            System.out.println("Project does not exist!");
        }
        
    }
    
    public void updateStatus(Project currentProject, User currentUser){
        if(currentProject.getProjectManager().checkID(currentUser.getID())){
            String newStatus = input.getStr("Enter the status: ");
            if(confirmAction("Are you sure you want to update the status?")){
                currentProject.setStatus(newStatus);
            }
        }
        else{
            System.out.println("You are not authorized to perform this action!");
        }
    }
    
    
    public boolean deleteProject(Project currentProject, User currentUser){
        Project projectToDelete = (Project)findItInList(currentProject.getID());
        if(projectToDelete==null){
            System.out.println("Project does not exist!");
            return false;
        }
        if(projectToDelete.getProjectManager().checkID(currentUser.getID())){
            System.out.println("Warning!");
            System.out.println("You are about to delete the entire project!");
            System.out.println("This action is irreversible!");
            String password = input.getStr("Please enter password to confirm the deletion (leave empty to abort): ");
            if(currentUser.getPassword().equals(password)){
                if(removeItFromList(projectToDelete.getID())){
                    System.out.println("Project successfully deleted!");
                    System.out.println("Returning to main menu...");
                    return true;
                } else {
                    System.out.println("Something went wrong");
                    return false;
                }
            } else if(password.isEmpty()){
                System.out.println("Action aborted!");
                System.out.println("Returning to main menu...");
                return false;
            } else {
                System.out.println("Confirmation Failed!");
                return false;
            }
        }
        System.out.println("You are not authorized to perform this action!");
        return false;
    }
    
    private boolean confirmAction(String text){
        Input input = Input.getInstance();
        System.out.println(text);
        boolean confirmCheck = false;
        while(!confirmCheck){
            String confirmation = input.getStr("Please enter yes or no: ");
            if(confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")){
                return true;
            } else if (confirmation.equalsIgnoreCase("no") || confirmation.equalsIgnoreCase("n")) {
                System.out.println("Action aborted!");
                return false;
            } else {
                System.out.println("Invalid input!");
            }
        } return false;
        
    }
    
    public void viewCost(Project currentProject)
    {
        Project project = (Project)findItInList(currentProject.getID());
        if(project==null){
            System.out.println("Project does not exist!");
        } else {
            TaskLibrary tempLibrary = currentProject.taskList;
            double totalCost = 0;
            for (Data task : currentProject.taskList.list)
            {
                Task currentTask = (Task) task;
                double totalCostPerTask = 0;
                
                for (WorkedHours log : currentTask.getWorkedHours())
                {
                    totalCostPerTask += log.getUser().getSalary() * log.getWorkedHours();
                    
                }
                System.out.println("The total cost for the Task  " + currentTask.getName() + " " + totalCostPerTask + "kr.");
                totalCost += totalCostPerTask;
            }
            System.out.println("Total cost for the project are " + totalCost + "kr.");
        }
    }
    
    public void viewBudget(Project currentProject, User currentUser) {
        if(noTeam(currentProject)) {
            return;
        }
        if(!confirmAccess(currentProject.getTeam(),currentUser)) {
            return;
        }
        double sek = currentProject.getBudget().getMoney();
        double hours = currentProject.getBudget().getHours();
        double hoursRemaining = getHoursRemainingBudget(currentProject);
        double daysRemaining = hoursRemaining / 24;
        System.out.println("Budget Statistics: " +
                "\nTotal SEK: " + sek +
                "\nTotal Hours: " + hours +
                "\nDays left on budget: " + daysRemaining +
                "\nHours left on budget: " + hoursRemaining +
                "");
        
    }
    
    public double getHoursRemainingBudget(Project currentProject) {
        double totalHoursPerTask = 0.0;
        for (Data task : currentProject.taskList.list) {
            Task currentTask = (Task) task;
            
            for (WorkedHours log : currentTask.getWorkedHours()) {
                totalHoursPerTask += log.getWorkedHours();
            }
        }
        return totalHoursPerTask;
    }
    
    public void addBudgetMoney(Project currentProject, User currentUser) {
        if(noTeam(currentProject)) {
            return;
        }
        if(currentProject.getBudget().budgetExistMoney()) {
            updateBudgetMoney(currentProject, currentUser);
            return;
        }
        if (!confirmAccess(currentProject.getTeam(), currentUser)) {
            return;
        }
        double value = -1;
        do {
            value = input.getDouble("What is the total budget value in SEK?");
        } while(value < 0);
        currentProject.getBudget().setMoney(value);
        System.out.println("Budget in SEK has been added");
    }
    
    public void addBudgetHours(Project currentProject, User currentUser) {
        if(noTeam(currentProject)) {
            return;
        }
        if(currentProject.getBudget().budgetExistHours()) {
            updateBudgetHours(currentProject, currentUser);
            return;
        }
        if (!confirmAccess(currentProject.getTeam(), currentUser)) {
            return;
        }
        double value = -1;
        do {
            value = input.getDouble("What is the total budget in hours?");
        } while(value < 0);
        currentProject.getBudget().setMoney(value);
        System.out.println("Budget in hours has been added");
    }
    
    public void updateBudgetMoney(Project currentProject, User currentUser) {
        if(noTeam(currentProject)) {
            return;
        }
        if(!confirmAccess(currentProject.getTeam(), currentUser)) {
            return;
        }
        double value = -1;
        do {
            value = input.getDouble("What is the total budget value in SEK?");
        } while(value < 0);
        currentProject.getBudget().setMoney(value);
        System.out.println("Budget in SEK has been updated");
    }
    
    public void updateBudgetHours(Project currentProject, User currentUser) {
        if(noTeam(currentProject)) {
            return;
        }
        if(!confirmAccess(currentProject.getTeam(), currentUser)) {
            return;
        }
        double value = -1;
        do {
            value = input.getDouble("What is the total budget in hours?");
        } while(value < 0);
        currentProject.getBudget().setMoney(value);
        System.out.println("Budget in hours has been updated");
    }

    public void taskVisualisation(Project currentProject) {
        ArrayList<Data> taskList = currentProject.taskList.list;
        Collections.sort(taskList, sortByStartDate);
        LocalDate startDate = currentProject.getStartDate();
        LocalDate endDate = currentProject.getEndDate();
        long daysOfProject = DAYS.between(startDate, endDate);
        long amountOfDates = daysOfProject/20 + 1;
        LocalDate printedDate = startDate;
        System.out.println("\n" + input.PURPLE + "GANTT CHART" + input.RESET);
        System.out.print("                    ");
        for (int i = 1; i < amountOfDates ; i++){
            if (DAYS.between(printedDate, endDate) > 19){
                System.out.print(printedDate + "          ");
                printedDate = printedDate.plusDays(20);
            }
        }
        System.out.println(endDate);
        for (Data task : taskList){
            Task currentTask = (Task) task;
            int characters = charactersInName(currentTask);
            System.out.print(currentTask.getName());
            for (int i = characters; i < 20; i++) System.out.print(" ");
            long daysFromStart = DAYS.between(startDate, currentTask.getStartDate());
            for (int i = 0; i < daysFromStart; i++) System.out.print(" ");
            long durationOfTask = DAYS.between(currentTask.getStartDate(), currentTask.getDeadline());
            for (int i = 0; i < durationOfTask; i++){
                System.out.print(input.BLUE + "¤");
            }
            System.out.println(input.RESET + "\n");
        }
    }

    public int charactersInName(Task task){
        String name = task.getName();
        int count = name.length();
        return count;
    }

}
