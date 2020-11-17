import controller.Controller;
import entities.Project;
import entities.ProjectLibrary;

public class Main
{
    public static void main(String[] args)
    {
        /*Controller start = Controller.getInstance();
        start.run();*/

        ProjectLibrary projects = new ProjectLibrary();
        Project projectX = projects.createProject("Dummy Duck","Inject Adamentium to the Wolverine");
        System.out.println("Project with ID: "+ projectX.getID()+ "has been created successfully");
        System.out.println("list of all projects");
        System.out.println(projects.listAll());

        if (projects.findItInList(projectX.getID()) != null ){
            System.out.println("Yes, the project x exist in the list");
        }

        if(projects.deleteProject(projectX.getID())){
            System.out.println("Project has been deleted successfully");
        }
        System.out.println("list of all projects");
        System.out.println(projects.listAll());
    }
}
