package controller;

import access_roles.Developer;
import access_roles.Maintainer;
import achievements.AchievementLibrary;
import components.Login;
import entities.*;
import tools.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Controller extends Menu {
    public Input input = Input.getInstance();
    public Menu menu;
    private Login login = Login.getInstance();
    private UserLibrary userLibrary = UserLibrary.getInstance();
    private TeamLibrary teamLibrary = TeamLibrary.getInstance();
    private ProjectLibrary projectLibrary = ProjectLibrary.getInstance();
    private TaskLibrary taskLibrary = TaskLibrary.getInstance();
    private AchievementLibrary achievementLibrary = AchievementLibrary.getInstance();
    public static Controller instance = null;
    private User currentUser = null;
    private Project currentProject = null;
    private Task currentTask = null;
    private File testFile = new File("src/temptestdata.txt");
    private ImportJson importJson = new ImportJson(projectLibrary, userLibrary);
    private ExportJson exportJson = new ExportJson(projectLibrary);

    
    private Controller() throws IOException {
        super();
    }
    
    public static Controller getInstance() throws IOException {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void run() throws IOException {
        userLibrary.addUserToList(new User("SBR", "1", "sbr@sbr.com", "Leet", "Ericsson", 400, 2));
        User sbr = (User) userLibrary.findUserInList("SBR");
        sbr.achievementTracker.addPoints("createProject",5, sbr);
        sbr.achievementTracker.addPoints("deleteProject", 3, sbr);
        ImportExcel lego = new ImportExcel(userLibrary, projectLibrary, sbr);
        // readFile();
        // testData();
        
        loginMenu();
    }
    
    public void testData() {
        User projectOwner = (User) userLibrary.findUserInList("SBR");
        projectOwner.achievementTracker.setExperience(15);
        projectOwner.getInbox().add(new Message("TestDataSender", "SBR", "Problem?"));
        projectOwner.getInbox().add(new Message("TestDataSender", "SBR", "Blä"));
        projectOwner.getInbox().add(new Message("TestDataSender", "SBR", "Kek"));
        try {
            ExportJson exportJSON = new ExportJson(projectLibrary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void exit()
    {
        input.teardown();
        teardown();
        System.exit(0);
    }
    
    private void readFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(testFile));
            while((line = br.readLine()) != null) {
                String[] word = line.split(",");
                switch (word[0].toLowerCase()) {
                    case "user" -> userLibrary.addUserToList(new User(word[1],word[2],word[3], word[4],word[5], Float.parseFloat(word[6]), Float.parseFloat(word[7])));
                    case "project" -> {
                        this.currentUser = (User) userLibrary.findUserInList(word[2]);
                        projectLibrary.addProjectToList(new Project(word[1], currentUser, word[3], LocalDate.of(parseInt(word[4]), parseInt(word[5]), parseInt(word[6])), LocalDate.of(parseInt(word[7]), parseInt(word[8]), parseInt(word[9]))));
                        this.currentUser = null;
                    }
                    case "task" -> {
                        currentUser = (User) userLibrary.findUserInList(word[2]);
                        currentProject = projectLibrary.listUsersProjects(currentUser, false).get(0);
                        TaskLibrary currentTaskLibrary = currentProject.getTaskList();
                        currentTaskLibrary.addTaskToList(currentProject, currentUser, word[3], word[4], LocalDate.parse(word[5]), LocalDate.parse(word[6]));
                    }
                    case "workedhours" -> {
                        currentUser = (User) userLibrary.findUserInList(word[1]);
                        currentProject = projectLibrary.listUsersProjects(currentUser, false).get(0);
                        TaskLibrary currentTaskLibrary = currentProject.getTaskList();
                        Task currentTask = currentTaskLibrary.listProjectsTasks(currentProject, false).get(0);
                        currentTaskLibrary.addWorkedHoursToList(currentTask, currentUser, parseDouble(word[2]));
                    }
                    case "budget" -> {
                        currentUser = (User) userLibrary.findUserInList(word[1]);
                        currentProject = projectLibrary.listUsersProjects(currentUser, false).get(0);
                        projectLibrary.addBudgetToList(currentProject,parseDouble(word[2]),parseDouble(word[3]));
                    }
                    case "achievementprogress" -> {
                        currentUser = (User) userLibrary.findUserInList(word[1]);
                        //idk what happens when achievement doesnt exist in the library??
                        currentUser.achievementTracker.addPoints(word[2],parseInt(word[3]), currentUser);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method just to say that a menu item has not been implemented. (temporary)
    private void notImplemented() {
        System.out.println(Input.RED + "This Feature Has Not been Implemented" + Input.RESET);
    }
    
    private void loginMenu() throws IOException {
        String[] options =
                {
                        "Create Account",
                        "Login",
                        "System Administrator Login",
                        "Exit"
                };
        menu = new Menu("Login Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> createUser();
                case "2" -> login();
                case "3" -> {
                    if (systemAdminLogin())
                    {
                        sysAdminMenu();
                    }
                    else
                    {
                        loginMenu();
                    }
                }
                case "4" -> exit();
            }
        } while (true);
    }
    
    private void mainMenu() throws IOException {
        String[] options =
                {
                        "Leaderboard",
                        "Projects",
                        "Profiles",
                        "Search",
                        "Messaging",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Main Menu", options);
        do
        {
            currentUser.achievementTracker.printXpBar();
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> leaderboardMenu();
                case "2" -> projectMenu();
                case "3" -> profileMenu();
                case "4" -> searchMenu();
                case "5" -> messageMenu();
                case "6" -> logout();
                case "7" -> exit();
            }
        } while (true);
    }
    
    private void sysAdminMenu() throws IOException {
        String[] options =
                {
                        "View all Projects",
                        "View all Users",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("System Admin Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> viewAllProjects();
                case "2" -> viewAllUsers();
                case "3" -> loginMenu();
                case "4" -> exit();
            }
        } while (true);
    }
    
    private void projectMenu() throws IOException {
        String[] options =
                {
                        "View Projects",
                        "Create Project",
                        "Import Project",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> {
                    currentProject = projectLibrary.navigateBetweenProjects(currentUser);
                    if(currentProject != null) {
                        currentProjectMenu();
                    }
                }
                case "2" -> createProject();
                case "3" -> importJson.parseJson();
                case "4" -> mainMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }
    
    private void currentProjectMenu() throws IOException {
        String[] options =
                {
                        "View Project Details",
                        "Tasks Menu",
                        "Finance Menu",
                        "Team Menu",
                        "Gantt Chart",
                        "Update Status",
                        "Export Project",
                        "Delete Project",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project: '" + currentProject.getName() + "' Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.viewProjectDetails(currentProject);
                case "2" -> tasksMenu();
                case "3" -> financeMenu(currentProject, currentUser);
                case "4" -> teamMenu();
                case "5" -> projectLibrary.ganttChart(currentProject);
                case "6" -> projectLibrary.updateStatus(currentProject, currentUser);
                case "7" -> exportJson.writeJsonProject(currentProject);
                case "8" -> {
                    Boolean isSuccessful = projectLibrary.deleteProject(currentProject, currentUser);
                    if(isSuccessful){
                        mainMenu();
                    }
                }
                case "9" -> mainMenu();
                case "10" -> logout();
                case "11" -> exit();
            }
        } while (true);
    }
    
    private void financeMenu(Project currentProject, User currentUser) throws IOException {
        String[] options =
                {
                        "View Project Budget",
                        "Add Budget",
                        "Change Budget",
                        "View Cost",
                        "View total hours by task",
                        "Hours to be Invoiced",
                        "Export Hours to be Invoiced",
                        "Expenses Forecast",
                        currentProject.getName() + " Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project: '" + currentProject.getName() + "' Budget Menu", options);
        do {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.viewBudget(currentProject, currentUser);
                case "2" -> addBudgetMenu(currentProject, currentUser);
                case "3" -> updateBudgetMenu(currentProject, currentUser);
                case "4" -> projectLibrary.viewCost(currentProject);
                case "5" -> taskLibrary.printDetailedWorkedHours(currentProject);
                case "6" -> taskLibrary.billableTask(currentProject, false);
                case "7" -> taskLibrary.billableTask(currentProject, true);
                case "8" -> projectLibrary.getExpenseForecast(currentProject, currentUser);
                case "9" -> currentProjectMenu();
                case "10" -> logout();
                case "11" -> exit();
            }
        } while (true);
    }
    
    private void addBudgetMenu(Project currentProject, User currentUser) throws IOException {
        String[] options =
                {
                        "Add Budget (SEK)",
                        "Add Budgeted Hours",
                        "Finance Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project: '" + currentProject.getName() + "' Budget Menu", options);
        do {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.addBudgetMoney(currentProject, currentUser);
                case "2" -> projectLibrary.addBudgetHours(currentProject, currentUser);
                case "3" -> financeMenu(currentProject, currentUser);
                case "4" -> logout();
                case "5" -> exit();
            }
        } while (true);
    }
    
    private void updateBudgetMenu(Project currentProject, User currentUser) throws IOException {
        String[] options =
                {
                        "Update Budget (SEK)",
                        "Update Budgeted Hours",
                        "Finance Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project: '" + currentProject.getName() + "' Budget Menu", options);
        do {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.updateBudgetMoney(currentProject, currentUser);
                case "2" -> projectLibrary.updateBudgetHours(currentProject, currentUser);
                case "3" -> financeMenu(currentProject, currentUser);
                case "4" -> logout();
                case "5" -> exit();
            }
        } while (true);
    }
    
    private void tasksMenu() throws IOException {
        String[] options =
                {
                        "Add Task",
                        "View Tasks",
                        "Remove Task",
                        "Completed Tasks",
                        "Countdown",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentProject.getName() + ": Tasks" +  " Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> taskLibrary.createTask(currentProject, currentUser);
                case "2" -> {
                    currentTask = taskLibrary.navigateBetweenTasks(currentProject);
                    if (currentTask != null) {
                        currentTaskMenu(currentProject, currentTask, currentUser);
                    }
                } // taskMenu
                case "3" -> taskLibrary.deleteTask(currentProject, currentUser);
                case "4" -> taskLibrary.completedTasks(currentProject);
                case "5" -> taskLibrary.countdown(currentProject);
                case "6" -> mainMenu();
                case "7" -> logout();
                case "8" -> exit();
            }
        } while (true);
    }
    
    private void teamMenu() throws IOException {
        String menuName;
        if (currentProject.getTeam() == null)
        {
            menuName = "Team Menu";
        }
        else
        {
            menuName = "Team Menu";
        }
        String[] options =
                {
                        "View Team",
                        "Add Team Member",
                        "Remove Team Member",
                        "Change Team Member Role",
                        currentProject.getName() + " Menu",
                        "Project Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(menuName, options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> teamLibrary.viewTeam(currentProject.getTeam());
                case "2" -> addMemberMenu();
                case "3" -> teamLibrary.removeTeamMember(currentProject.getTeam(), currentUser);
                case "4" -> currentProject.getTeam().roleChange(currentUser, currentProject);
                case "5" -> currentProjectMenu();
                case "6" -> projectMenu();
                case "7" -> logout();
                case "8" -> exit();
            }
        } while (true);
    }

    private void addMemberMenu() throws IOException {
        String[] options =
                {
                        "Maintainer",
                        "Developer",
                        "Custom Role",
                        "Team Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Add Member", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> teamLibrary.addTeamMember(currentProject.getTeam(), currentUser, "Maintainer");
                case "2" -> teamLibrary.addTeamMember(currentProject.getTeam(), currentUser, "Developer");
                case "3" -> teamLibrary.addTeamMember(currentProject.getTeam(), currentUser, "Custom");
                case "4" -> teamMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }
    
    private void currentTaskMenu(Project currentProject, Task currentTask, User currentUser) throws IOException {
        String[] options =
                {
                        "View Task Details",
                        "Add Team Member",
                        "Remove Team Member",
                        "Update Name",
                        "Update Description",
                        "Update Status",
                        "Add Worked Hours",
                        "Total Worked Hours",
                        "Notes for " + currentTask.getName(),
                        "Project Menu",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentTask.getName() + " Task", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> taskLibrary.viewTaskDetails(currentTask);
                case "2" -> taskLibrary.addAssignee(currentProject, currentTask, currentUser);
                case "3" -> taskLibrary.removeAssignee(currentProject.getTeam(), currentTask.getAssignees(), currentUser);
                case "4" -> taskLibrary.updateName(currentProject, currentTask, currentUser);
                case "5" -> taskLibrary.updateDescription(currentProject, currentTask, currentUser);
                case "6" -> taskLibrary.updateStatus(currentProject, currentTask, currentUser);
                case "7" -> taskLibrary.addWorkedHours(currentProject, currentTask, currentUser);
                case "8" -> taskLibrary.printAllWorkedHours(currentTask);
                case "9" -> currentTaskNoteMenu();
                case "10" -> currentProjectMenu();
                case "11" -> mainMenu();
                case "12" -> logout();
                case "13" -> exit();
            }
        } while (true);
    }

    private void currentTaskNoteMenu () throws IOException {
        String[] options =
                {
                        "Add a Note",
                        "View Your Note(s)",
                        "Edit a Note",
                        "Remove a Note",
                        "Task " + currentTask.getName() + "'s Menu",
                        "Project Menu",
                        "Main Menu",
                        "Logout",
                        "Exit",

                };
        menu = new Menu("Task " + currentTask.getName() + "'s Note Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> currentTask.addNote(currentUser);
                case "2" -> currentTask.viewUserNotes(currentUser);
                case "3" -> currentTask.editNote(currentUser);
                case "4" -> currentTask.deleteNote(currentUser);
                case "5" -> currentTaskMenu(currentProject, currentTask, currentUser);
                case "6" -> currentProjectMenu();
                case "7" -> mainMenu();
                case "8" -> logout();
                case "9" -> exit();
            }
        } while (true);
    }
    
    private void searchMenu() throws IOException {
        String[] options =
                {
                        "Search by Username",
                        "Search by Company",
                        "Search by Occupation",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> userLibrary.printSearchResults(userLibrary::searchByUsername);
                case "2" -> userLibrary.printSearchResults(userLibrary::searchByCompany);
                case "3" -> userLibrary.printSearchResults(userLibrary::searchByOccupation);
                case "4" -> mainMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }

    private void messageMenu() throws IOException {
        String[] options =
                {
                        "Create Message",
                        "Read Messages",
                        "Delete Message",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentUser.getUserName() + " Messaging", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> createMessage();
                case "2" -> readMessage();
                case "3" -> deleteMessage();
                case "4" -> mainMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }
    
    private void profileMenu() throws IOException {
        String[] options =
                {
                        "View My Profile",
                        "Edit Profile",
                        "View All User Profiles",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Profile Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> userLibrary.viewMyProfile(currentUser);
                case "2" -> editProfileMenu();
                case "3" -> userLibrary.viewAllProfiles();
                case "4" -> mainMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }
    
    private void editProfileMenu() throws IOException {
        String[] options =
                {
                        "Change Password",
                        "Update Email",
                        "Update Company",
                        "Update Occupation",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Edit Profile", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> userLibrary.changePassword(currentUser);
                case "2" -> userLibrary.updateEmail(currentUser);
                case "3" -> userLibrary.updateCompany(currentUser);
                case "4" -> userLibrary.updateOccupation(currentUser);
                case "5" -> mainMenu();
                case "6" -> logout();
                case "7" -> exit();
            }
        } while (true);
    }
    
    
    private void leaderboardMenu() throws IOException {
        String[] options =
                {
                        "View Leaderboard",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Leaderboard", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> userLibrary.leaderboard();
                case "2" -> mainMenu();
                case "3" -> logout();
                case "4" -> exit();
            }
        } while (true);
    }

    private void login() throws IOException {
        currentUser = (userLibrary.login());
        if (currentUser !=null)
        {
            mainMenu();
        }
    }
    
    public void logout() throws IOException {
        currentUser = null;
        loginMenu();
    }
    
    public User userProfile(String name) {
        for (User user : userLibrary.getAllUsers()) {
            if (user.getUserName().equals(name)){
                return user;
            }
        }
        return null;
    }
    
    private boolean systemAdminLogin(){
        if (Login.getInstance().authorizeAdmin())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private void viewAllProjects()
    {
        projectLibrary.printAllProjects();
    }
    private void viewAllUsers()
    {
        UserLibrary.getInstance().printAllUsers();
    }
    
    private void createUser() {
        userLibrary.createUser();
    }
    
    private void createProject() {
        projectLibrary.createProject(currentUser);
    }
    
    
    private void createMessage() {
        userLibrary.createMessage(currentUser);
    }
    
    private void readMessage() {
        userLibrary.readMessage(currentUser);
    }
    
    private void deleteMessage() {
        userLibrary.deleteMessage(currentUser);
    }
    
    
}

