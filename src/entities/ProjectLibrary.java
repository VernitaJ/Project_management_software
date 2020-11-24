package entities;

import tools.Input;

import java.time.LocalDate;

public class ProjectLibrary extends DataLibrary{
    //user control is missing
    public void createProject() {
    }

    //access level and ownership check is missing
    public boolean deleteProject(String idToDelete){
        return removeItFromList(idToDelete);
    }
    
}
