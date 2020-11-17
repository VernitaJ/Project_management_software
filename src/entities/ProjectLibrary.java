package entities;
//currently just for testing DataLibrary, not the final class
//please dont edit it yet
public class ProjectLibrary extends DataLibrary{

    public Project createProject(String projectOwner, String task){
        Project newProj = new Project(projectOwner, task);
        addToList(newProj);
        return newProj;
    }

    public boolean deleteProject(String idToDelete){
        return removeItFromList(idToDelete);
    }


}
