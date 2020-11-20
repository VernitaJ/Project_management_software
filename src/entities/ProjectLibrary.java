package entities;
import tools.Input;

public class ProjectLibrary extends DataLibrary{
    
    public void createProject() {
        Input input = Input.getInstance();
        String name = input.getStr("Project name");
        String startDate = input.getStr("Project Start Date");
        String endDate = input.getStr("End Date");
        //addToList(new Project(name, name,startDate, endDate));
    }
    

    
    // deleteProject()
    
}
