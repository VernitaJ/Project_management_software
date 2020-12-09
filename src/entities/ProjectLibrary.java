package entities;

import tools.Input;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;

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
    
    public ArrayList<Project> listUsersProjects(User currentUser, boolean print){
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
        if (print){
            if (tempList.size() == 0){
                System.out.println("You have no projects!");
            } else {
                for (int i=0; i<tempList.size(); i++){
                    System.out.println(" " + (i + 1) + ") " + tempList.get(i).getName());
                }
            }
        }
        return tempList;
    }

    
    public Project navigateBetweenProjects(User currentUser){
        ArrayList<Project> projectList = listUsersProjects(currentUser, true);
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
        if(currentProject==null){
            System.out.println("Project does not exist!");
            return;
        }
        TaskLibrary taskLibrary = currentProject.taskList;
        getTotalCostProject(taskLibrary, true);
    }
    
    public double getTotalCostProject(TaskLibrary taskLibrary, boolean print) {
        double totalCostProject = 0;
        for (Data task : taskLibrary.list)
        {
            Task currentTask = (Task) task;
            totalCostProject += getTotalCostTask(currentTask, print);
        }
        if(print) {
            System.out.println("Total cost for the project is " + totalCostProject + "kr.");
        }
        return totalCostProject;
    }
    
    public double getTotalCostTask(Task currentTask, boolean print) {
        double totalCostPerTask = 0;
        for (WorkedHours log : currentTask.getWorkedHours())
        {
            totalCostPerTask += log.getUser().getSalary() * log.getWorkedHours();
        }
        if(print) {
            System.out.println("The total cost for the Task: " + currentTask.getName() + " " + totalCostPerTask + "kr.");
        }
        return totalCostPerTask;
    }
    
    public void getExpenseForecast(Project currentProject, User currentUser) {
        if(noTeam(currentProject)) {
            return;
        }
        if(!confirmAccess(currentProject.getTeam(),currentUser)) {
            return;
        }
        String currency = currentProject.budget.CURRENCY;
        double costPerDay = getTotalCostProject(currentProject.taskList, false) / currentProject.duration();
        Period actualDaysRemainingPeriod = currentProject.getStartDate().
                until(currentProject.getStartDate().
                        plusDays(currentProject.duration()));
        int actualDaysRemaining = actualDaysRemainingPeriod.getDays();
        double iteratedCostRemainingDays = (getBudgetHoursRemaining(currentProject) / 24) * costPerDay;
        double iteratedRemainingMoney =
                currentProject.getBudget().getMoney() -
                        getTotalCostProject(currentProject.taskList, false) -
                        iteratedCostRemainingDays;
        double iteratedMoneyToUsePerHour = (iteratedRemainingMoney /
                getBudgetHoursRemaining(currentProject) / 24) / 8;
        iteratedRemainingMoney = new BigDecimal(iteratedRemainingMoney).
                setScale(2, RoundingMode.HALF_UP).
                doubleValue();
        iteratedMoneyToUsePerHour = new BigDecimal(iteratedMoneyToUsePerHour).
                setScale(2, RoundingMode.HALF_UP).
                doubleValue();
        String message;
        if(iteratedRemainingMoney > 0) {
            message = "Your project is cheaper than planned. " +
                    "\nThere are " + actualDaysRemaining + " days remaining until the current finish date." +
                    "\nIf your project keeps the same linear pace, you will have " + iteratedRemainingMoney + currency + " extra when the project is completed." +
                    "\nThis opens up for hiring additional resources with a combined rate of " + iteratedMoneyToUsePerHour + currency + "/hour";
        } else {
            message = "Your project will break the budget. " +
                    "\nThere are " + actualDaysRemaining + " days remaining until the current finish date." +
                    "\nIf your project keeps the same linear pace you will have a deficit of " + input.RED + iteratedRemainingMoney + currency + input.RESET + " when the project is completed";
        }
        System.out.println(message);
    }
    
    public void viewBudget(Project currentProject, User currentUser) {
        if(noTeam(currentProject)) {
            return;
        }
        if(!confirmAccess(currentProject.getTeam(),currentUser)) {
            return;
        }
        double budgetMoney = currentProject.getBudget().getMoney();
        double budgetHours = currentProject.getBudget().getHours();
        double hoursRemaining = getBudgetHoursRemaining(currentProject);
        double daysRemaining = new BigDecimal(hoursRemaining/24).
                setScale(2, RoundingMode.HALF_UP).
                doubleValue();
        double remainingMoney = getBudgetMoneyRemaining(currentProject);
        String informBudgetReachedSEK = "";
        String informBudgetReachedHours = "";
        if(!getOnBudgetMoney(currentProject)) {
            informBudgetReachedSEK = "\nYou have reached your budget limit in SEK!";
        }
        if(!getOnBudgetHours(currentProject)) {
            informBudgetReachedHours = "\nYou have reached your budget limit in Hours!";
        }
        System.out.println("Budget Statistics: " +
                "\nTotal SEK: " + budgetMoney +
                "\nTotal Hours: " + budgetHours +
                "\nDays remaining on budget: " + daysRemaining +
                "\nHours remaining on budget: " + hoursRemaining +
                informBudgetReachedHours +
                "\nSEK remaining on budget: " + remainingMoney +
                informBudgetReachedSEK);
    }
    
    public double getBudgetMoneyRemaining(Project currentProject) {
        return currentProject.getBudget().getMoney() - getTotalCostProject(currentProject.taskList, false);
    }
    
    public double getBudgetHoursRemaining(Project currentProject) {
        return currentProject.getBudget().getHours() - getHoursWorkedProject(currentProject);
    }
    
    public boolean getOnBudgetMoney(Project currentProject) {
        double budgetSEK = currentProject.getBudget().getMoney();
        double currentCost = getTotalCostProject(currentProject.taskList, false);
        if(budgetSEK > currentCost) {
            return true;
        }
        return false;
    }
    
    public boolean getOnBudgetHours(Project currentProject) {
        double budgetHours = currentProject.getBudget().getHours();
        double currentHours = getHoursWorkedProject(currentProject);
        if(budgetHours > currentHours) {
            return true;
        }
        return false;
    }
    
    public double getHoursWorkedProject(Project currentProject) {
        double totalHoursProject = 0.0;
        for (Data task : currentProject.taskList.list) {
            Task currentTask = (Task) task;
            totalHoursProject += getHoursWorkedTask(currentTask);
        }
        return totalHoursProject;
    }
    
    public double getHoursWorkedTask(Task currentTask) {
        double totalHoursTask = 0;
        for (WorkedHours log : currentTask.getWorkedHours()) {
            totalHoursTask += log.getWorkedHours();
        }
        return totalHoursTask;
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
    
    
    public void ganttChart(Project currentProject){
        ArrayList<Data> taskList = currentProject.taskList.list;
        Collections.sort(taskList, sortByStartDate);
        System.out.println("\n");
        for (int i = 0; i < 20; i++) System.out.print(" ");
        System.out.println(input.PURPLE + "GANTT CHART \n" + input.RESET);
        for (int i = 0; i < 20; i++) System.out.print(" ");
        long daysBetweenDates = dateVisualisation(currentProject);
        printTasks(currentProject, taskList, daysBetweenDates);
    }
    
    public long dateVisualisation(Project currentProject) {
        LocalDate startDate = currentProject.getStartDate();
        LocalDate endDate = currentProject.getEndDate();
        long daysOfProject = DAYS.between(startDate, endDate);
        long remainder = daysOfProject%5;
        long daysBetweenDisplayDates = Math.round(daysOfProject - remainder)/10;
        if (daysBetweenDisplayDates < 12){
            daysBetweenDisplayDates = 12;
        }
        long amountOfDates = (daysOfProject/daysBetweenDisplayDates);
        LocalDate printedDate = startDate;
        for (int i = 0; i < amountOfDates+1; i++){
            if (DAYS.between(printedDate, endDate) > 3){
                System.out.print(printedDate);
                String declare = " ";
                System.out.print(declare.repeat((int) (daysBetweenDisplayDates-10)));
                printedDate = printedDate.plusDays(daysBetweenDisplayDates);
            }
        }
        System.out.println(" Final Day of Project: " + input.GREEN + endDate + input.RESET);
        return daysBetweenDisplayDates;
    }
    
    public void printTasks(Project currentProject, ArrayList<Data> taskList, long daysBetween){
        for (Data task : taskList){
            Task currentTask = (Task) task;
            int characters = charactersInName(currentTask);
            System.out.print(currentTask.getName());
            String displayDateChar = (input.GREEN + "¤");
            for (int i = characters; i < 20; i++) System.out.print(" ");
            long daysFromStart = DAYS.between(currentProject.getStartDate(), currentTask.getStartDate());
            long charPerDay = 20/daysBetween;
            long spaceFromStart = daysFromStart*(20/daysBetween);
            String spacer = " ";
            for (int i = -5; i < spaceFromStart; i++) System.out.print(spacer);
            long durationOfTask = DAYS.between(currentTask.getStartDate(), currentTask.getDeadline());
            for (int i = 0; i < durationOfTask*charPerDay; i++){
                if ((spaceFromStart+i)%daysBetween == 0){
                    System.out.print(displayDateChar);
                } else System.out.print(input.BLUE + "¤" + input.RESET);
            }
            for (User teamMember : currentTask.getAssignees()){
                System.out.print(" " +teamMember.getUserName() + " ");
            }
            System.out.println("\n");
        }
    }
    
    public int charactersInName(Task task){
        String name = task.getName();
        int count = name.length();
        return count;
    }
}
