package entities;
//currently just for testing DataLibrary, not the final class
//please dont edit it yet
import tools.*;


import java.time.LocalDate;

public class ProjectLibrary extends DataLibrary{
    private static final ProjectLibrary instance = null;

    public static ProjectLibrary getInstance() {
        if (instance == null) {
            return new ProjectLibrary();
        } else {
            return instance;
        }
    }
    //user control is missing
    public void createProject(User currentUser) {
        Input input = Input.getInstance();
        String name = input.getStr("Project name: ");
        String description = input.getStr("Project description: ");
        LocalDate startDate = input.getDate("Project start date (YYYY-MM-DD): ");
        LocalDate endDate = input.getDate("Project end date (YYYY-MM-DD): ");
        addToList(new Project(name, currentUser, description, startDate, endDate));
    }


    //access level and ownership check is missing
    public boolean deleteProject(String idToDelete, User currentUser){
        if(currentUser.getRole().roleType().equals("Owner")){
            return removeItFromList(idToDelete);
        }
        else {
            System.out.println("You are not authorized to perform this action!");
        }
            return false;

    }

}
