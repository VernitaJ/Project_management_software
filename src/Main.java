import controller.Controller;
import entities.User;
import tools.ImportExcel;


public class Main {
    static User boye = new User("boye", "1", "pog@pog.com", "Leet", "Ericsson", 400, 2);
    public static void main(String[] args) {
        ImportExcel lego = new ImportExcel(boye);
        Controller start = Controller.getInstance();
        start.run();
    }
}