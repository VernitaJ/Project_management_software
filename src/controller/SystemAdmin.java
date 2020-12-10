package controller;

public class SystemAdmin
{
    private String username;
    private String password;
    private static final SystemAdmin instance = null;

    SystemAdmin(){
        this.username = "admin";
        this.password = "admin";
    }

    public static SystemAdmin getInstance() {
        if (instance == null) {
            return new SystemAdmin();
        } else {
            return instance;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
