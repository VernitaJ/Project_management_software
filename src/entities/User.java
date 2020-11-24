package entities;

public class User extends Data {

    private String userName;
    private String password;
    private String eMail;
    private String occupation;
    private String companyName;



    public User(String userName, String password, String eMail, String occupation, String companyName) {
        this.userName = userName;
        this.password = password;
        this.eMail = eMail;
        this.occupation = occupation;
        this.companyName = companyName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.eMail;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
