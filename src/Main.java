import controller.Controller;
import entities.Project;
import entities.ProjectLibrary;

public class Main {
    public static void main(String[] args) {
        Controller start = Controller.getInstance();
        start.run();
    }
}