package entities;

import tools.Input;

import java.time.LocalDate;

public class ProjectLibrary extends DataLibrary{
    
    public void createProject() {
        Input input = Input.getInstance();
        String name = input.getStr("Project name: ");
        String description = input.getStr("Project description: ");
        LocalDate startDate = input.getDate("Project start date (YYYY-MM-DD): ");
        LocalDate endDate = input.getDate("Project end date (YYYY-MM-DD): ");
        addToList(new Project(name, name, description, startDate, endDate));
    }
    

    
    // deleteProject()
    
}
