import controller.Controller;

import java.io.IOException;


public class Main {
    
    public static void main(String[] args) throws IOException {
        Controller start = Controller.getInstance();
        start.run();
    }
}