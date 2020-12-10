import controller.Controller;
import tools.ImportExcel;


public class Main {
    public static void main(String[] args) {
        ImportExcel lego = new ImportExcel();
        Controller start = Controller.getInstance();
        start.run();
    }
}