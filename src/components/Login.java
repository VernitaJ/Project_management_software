package components;

public class Login {
    private static final Login instance = null;

    Login(){
    }

    public static Login getInstance() {
        if (instance == null) {
            return new Login();
        } else {
            return instance;
        }
    }
}