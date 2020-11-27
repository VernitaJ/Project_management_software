package entities;
import tools.*;


import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectLibrary extends DataLibrary{
    private static final ProjectLibrary instance = null;

    public static ProjectLibrary getInstance() {
        if (instance == null) {
            return new ProjectLibrary();
        } else {
            return instance;
        }
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


    //not final we need to decide to menus before integrate this with them
    public boolean deleteProject(String idToDelete, User currentUser){
        Project projectToDelete = (Project)findItInList(idToDelete);
        if(projectToDelete==null){
            return false;
        }
        if(projectToDelete.isUserOwner(currentUser)){
            return removeItFromList(idToDelete);
        }
        System.out.println("You are not authorized to perform this action!");
        return false;

    }

    public ArrayList<Project> listUsersProjects(User currentUser){
        ArrayList<Project> tempList = new ArrayList<>();
        for(Data project : list){
            Project currentProject = ((Project)project);
            if((currentProject.isUserTeamMember(currentUser))){
                tempList.add(currentProject);
            }
        }

        if (tempList.size() == 0){
            System.out.println("You have no projects!");
        } else {
            for (int i=0; i<tempList.size(); i++){
                System.out.println(i+1 + "- " + tempList.get(i).getName());
            }
        }

        return tempList;
    }

    public Project navigateBetweenProjects(User currentUser){
        Input input = Input.getInstance();
        ArrayList<Project> projectList = listUsersProjects(currentUser);
        if(projectList.size()==0){
            return null;
        } else {
            int choice;
            do{
                choice = input.getInt("Enter project number or 0 to return to the previous menu: ");
            } while(choice < 0 || choice > projectList.size()+1);

            if (choice == 0){
                return null;
            } else return projectList.get(choice-1);
        }
    }
    
    public void updateName(User currentUser){
        Project currentProject = null;
        for(Data project : list) {
            currentProject = ((Project) project);
        }
            if(currentProject.team.findTeamMember(currentUser).getRole().adminAccess()){
            Input input = Input.getInstance();
            String newDesc = input.getStr("Enter the description: ");
            currentProject.setName(newDesc);
        }
        else{
            System.out.println("You are not authorized to perform this action!!");
        }
        
    }

}
