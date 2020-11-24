package entities;
//currently just for testing DataLibrary, not the final class
//please dont edit it yet
public class ProjectLibrary extends DataLibrary{
    //user control is missing
    public void createProject() {
        Input input = Input.getInstance();
        String name = input.getStr("Project name: ");
        String description = input.getStr("Project description: ");
        LocalDate startDate = input.getDate("Project start date (YYYY-MM-DD): ");
        LocalDate endDate = input.getDate("Project end date (YYYY-MM-DD): ");
        addToList(new Project(name, name, description, startDate, endDate));
    }


    //access level and ownership check is missing
    public boolean deleteProject(String idToDelete){
        return removeItFromList(idToDelete);
    }

}
