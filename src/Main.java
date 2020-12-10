import controller.Controller;
import entities.Project;
import entities.ProjectLibrary;
import tools.Import;

public class Main {
    public static void main(String[] args) {
        Import lego = new Import();
        Controller start = Controller.getInstance();
        start.run();
    }
}